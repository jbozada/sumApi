package com.example.sumapi.service;

import com.example.sumapi.dto.SumRequest;
import com.example.sumapi.dto.SumResponse;
import com.example.sumapi.exception.ConfigurationNotFoundException;
import com.example.sumapi.exception.InvalidInputException;
import com.example.sumapi.service.impl.SumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SumServiceTest {

    @Mock
    private SumPublisher sumPublisher;

    @Mock
    private ConfigurationService configurationService;

    @InjectMocks
    private SumServiceImpl sumService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateAndSend_success() {
        SumRequest request = new SumRequest(10, 20);
        int percentage = 50;

        when(configurationService.getConfigurationValue("percentage")).thenReturn(percentage);

        SumResponse response = sumService.calculateAndSend(request);

        assertNotNull(response);
        assertEquals(15.00, response.getResult());
        verify(configurationService, times(1)).getConfigurationValue("percentage");
    }

    @Test
    void testCalculateAndSend_invalidInput_nullNumber1() {
        SumRequest request = new SumRequest(null, 5);

        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> sumService.calculateAndSend(request));

        assertEquals("Both numbers are required.", exception.getMessage());
        verifyNoInteractions(configurationService);
    }

    @Test
    void testCalculateAndSend_invalidInput_nullNumber2() {
        SumRequest request = new SumRequest(5, null);

        InvalidInputException exception = assertThrows(InvalidInputException.class,
                () -> sumService.calculateAndSend(request));

        assertEquals("Both numbers are required.", exception.getMessage());
        verifyNoInteractions(configurationService);
    }

    @Test
    void testCalculateAndSend_configurationNotFound() {
        SumRequest request = new SumRequest(5, 5);

        when(configurationService.getConfigurationValue("percentage"))
                .thenThrow(new ConfigurationNotFoundException("Configuration not found"));

        ConfigurationNotFoundException exception = assertThrows(ConfigurationNotFoundException.class,
                () -> sumService.calculateAndSend(request));

        assertEquals("Configuration not found", exception.getMessage());
        verify(configurationService, times(1)).getConfigurationValue("percentage");
    }
}
