package com.example.timeapp.service;

import com.example.timeapp.dto.TimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 時刻取得サービス
 * Asia/Tokyoタイムゾーンで現在時刻を取得する
 */
@Service
@Slf4j
public class TimeService {

    private static final ZoneId ZONE_TOKYO = ZoneId.of("Asia/Tokyo");
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 現在時刻を取得してTimeResponseを返す
     *
     * @return 現在時刻情報を含むTimeResponse
     */
    public TimeResponse getCurrentTime() {
        ZonedDateTime now = ZonedDateTime.now(ZONE_TOKYO);

        TimeResponse response = TimeResponse.builder()
                .currentDateTime(now.format(ISO_FORMATTER))
                .date(now.format(DATE_FORMATTER))
                .time(now.format(TIME_FORMATTER))
                .zone(ZONE_TOKYO.getId())
                .timestamp(now.toInstant().toEpochMilli())
                .build();

        log.debug("Current time retrieved: {}", response.getCurrentDateTime());

        return response;
    }
}
