package org.paradise.microservice.userpreference.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.paradise.microservice.userpreference.exception.handler.GlobalExceptionHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by terrence on 9/5/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserPreferenceControllerTest {

    @InjectMocks
    private UserPreferenceController userPreferenceController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders
                .standaloneSetup(userPreferenceController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getUserPreferencesList() throws Exception {
    }

    @Test
    public void getUserPreferences() throws Exception {
    }

    @Test
    public void createUserPreferences() throws Exception {
    }

}