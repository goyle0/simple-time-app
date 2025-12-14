package com.example.timeapp.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 時刻APIレスポンスDTO
 */
@Data
@Builder
public class TimeResponse {

    /**
     * ISO 8601形式の現在日時（タイムゾーン付き）
     * 例: "2025-12-14T18:30:45.123+09:00"
     */
    private String currentDateTime;

    /**
     * 日付（YYYY-MM-DD形式）
     * 例: "2025-12-14"
     */
    private String date;

    /**
     * 時刻（HH:mm:ss形式）
     * 例: "18:30:45"
     */
    private String time;

    /**
     * タイムゾーン
     * 例: "Asia/Tokyo"
     */
    private String zone;

    /**
     * UNIXタイムスタンプ（ミリ秒）
     * 例: 1734172245123
     */
    private long timestamp;
}
