package br.com.api.timesheet.dto;

import lombok.Data;

@Data
public class TimesheetDailyReport {

  private Long id;
  private String type;
  private String date;
  private String entry;
  private String hoursWorked;
  private String hoursJourney;
  private String hoursAdjustment;
  private String weeklyRest;
  private String extraHours;
  private String sumula90;
  private String nightShift;
  private String paidNightTime;

}
