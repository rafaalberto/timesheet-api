package br.com.api.timesheet.dto;

import static br.com.api.timesheet.utils.DateUtils.TIME_FORMAT;
import static br.com.api.timesheet.utils.DateUtils.convertNanosToDecimalHours;
import static java.text.NumberFormat.getCurrencyInstance;
import static java.time.Duration.ofNanos;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

import java.util.Locale;
import lombok.Data;

@Data
public class TimesheetDocketItem {

  private String typeCode;
  private String typeDescription;
  private long totalHours;
  private double costPerHour;
  private double totalCost;

  /**
   * Receive items.
   * @param typeCode - type code
   * @param typeDescription - type description
   * @param totalHours - total hours
   * @param costPerHour - cost per hour
   */
  public TimesheetDocketItem(String typeCode, String typeDescription, long totalHours,
      double costPerHour) {
    this.typeCode = typeCode;
    this.typeDescription = typeDescription;
    this.totalHours = totalHours;
    this.costPerHour = costPerHour;
    this.totalCost = convertNanosToDecimalHours(totalHours) * costPerHour;
  }

  /**
   * Receive items.
   * @param typeCode - type code
   * @param typeDescription - type description
   * @param totalCost - total cost
   * @param totalHours - total hours
   */
  public TimesheetDocketItem(String typeCode, String typeDescription, double totalCost,
      long totalHours) {
    this.typeCode = typeCode;
    this.typeDescription = typeDescription;
    this.totalCost = totalCost;
    this.totalHours = totalHours;
  }

  /**
   * Receive items.
   * @param typeCode - type code
   * @param typeDescription - type description
   * @param totalCost - total cost
   */
  public TimesheetDocketItem(String typeCode, String typeDescription, double totalCost) {
    this.typeCode = typeCode;
    this.typeDescription = typeDescription;
    this.totalCost = totalCost;
  }

  public String getTotalHoursFormatted() {
    return formatDuration(ofNanos(totalHours).toMillis(), TIME_FORMAT);
  }

  public String getTotalCostFormatted() {
    return getCurrencyInstance(new Locale("pt", "BR")).format(getTotalCost());
  }

}
