package com.example.timeapp.controller;

import com.example.timeapp.dto.TimeResponse;
import com.example.timeapp.service.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 時刻APIコントローラー
 * GET /api/v1/time エンドポイントを提供
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TimeController {

    private final TimeService timeService;

    /**
     * 現在時刻を取得するAPI
     *
     * @return 現在時刻情報を含むTimeResponse
     */
    @GetMapping("/time")
    public ResponseEntity<TimeResponse> getCurrentTime() {
        log.info("GET /api/v1/time requested");

        TimeResponse response = timeService.getCurrentTime();

        return ResponseEntity.ok(response);
    }
}
