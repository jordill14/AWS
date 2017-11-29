package org.paradise.microservice.userpreference.validator;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.paradise.microservice.userpreference.domain.UserPreferences;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.io.File;
import java.net.URLClassLoader;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserPreferencesValidatorTest {

    @InjectMocks
    private UserPreferencesValidator userPreferencesValidator;

    private ResourceBundleMessageSource messageSource;

    @Before
    public void setUp() throws Exception {

        messageSource = new ResourceBundleMessageSource();
        messageSource.setBundleClassLoader(new URLClassLoader(ArrayUtils.add(null, new File("").toURI().toURL())));
        messageSource.setBasename("ValidationMessages");
    }

    @Test
    public void supports() throws Exception {
    }

    @Test
    public void validate() throws Exception {

        UserPreferences userPreferences = new UserPreferences();

        Errors errors = new BeanPropertyBindingResult(userPreferences, "userPreferences");

        userPreferencesValidator.validate(userPreferences, errors);

        assertThat("Incorrect error field", errors.getFieldErrors().get(0).getField(), is("preferences"));
        assertThat("Incorrect error code", errors.getAllErrors().get(0).getCode(), is("internal.error"));

        assertThat("Incorrect error field", errors.getFieldErrors().get(1).getField(), is("cNumber"));
        assertThat("Incorrect error code", errors.getAllErrors().get(1).getCode(), is("value.empty.error"));

        assertThat("Incorrect error message",
                getMessage(errors.getAllErrors().get(0).getCode(), new Object[] { errors.getFieldErrors().get(0).getField() }),
                is("An internal error has occurred. [preferences] field can't be NULL."));
        assertThat("Incorrect error message",
                getMessage(errors.getAllErrors().get(1).getCode(), new Object[] { errors.getFieldErrors().get(1).getField() }),
                is("Input field [cNumber] value is empty."));
    }

    private String getMessage(String errorCode, Object[] args) {

        return messageSource.getMessage(errorCode, args, Locale.getDefault());
    }

}