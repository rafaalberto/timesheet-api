package br.com.api.timesheet.converter;

import br.com.api.timesheet.enumeration.TimesheetTypeEnum;
import com.google.common.collect.ImmutableMap;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Converter(autoApply = true)
public class TimesheetTypeEnumConverter implements AttributeConverter<TimesheetTypeEnum, String> {

  private static final Map<String, TimesheetTypeEnum> typeConversions =
          ImmutableMap.copyOf(Stream
                  .of(TimesheetTypeEnum.values())
                  .collect(toMap(TimesheetTypeEnum::getCode, identity())));

  @Override
  public String convertToDatabaseColumn(TimesheetTypeEnum attribute) {
    return attribute != null ? attribute.getCode() : null;
  }

  @Override
  public TimesheetTypeEnum convertToEntityAttribute(String dbData) {
    return dbData != null ? typeConversions.get(dbData) : null;
  }
}
