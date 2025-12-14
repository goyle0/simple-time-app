package com.example.timeapp.controller;

import com.example.timeapp.dto.TimeResponse;
import com.example.timeapp.service.TimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TimeControllerのテスト
 */
@WebMvcTest(TimeController.class)
class TimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeService timeService;

    @Test
    @DisplayName("GET /api/v1/time - 正常系：現在時刻を取得できる")
    void getCurrentTime_ReturnsTimeResponse() throws Exception {
        // Arrange
        TimeResponse mockResponse = TimeResponse.builder()
                .currentDateTime("2025-12-14T18:30:45.123+09:00")
                .date("2025-12-14")
                .time("18:30:45")
                .zone("Asia/Tokyo")
                .timestamp(1734172245123L)
                .build();

        when(timeService.getCurrentTime()).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/v1/time")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentDateTime").value("2025-12-14T18:30:45.123+09:00"))
                .andExpect(jsonPath("$.date").value("2025-12-14"))
                .andExpect(jsonPath("$.time").value("18:30:45"))
                .andExpect(jsonPath("$.zone").value("Asia/Tokyo"))
                .andExpect(jsonPath("$.timestamp").value(1734172245123L));
    }

    @Test
    @DisplayName("GET /api/v1/time - レスポンスがJSON形式である")
    void getCurrentTime_ReturnsJsonContentType() throws Exception {
        // Arrange
        TimeResponse mockResponse = TimeResponse.builder()
                .currentDateTime("2025-12-14T12:00:00.000+09:00")
                .date("2025-12-14")
                .time("12:00:00")
                .zone("Asia/Tokyo")
                .timestamp(1734148800000L)
                .build();

        when(timeService.getCurrentTime()).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/v1/time"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
