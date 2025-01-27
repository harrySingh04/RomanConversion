package org.romanconversion.controller;

import org.romanconversion.dto.ErrorResponseDto;
import org.romanconversion.dto.ResponseDto;
import org.romanconversion.server.RomanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Roman numeral conversion.
 * This controller handles HTTP requests to convert an integer to its Roman numeral representation.
 */
@RestController
@RequestMapping(value = "/romannumeral", produces = MediaType.APPLICATION_JSON_VALUE) // Base URL for the API
public class RomanConversion {

    private static final Logger logger = LoggerFactory.getLogger(RomanConversion.class);

    // RomanService is used to perform the conversion logic.
    private final RomanService romanService;

    /**
     * Constructor for RomanConversion.
     * Spring automatically injects the RomanService bean into the controller.
     *
     * @param romanService the Roman numeral conversion service
     */
    public RomanConversion(RomanService romanService) {
        this.romanService = romanService;
    }

    /**
     * API endpoint to convert an integer to a Roman numeral.
     * The input is provided as a query parameter named "query".
     * <p>
     * Example:
     * - Request: GET /romannumeral?query=1994
     * - Response: MMCMXCIV
     *
     * @param number the integer to convert (provided as the "query" parameter)
     * @return the Roman numeral representation of the number
     */
    @GetMapping("")// Maps to HTTP GET requests at /romannumeral
    public ResponseEntity<ResponseDto> getRomanNumeral(@RequestParam(name = "query") Integer number) {
        logger.info("Received GET request for number: {}", number);
        try {
            // Call the RomanService to convert the number

            String romanNumeral = romanService.getRomanFromInteger(number);
            logger.info("Successfully converted number: {} to Roman numeral: {}", number, romanNumeral);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(String.valueOf(number), romanNumeral)); // Return 200 OK with the result
        } catch (IllegalArgumentException e) {
            // Return 400 Bad Request with the error message
            logger.error("Error processing request: {}", e.getMessage());
             throw new IllegalArgumentException(e.getMessage());
        }

    }
}
