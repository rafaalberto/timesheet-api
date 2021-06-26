package br.com.api.timesheet.converter;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import br.com.api.timesheet.enumeration.ProfileEnum;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProfileEnumConverter implements AttributeConverter<ProfileEnum, String> {

  private static final Map<String, ProfileEnum> typeConversions =
          ImmutableMap.copyOf(Stream
                  .of(ProfileEnum.values())
                  .collect(toMap(ProfileEnum::getCode, identity())));

  @Override
  public String convertToDatabaseColumn(ProfileEnum attribute) {
    return attribute != null ? attribute.getCode() : null;
  }

  @Override
  public ProfileEnum convertToEntityAttribute(String dbData) {
    return dbData != null ? typeConversions.get(dbData) : null;
  }
}
