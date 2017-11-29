package org.paradise.microservice.userpreference.validator;

import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

public class UserPreferencesValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {

        return clazz == UserPreferences.class;
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserPreferences userPreferences = (UserPreferences) target;

        if (Objects.isNull(userPreferences.getPreferences())) {
            errors.rejectValue("preferences", "internal.error");
        }

        ValidationUtils.rejectIfEmpty(errors, "cNumber", "value.empty.error");
    }

}
