package org.adobemarketing.adobeperformance.controller;

import org.adobemarketing.adobeperformance.RomanService;
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
@RequestMapping("/romannumeral") // Base URL for the API
public class RomanConversion {

    // RomanService is used to perform the conversion logic.
    private final RomanService romanService;

    /**
     * Constructor for RomanConversion.
     * Spring automatically injects the RomanService bean into the controller.
     *
     * @param romanService the Roman numeral conversion service
     */
    public RomanConversion(RomanService romanService){
        this.romanService = romanService;
    }

    /**
     * API endpoint to convert an integer to a Roman numeral.
     * The input is provided as a query parameter named "query".
     *
     * Example:
     * - Request: GET /romannumeral?query=1994
     * - Response: MMCMXCIV
     *
     * @param number the integer to convert (provided as the "query" parameter)
     * @return the Roman numeral representation of the number
     */
    @GetMapping("")// Maps to HTTP GET requests at /romannumeral
    public ResponseEntity<String> getRomanNumeral(@RequestParam(name="query") Integer number){

        try {
            // Call the RomanService to convert the number
            String romanNumeral = romanService.getRomanFromInteger(number);
            return ResponseEntity.ok(romanNumeral); // Return 200 OK with the result
        } catch (IllegalArgumentException e) {
            // Return 400 Bad Request with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
