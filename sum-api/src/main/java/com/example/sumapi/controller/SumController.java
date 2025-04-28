package com.example.sumapi.controller;

import com.example.sumapi.dto.SumRecordResponse;
import com.example.sumapi.dto.SumRequest;
import com.example.sumapi.dto.SumResponse;
import com.example.sumapi.entity.SumRecord;
import com.example.sumapi.service.SumRecordService;
import com.example.sumapi.service.SumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for handling sum operations and history retrieval.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Sum API", description = "Endpoints for summing numbers and retrieving sum history")
public class SumController {

    private final SumService sumService;
    private final SumRecordService sumRecordService;

    /**
     * Endpoint to sum two numbers.
     *
     * @param request The request containing the two numbers to sum.
     * @return The sum result.
     */
    @PostMapping("/sum")
    @Operation(
            summary = "Sum two numbers",
            description = "Calculates the sum of two numbers and sends the result asynchronously."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully summed the numbers",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = SumResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Map.class)))
    })
    public ResponseEntity<SumResponse> sum(@RequestBody @Parameter(description = "Request body containing two numbers to sum") SumRequest request) {
        SumResponse response = sumService.calculateAndSend(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to retrieve paginated sum records history.
     *
     * @param page The page number to retrieve (default is 0).
     * @param size The number of records per page (default is 10).
     * @return A paginated list of sum records ordered by most recent first.
     */
    @GetMapping("/history")
    @Operation(
            summary = "Get sum history",
            description = "Retrieves paginated sum operation history ordered by most recent."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the sum history",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameters",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Map.class)))
    })
    public ResponseEntity<Page<SumRecordResponse>> getAllSumRecords(
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number to retrieve") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Number of records per page") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SumRecordResponse> records = sumRecordService.getAllSumRecords(pageable);
        return ResponseEntity.ok(records);
    }

}
