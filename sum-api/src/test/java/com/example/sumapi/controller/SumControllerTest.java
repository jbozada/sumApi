package com.example.sumapi.controller;

import com.example.sumapi.dto.SumRecordResponse;
import com.example.sumapi.dto.SumRequest;
import com.example.sumapi.dto.SumResponse;
import com.example.sumapi.exception.InvalidInputException;
import com.example.sumapi.service.SumRecordService;
import com.example.sumapi.service.SumService;
import com.example.sumapi.service.impl.SumRecordServiceImpl;
import com.example.sumapi.service.impl.SumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SumControllerTest {

    @Mock
    private SumServiceImpl sumServiceImpl;

    @Mock
    private SumRecordServiceImpl sumRecordServiceImpl;

    @InjectMocks
    private SumController sumController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSum_success() {
        SumRequest request = new SumRequest(5, 10);
        SumResponse expectedResponse = new SumResponse(15.0);

        when(sumServiceImpl.calculateAndSend(request)).thenReturn(expectedResponse);

        ResponseEntity<SumResponse> response = sumController.sum(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse.getResult(), response.getBody().getResult());
        verify(sumServiceImpl, times(1)).calculateAndSend(request);
    }

    @Test
    void testSum_invalidInput() {
        SumRequest request = new SumRequest(null, 10);

        when(sumServiceImpl.calculateAndSend(request)).thenThrow(new InvalidInputException("Both numbers are required."));

        assertThrows(InvalidInputException.class, () -> sumController.sum(request));
        verify(sumServiceImpl, times(1)).calculateAndSend(request);
    }

    @Test
    void testGetAllSumRecords_success() {
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        List<SumRecordResponse> recordList = List.of(
                SumRecordResponse.builder()
                        .request("{\"number1\":5,\"number2\":10}")
                        .response("{\"result\":15.0}")
                        .endpoint("/api/sum")
                        .date(LocalDateTime.of(2024, 4, 1, 12, 0))
                        .codResponse(200)
                        .build(),
                SumRecordResponse.builder()
                        .request("{\"number1\":2,\"number2\":3}")
                        .response("{\"result\":5.0}")
                        .endpoint("/api/sum")
                        .date(LocalDateTime.of(2024, 4, 2, 14, 0))
                        .codResponse(200)
                        .build()
        );

        Page<SumRecordResponse> expectedPage = new PageImpl<>(recordList);

        when(sumRecordServiceImpl.getAllSumRecords(pageable)).thenReturn(expectedPage);

        ResponseEntity<Page<SumRecordResponse>> response = sumController.getAllSumRecords(page, size);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getContent().size());
        verify(sumRecordServiceImpl, times(1)).getAllSumRecords(pageable);
    }
}
