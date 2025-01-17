package org.romanconversion.controller;

import org.romanconversion.server.RomanService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.romanconversion.controller.RomanConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RomanConversion.class)
public class RomanConversionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RomanService romanService;

    @Test
    public void testGetRomanNumeral_ValidInput() throws Exception {
        // Mock the service response
        Mockito.when(romanService.getRomanFromInteger(1994)).thenReturn("MCMXCIV");

        // Perform the GET request and validate the response
        mockMvc.perform(get("/romannumeral?query=1994"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"value\": \"MCMXCIV\"}"));
    }

    @Test
    public void testGetRomanNumeral_InvalidInput() throws Exception {
        // Mock the service to throw an exception for invalid input
        Mockito.when(romanService.getRomanFromInteger(0)).thenThrow(new IllegalArgumentException("Input number must be between 1 and 3999."));

        // Perform the GET request and validate the response
        mockMvc.perform(get("/romannumeral?query=0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"value\": \"Input number must be between 1 and 3999.\" }"));
    }
}

