package com.example.timeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple Time App - メインアプリケーションクラス
 * サーバー時刻を表示するシンプルなWebアプリケーション
 */
@SpringBootApplication
public class TimeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeAppApplication.class, args);
    }
}
