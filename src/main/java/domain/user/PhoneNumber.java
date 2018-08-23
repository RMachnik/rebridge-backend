package domain.user;

import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.math.NumberUtils.isDigits;

@UserDefinedType
@Value
public class PhoneNumber {
    String value;

    public static PhoneNumber fromNumber(String number) {
        checkArgument(isNotBlank(number), "phone can't be empty");
        checkArgument(number.length() >= 9, "phone must have at least 9 digits");
        checkArgument(isDigits(number), "phone should have only digits");
        return new PhoneNumber(number);
    }

    public static PhoneNumber empty() {
        return new PhoneNumber("");
    }
}
