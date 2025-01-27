package org.romanconversion.server;

import org.romanconversion.metrics.CustomMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Service to convert an integer to its Roman numeral representation.
 * <p>
 * Roman numerals use the following symbols:
 * - I (1), V (5), X (10), L (50), C (100), D (500), M (1000).
 * - Numbers are represented by combining these symbols in a specific order.
 * <p>
 * Example:
 * - Input: 1994
 * - Output: "MCMXCIV"
 */
@Service
public class RomanService {

    private static final Logger logger = LoggerFactory.getLogger(RomanService.class);

    private final CustomMetrics customMetrics;

    // Array of Roman numeral symbols in descending order of their values
    private static final String[] ROMAN_VALUES = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    // Array of integer values corresponding to the Roman numeral symbols
    private static final int[] INTEGER_VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};


    public RomanService(CustomMetrics customMetrics) {
        this.customMetrics = customMetrics;
    }

    /**
     * Converts an integer to its Roman numeral equivalent.
     *
     * @param number the integer to be converted (must be between 1 and 3999)
     * @return the Roman numeral representation of the number
     * @throws IllegalArgumentException if the input number is out of the valid range
     *                                  <p>
     *                                  Example usage:
     *                                  <pre>
     *                                  RomanService service = new RomanService();
     *                                  String roman = service.getRomanFromInteger(1994); // Returns "MCMXCIV"
     *                                  </pre>
     */
    public String getRomanFromInteger(Integer number) {

        logger.info("Received request to convert number: {}", number);


        // Validate input to ensure it is within the acceptable range
        if (number == null) {
            logger.error("Number is null , hence cannot convert to Roman");
            throw new IllegalArgumentException("Input number cannot be null.");
        }
        if (number <= 0 || number > 3999) {
            logger.error("Invalid input: {}", number);
            throw new IllegalArgumentException("Input number must be between 1 and 3999.");
        }

        // StringBuilder is used for efficient concatenation of the Roman numeral string
        StringBuilder romanNumeralBuilder = new StringBuilder();

        // Loop through the integer values and symbols
        for (int i = 0; i < INTEGER_VALUES.length && number > 0; i++) {
            // While the current value can be subtracted from the number
            while (INTEGER_VALUES[i] <= number) {
                // Subtract the current value from the number
                number = number - INTEGER_VALUES[i];
                // Append the corresponding Roman numeral symbol
                romanNumeralBuilder.append(ROMAN_VALUES[i]);

                logger.debug("Appended symbol: {} | Remaining number: {}", ROMAN_VALUES[i], number);
            }
        }

        logger.info("Converted Roman numeral: {}", romanNumeralBuilder);
        customMetrics.incrementConversionCounter();

        // Return the resulting Roman numeral string
        return romanNumeralBuilder.toString();
    }

}

