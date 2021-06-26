package br.com.api.timesheet.service.impl;

import static br.com.api.timesheet.enumeration.ReportTypeEnum.DANGEROUSNESS;
import static br.com.api.timesheet.enumeration.ReportTypeEnum.EXTRA_HOURS_FULL;
import static br.com.api.timesheet.enumeration.ReportTypeEnum.EXTRA_HOURS_PART;
import static br.com.api.timesheet.enumeration.ReportTypeEnum.NIGHT_SHIFT;
import static br.com.api.timesheet.enumeration.ReportTypeEnum.PAID_NIGHT_TIME;
import static br.com.api.timesheet.enumeration.ReportTypeEnum.REGULAR_HOURS;
import static br.com.api.timesheet.enumeration.ReportTypeEnum.SUMULA_90;
import static br.com.api.timesheet.enumeration.ReportTypeEnum.WEEKLY_REST;
import static br.com.api.timesheet.enumeration.ReportTypeEnum.WEEKLY_REST_COMPLEMENT;
import static br.com.api.timesheet.utils.DateUtils.convertNanosToDecimalHours;
import static java.time.Duration.ofSeconds;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

import br.com.api.timesheet.dto.TimesheetDailyReport;
import br.com.api.timesheet.dto.TimesheetDocket;
import br.com.api.timesheet.dto.TimesheetDocketItem;
import br.com.api.timesheet.dto.TimesheetReport;
import br.com.api.timesheet.entity.Bonus;
import br.com.api.timesheet.entity.Employee;
import br.com.api.timesheet.entity.TimesheetRegister;
import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import br.com.api.timesheet.exception.BusinessException;
import br.com.api.timesheet.repository.TimesheetRegisterRepository;
import br.com.api.timesheet.resource.timesheetregister.TimesheetRequest;
import br.com.api.timesheet.service.BonusService;
import br.com.api.timesheet.service.EmployeeService;
import br.com.api.timesheet.service.TimesheetRegisterService;
import br.com.api.timesheet.utils.DateUtils;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TimesheetRegisterServiceImpl implements TimesheetRegisterService {

  public static final String SEPARATOR_CHARACTER = "-";
  public static final int TOTAL_HOURS_PER_MONTH = 220;

  @Autowired
  private TimesheetRegisterRepository timesheetRegisterRepository;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private BonusService bonusService;

  /**
   * Save timesheet register.
   * @param request - request
   * @return
   */
  public TimesheetRegister save(TimesheetRequest request) {
    verifyIfHourIsTyped(request);
    verifyIfRegisterExists(request);
    TimesheetRegister register = getTimeSheetRegister(request);
    verifySumula90(request, register);
    return timesheetRegisterRepository.save(register);
  }

  public TimesheetRegister findById(Long id) {
    return timesheetRegisterRepository.findById(id)
            .orElseThrow(() -> new BusinessException("error-timesheet-1", HttpStatus.BAD_REQUEST));
  }

  public void delete(Long id) {
    timesheetRegisterRepository.delete(findById(id));
  }

  public Collection<TimesheetReport> listReport(Long employee, Integer year, Integer month) {
    return timesheetRegisterRepository.listReport(employee, year, month);
  }

  /**
   * List daily report.
   * @param employee - employee
   * @param year - year
   * @param month - month
   * @param asc - asc
   * @return
   */
  public Collection<TimesheetDailyReport> listDailyReport(Long employee, Integer year,
      Integer month, boolean asc) {
    List<TimesheetDailyReport> dailyReport = new ArrayList<>();
    List<TimesheetRegister> registers = asc
            ? timesheetRegisterRepository.findByEmployeeAndPeriodAsc(employee, year, month)
            : timesheetRegisterRepository.findByEmployeeAndPeriodDesc(employee, year, month);
    setReport(dailyReport, registers);
    return dailyReport;
  }

  /**
   * List docket.
   * @param employeeId - employee id
   * @param year - year
   * @param month - month
   * @return
   */
  public TimesheetDocket listDocket(Long employeeId, Integer year, Integer month) {
    TimesheetDocket timesheetDocket = new TimesheetDocket();
    Collection<TimesheetReport> report = listReport(employeeId, year, month);

    double costPerHour = fetchCostPerHour(report);
    Collection<Bonus> bonuses = fetchBonuses(employeeId, year, month);
    boolean isDangerousness = fetchDangerousness(report);
    setPricesTable(timesheetDocket, costPerHour, bonuses);

    Collection<TimesheetDocketItem> docketItems = new ArrayList<>();
    setExtraHoursCost(report, timesheetDocket, docketItems, isDangerousness);
    setBonuses(docketItems, bonuses);
    setWeeklyRestComplement(docketItems);

    timesheetDocket.setItems(docketItems);

    timesheetDocket.setTotal(timesheetDocket.getItems().stream().mapToDouble(
            TimesheetDocketItem::getTotalCost).sum());

    return timesheetDocket;
  }

  private void verifySumula90(TimesheetRequest request, TimesheetRegister register) {
    if (request.getTimeIn().endsWith("00:00")) {
      register.setSumula90(Duration.ofSeconds(0));
    }
  }

  private void verifyIfHourIsTyped(TimesheetRequest request) {
    if (request.getType().equals(TimesheetTypeEnum.REGULAR)
            && request.getTimeIn().endsWith("00:00")) {
      throw new BusinessException("error-timesheet-3", HttpStatus.BAD_REQUEST);
    }
  }

  private void verifyIfRegisterExists(TimesheetRequest request) {
    DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
    LocalDateTime startDate = parse(request.getTimeIn(), formatter)
            .withHour(0).withMinute(0).withSecond(0);
    LocalDateTime endDate = startDate.withHour(23).withMinute(59).withSecond(59);
    Optional<Long> employeeId = request.getEmployeeId();
    if (employeeId.isPresent()) {
      List<TimesheetRegister> registers = timesheetRegisterRepository
              .findByEmployeeAndTimeIn(employeeId.get(), request.getYearReference(),
                      request.getMonthReference(),
              startDate, endDate);
      if (!registers.isEmpty()) {
        throw new BusinessException("error-timesheet-2", HttpStatus.BAD_REQUEST);
      }
    }

  }

  private void setReport(List<TimesheetDailyReport> dailyReport,
      List<TimesheetRegister> registers) {
    if (!registers.isEmpty()) {
      registers.stream().forEach(register -> {
        TimesheetDailyReport report = new TimesheetDailyReport();
        report.setId(register.getId());
        report.setType(getDescription(register));
        report.setDate(ofPattern(DateUtils.DATE_FORMAT_PT_BR).format(register.getTimeIn()));
        report.setEntry(fetchEntry(register));
        report.setHoursWorked(register.getHoursWorked());
        report.setHoursJourney(register.getHoursJourney());
        report.setHoursAdjustment(register.getHoursAdjustment());
        report.setExtraHours(register.getExtraHours());
        report.setWeeklyRest(register.getWeeklyRest());
        report.setSumula90(register.getSumula90());
        report.setNightShift(register.getNightShift());
        report.setPaidNightTime(register.getPaidNightTime());
        dailyReport.add(report);
      });
    }
  }

  private String getDescription(TimesheetRegister register) {
    if (StringUtils.isNotBlank(register.getNotes())) {
      return register.getNotes();
    }
    return register.getType().getDescription();
  }

  private void setExtraHoursCost(Collection<TimesheetReport> report,
      TimesheetDocket docket, Collection<TimesheetDocketItem> docketItems,
      boolean isDangerousness) {
    docketItems.add(new TimesheetDocketItem(REGULAR_HOURS.getCode(), REGULAR_HOURS.getDescription(),
            getTotalHoursWorked(report), docket.getRegularPrice()));
    docketItems.add(new TimesheetDocketItem(WEEKLY_REST.getCode(), WEEKLY_REST.getDescription(),
            getTotalWeeklyRest(report), docket.getRegularPrice()));
    docketItems.add(new TimesheetDocketItem(WEEKLY_REST_COMPLEMENT.getCode(),
            WEEKLY_REST_COMPLEMENT.getDescription(), 0.00, 0));
    docketItems.add(new TimesheetDocketItem(EXTRA_HOURS_PART.getCode(),
            EXTRA_HOURS_PART.getDescription(), getTotalExtraHoursPart(report),
            docket.getFiftyPercent()));
    docketItems.add(new TimesheetDocketItem(EXTRA_HOURS_FULL.getCode(),
            EXTRA_HOURS_FULL.getDescription(), getTotalExtraHoursFull(report),
            docket.getHundredPercent()));
    docketItems.add(new TimesheetDocketItem(SUMULA_90.getCode(),
            SUMULA_90.getDescription(), getTotalSumula90(report), docket.getFiftyPercent()));
    docketItems.add(new TimesheetDocketItem(NIGHT_SHIFT.getCode(),
            NIGHT_SHIFT.getDescription(), getTotalNightShift(report), docket.getTwentyPercent()));
    if (isDangerousness) {
      docketItems.add(new TimesheetDocketItem(DANGEROUSNESS.getCode(),
              DANGEROUSNESS.getDescription(), getTotalDangerousness(docketItems)));
    }
    setPaidNightTimeCost(report, docket, docketItems);
  }

  private void setWeeklyRestComplement(Collection<TimesheetDocketItem> docketItems) {
    double totalWeeklyRestComplement =
            docketItems.stream().filter(item ->
                    !item.getTypeCode().equals(REGULAR_HOURS.getCode())
                            && !item.getTypeCode().equals(WEEKLY_REST.getCode())
                            && !item.getTypeCode().equals(WEEKLY_REST_COMPLEMENT.getCode()))
                    .mapToDouble(TimesheetDocketItem::getTotalCost).sum();

    double regularHours = docketItems.stream().filter(item ->
            item.getTypeCode().equals(REGULAR_HOURS.getCode()))
            .mapToDouble(TimesheetDocketItem::getTotalCost).sum();
    double weeklyRest = docketItems.stream().filter(item ->
            item.getTypeCode().equals(WEEKLY_REST.getCode()))
            .mapToDouble(TimesheetDocketItem::getTotalCost).sum();

    if (regularHours > BigInteger.ZERO.intValue()) {
      totalWeeklyRestComplement = totalWeeklyRestComplement / regularHours * weeklyRest;
      Optional<TimesheetDocketItem> items = docketItems.stream().filter(
          item -> item.getTypeCode().equals(WEEKLY_REST_COMPLEMENT.getCode())).findFirst();
      if (items.isPresent()) {
        items.get().setTotalCost(totalWeeklyRestComplement);
      }
    }
  }

  private void setPaidNightTimeCost(Collection<TimesheetReport> report, TimesheetDocket docket,
      Collection<TimesheetDocketItem> docketItems) {
    long totalPaidNightTime = getTotalPaidNightTime(report);
    Double paidNightTimeDecimal = convertNanosToDecimalHours(totalPaidNightTime);
    Double paidNightTimeCost = (paidNightTimeDecimal * docket.getRegularPrice())
            + (paidNightTimeDecimal * docket.getFiftyPercent() * 0.5);
    docketItems.add(new TimesheetDocketItem(PAID_NIGHT_TIME.getCode(),
            PAID_NIGHT_TIME.getDescription(), paidNightTimeCost, totalPaidNightTime));
  }

  private void setPricesTable(TimesheetDocket timesheetDocket, double costPerHour,
      Collection<Bonus> bonuses) {
    timesheetDocket.setRegularPrice(costPerHour);
    timesheetDocket.setFiftyPercent(getFiftyPercentCost(costPerHour, bonuses));
    timesheetDocket.setHundredPercent(timesheetDocket.getFiftyPercent() * 2);
    timesheetDocket.setTwentyPercent(timesheetDocket.getHundredPercent() * 0.2);
  }

  private double getFiftyPercentCost(Double regularPrice, Collection<Bonus> bonuses) {
    Double bonusAmount = !bonuses.isEmpty() ? bonuses.stream().mapToDouble(Bonus::getCost).sum()
            : Double.valueOf(BigInteger.ZERO.intValue());
    return (regularPrice * TOTAL_HOURS_PER_MONTH + bonusAmount) / TOTAL_HOURS_PER_MONTH;
  }

  private TimesheetRegister getTimeSheetRegister(TimesheetRequest request) {
    final DateTimeFormatter formatter = ofPattern(DateUtils.DATE_TIME_FORMAT);
    TimesheetRegister register = new TimesheetRegister();
    request.getId().ifPresent(register::setId);
    Optional<Long> employeeId = request.getEmployeeId();
    if (employeeId.isPresent()) {
      Employee employee = employeeService.findById(employeeId.get());
      register.setEmployee(employee);
    }
    register.setMonthReference(request.getMonthReference());
    register.setYearReference(request.getYearReference());
    register.setCostHour(Double.valueOf(request.getCostHour()));
    register.setType(request.getType());
    register.setTimeIn(parse(request.getTimeIn(), formatter));
    register.setLunchStart(parse(request.getLunchStart(), formatter));
    register.setLunchEnd(parse(request.getLunchEnd(), formatter));
    register.setTimeOut(parse(request.getTimeOut(), formatter));
    register.setHoursJourney(ofSeconds(LocalTime.parse(request.getHoursJourney(),
            ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
    register.setSumula90(ofSeconds(LocalTime.parse(request.getSumula90(),
            ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
    register.setDangerousness(request.isDangerousness());
    register.setNotes(request.getNotes());
    register.setPeriod(request.getPeriod());
    register.setHoursAdjustment(ofSeconds(LocalTime.parse(request.getHoursAdjustment(),
            ofPattern(DateUtils.TIME_FORMAT)).toSecondOfDay()));
    return register;
  }

  private String fetchEntry(TimesheetRegister register) {
    DateTimeFormatter formatter = ofPattern(DateUtils.TIME_FORMAT);
    return formatter.format(register.getTimeIn()).concat(SEPARATOR_CHARACTER)
            .concat(formatter.format(register.getLunchStart())).concat(SEPARATOR_CHARACTER)
            .concat(formatter.format(register.getLunchEnd())).concat(SEPARATOR_CHARACTER)
            .concat(formatter.format(register.getTimeOut()));
  }

  private double fetchCostPerHour(Collection<TimesheetReport> report) {
    OptionalDouble optionalDouble = report.stream().mapToDouble(
            TimesheetReport::getCostHour).max();
    return optionalDouble.isPresent() ? optionalDouble.getAsDouble() : Double.valueOf(0);
  }

  private boolean fetchDangerousness(Collection<TimesheetReport> report) {
    if (!report.isEmpty()) {
      Optional<TimesheetReport> timesheetReport = report.stream().findFirst();
      return timesheetReport.map(TimesheetReport::isDangerousness).orElse(false);
    }
    return false;
  }

  private long getTotalPaidNightTime(Collection<TimesheetReport> report) {
    return report.stream().mapToLong(TimesheetReport::getPaidNightTime).sum();
  }

  private long getTotalNightShift(Collection<TimesheetReport> report) {
    return report.stream().mapToLong(TimesheetReport::getNightShift).sum();
  }

  private long getTotalSumula90(Collection<TimesheetReport> report) {
    return report.stream().mapToLong(TimesheetReport::getSumula90).sum();
  }

  private long getTotalExtraHoursFull(Collection<TimesheetReport> report) {
    return report.stream()
            .filter(r -> r.getType().equals(TimesheetTypeEnum.DAY_OFF)
                    || r.getType().equals(TimesheetTypeEnum.HOLIDAY))
            .mapToLong(TimesheetReport::getExtraHours).sum();
  }

  private long getTotalExtraHoursPart(Collection<TimesheetReport> report) {
    return report.stream()
            .filter(r -> r.getType().equals(TimesheetTypeEnum.REGULAR))
            .mapToLong(TimesheetReport::getExtraHours).sum();
  }

  private long getTotalWeeklyRest(Collection<TimesheetReport> report) {
    return report.stream().mapToLong(TimesheetReport::getWeeklyRest).sum();
  }

  private long getTotalHoursWorked(Collection<TimesheetReport> report) {
    return report.stream()
            .filter(r -> r.getType().equals(TimesheetTypeEnum.REGULAR))
            .mapToLong(TimesheetReport::getHoursWorked).sum();
  }

  private Collection<Bonus> fetchBonuses(Long employeeId, Integer year, Integer month) {
    return bonusService.findByEmployeeAndPeriod(employeeId, year, month);
  }

  private void setBonuses(Collection<TimesheetDocketItem> dockets, Collection<Bonus> bonuses) {
    if (!bonuses.isEmpty()) {
      bonuses.stream().forEach(bonus ->
              dockets.add(new TimesheetDocketItem(bonus.getCode(),
                      bonus.getDescription(), bonus.getCost())));
    }
  }

  private double getTotalDangerousness(Collection<TimesheetDocketItem> docketItems) {
    double regularHours = docketItems.stream().filter(item ->
            item.getTypeCode().equals(REGULAR_HOURS.getCode()))
            .mapToDouble(TimesheetDocketItem::getTotalCost).sum();
    double weeklyRest = docketItems.stream().filter(item ->
            item.getTypeCode().equals(WEEKLY_REST.getCode()))
            .mapToDouble(TimesheetDocketItem::getTotalCost).sum();
    return (regularHours + weeklyRest) * 0.30;
  }

}
