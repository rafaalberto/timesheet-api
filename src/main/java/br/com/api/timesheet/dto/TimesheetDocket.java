package br.com.api.timesheet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static java.text.NumberFormat.getCurrencyInstance;

@Getter
@Setter
public class TimesheetDocket {

    @JsonIgnore
    private double regularPrice;

    @JsonIgnore
    private double hundredPercent;

    @JsonIgnore
    private double fiftyPercent;

    @JsonIgnore
    private double twentyPercent;

    @JsonIgnore
    private double total;

    private Collection<TimesheetDocketItem> items;

    public String getRegularPriceFormatted() { return getCurrencyInstance(new Locale("pt", "BR")).format(regularPrice); }
    public String getHundredPercentFormatted() { return getCurrencyInstance(new Locale("pt", "BR")).format(hundredPercent); }
    public String getFiftyPercentFormatted() { return getCurrencyInstance(new Locale("pt", "BR")).format(fiftyPercent); }
    public String getTwentyPercentFormatted() { return getCurrencyInstance(new Locale("pt", "BR")).format(twentyPercent); }
    public String getTotalFormatted() { return getCurrencyInstance(new Locale("pt", "BR")).format(total); }
}
