package org.romanconversion.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for RomanService.
 */
class RomanServiceTest {

    // Create an instance of RomanService for testing
    private final RomanService romanService = new RomanService();

    @Test
    void testValidRomanConversion() {
        // Test cases for valid inputs
        assertEquals("I", romanService.getRomanFromInteger(1), "1 should convert to 'I'");
        assertEquals("IV", romanService.getRomanFromInteger(4), "4 should convert to 'IV'");
        assertEquals("IX", romanService.getRomanFromInteger(9), "9 should convert to 'IX'");
        assertEquals("XL", romanService.getRomanFromInteger(40), "40 should convert to 'XL'");
        assertEquals("XC", romanService.getRomanFromInteger(90), "90 should convert to 'XC'");
        assertEquals("DCCC", romanService.getRomanFromInteger(800), "800 should convert to 'DCCC'");
        assertEquals("MCMXCIV", romanService.getRomanFromInteger(1994), "1994 should convert to 'MMCMXCIV'");
        assertEquals("MMMCMXCIX", romanService.getRomanFromInteger(3999), "3999 should convert to 'MMMCMXCIX'");
    }

    @Test
    void testInvalidRomanConversion() {
        // Test cases for invalid inputs
        Exception exception;

        // Null input
        exception = assertThrows(IllegalArgumentException.class, () -> romanService.getRomanFromInteger(null));
        assertEquals("Input number cannot be null.", exception.getMessage());

        // Zero
        exception = assertThrows(IllegalArgumentException.class, () -> romanService.getRomanFromInteger(0));
        assertEquals("Input number must be between 1 and 3999.", exception.getMessage());

        // Negative numbers
        exception = assertThrows(IllegalArgumentException.class, () -> romanService.getRomanFromInteger(-5));
        assertEquals("Input number must be between 1 and 3999.", exception.getMessage());

        // Numbers greater than 3999
        exception = assertThrows(IllegalArgumentException.class, () -> romanService.getRomanFromInteger(4000));
        assertEquals("Input number must be between 1 and 3999.", exception.getMessage());
    }

    @Test
    void testEdgeCases() {
        // Test edge cases for Roman numeral conversion
        assertEquals("I", romanService.getRomanFromInteger(1), "Minimum valid input (1) should convert to 'I'");
        assertEquals("MMMCMXCIX", romanService.getRomanFromInteger(3999), "Maximum valid input (3999) should convert to 'MMMCMXCIX'");
    }
}

