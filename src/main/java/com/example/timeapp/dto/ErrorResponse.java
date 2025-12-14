package com.example.timeapp.dto;

import lombok.Builder;
import lombok.Data;

/**
 * エラーレスポンスDTO
 */
@Data
@Builder
public class ErrorResponse {

    /**
     * HTTPステータスコード
     */
    private int status;

    /**
     * エラーメッセージ
     */
    private String message;

    /**
     * エラー発生日時（ISO 8601形式）
     */
    private String timestamp;

    /**
     * リクエストパス
     */
    private String path;
}
