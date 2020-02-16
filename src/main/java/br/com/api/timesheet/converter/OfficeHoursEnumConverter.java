package br.com.api.timesheet.converter;

import br.com.api.timesheet.enumeration.OfficeHoursEnum;
import com.google.common.collect.ImmutableMap;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Converter(autoApply = true)
public class OfficeHoursEnumConverter implements AttributeConverter<OfficeHoursEnum, String> {

    private static final Map<String, OfficeHoursEnum> typeConversions =
            ImmutableMap.copyOf(Stream
                    .of(OfficeHoursEnum.values())
                    .collect(toMap(OfficeHoursEnum::getCode, identity())));

    @Override
    public String convertToDatabaseColumn(OfficeHoursEnum attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public OfficeHoursEnum convertToEntityAttribute(String dbData) {
        return dbData != null ? typeConversions.get(dbData) : null;
    }
}
