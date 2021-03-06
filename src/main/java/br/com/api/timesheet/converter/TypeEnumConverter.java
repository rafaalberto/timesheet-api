package br.com.api.timesheet.converter;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import br.com.api.timesheet.enumeration.PeriodEnum;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TypeEnumConverter implements AttributeConverter<PeriodEnum, String> {

  private static final Map<String, PeriodEnum> typeConversions =
          ImmutableMap.copyOf(Stream
                  .of(PeriodEnum.values())
                  .collect(toMap(PeriodEnum::getCode, identity())));

  @Override
  public String convertToDatabaseColumn(PeriodEnum attribute) {
    return attribute != null ? attribute.getCode() : null;
  }

  @Override
  public PeriodEnum convertToEntityAttribute(String dbData) {
    return dbData != null ? typeConversions.get(dbData) : null;
  }
}
