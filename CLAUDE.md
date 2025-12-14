# Simple Time App - CLAUDE.md

<!-- AUTO-MANAGED: project-description -->
## Overview

**Simple Time App** - AWS EC2環境で稼働するシンプルな時刻表示Webアプリケーション

### 主要機能
- 画面中央にサーバー時刻（日付・時刻）を表示
- 自動更新機能（1秒ごとにサーバー時刻を取得・更新）
- タイムゾーン: Asia/Tokyo（日本標準時）

### 技術スタック
| レイヤー | 技術 | バージョン |
|---------|------|-----------|
| フロントエンド | Vue.js 3 + Vite | Vue 3.4+, Vite 5.x |
| バックエンド | Java + Spring Boot | Java 21 LTS, Spring Boot 3.2+ |
| ビルドツール | Maven | 3.9+ |
| コンテナ | Docker | Multi-stage build |
| インフラ | AWS EC2 | us-east-1 (バージニア) |
| IDE | IntelliJ IDEA Ultimate | 2024.x |

<!-- END AUTO-MANAGED -->

<!-- AUTO-MANAGED: build-commands -->
## Build & Development Commands

### バックエンド（Spring Boot）
```bash
# ビルド（フロントエンド込み）
mvn clean package

# テスト実行
mvn test

# 開発サーバー起動
mvn spring-boot:run

# テストスキップでビルド
mvn clean package -DskipTests -B
```

### フロントエンド（Vue.js）
```bash
cd frontend

# 依存関係インストール
npm install

# 開発サーバー起動（http://localhost:3000）
npm run dev

# 本番ビルド
npm run build

# リント
npm run lint
```

### Docker
```bash
# イメージビルド
docker build -t simple-time-app:1.0.0 .

# コンテナ起動
docker run -d --name simple-time-app --restart unless-stopped -p 8080:8080 simple-time-app:1.0.0

# ヘルスチェック
curl http://localhost:8080/actuator/health

# 時刻API確認
curl http://localhost:8080/api/v1/time
```

<!-- END AUTO-MANAGED -->

<!-- AUTO-MANAGED: architecture -->
## Architecture

### システム構成図
```
┌─────────────────────────────────────────────────────────────────┐
│                        AWS EC2 (us-east-1)                      │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                   Docker Container                        │  │
│  │  ┌─────────────────────────────────────────────────────┐  │  │
│  │  │           Spring Boot Application (:8080)           │  │  │
│  │  │  ┌─────────────────┐  ┌─────────────────────────┐   │  │  │
│  │  │  │  Static Files   │  │    REST API Controller  │   │  │  │
│  │  │  │  (Vue.js Build) │  │    GET /api/v1/time     │   │  │  │
│  │  │  └─────────────────┘  └─────────────────────────┘   │  │  │
│  │  └─────────────────────────────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

### ディレクトリ構造
```
simple-time-app/
├── pom.xml                          # Mavenビルド設定
├── Dockerfile                       # マルチステージビルド定義
├── .dockerignore
├── .gitignore
├── README.md
├── CLAUDE.md                        # このファイル
│
├── src/
│   ├── main/
│   │   ├── java/com/example/timeapp/
│   │   │   ├── TimeAppApplication.java      # メインクラス
│   │   │   ├── controller/
│   │   │   │   └── TimeController.java      # REST APIコントローラー
│   │   │   ├── service/
│   │   │   │   └── TimeService.java         # 時刻取得ロジック
│   │   │   ├── dto/
│   │   │   │   ├── TimeResponse.java        # APIレスポンスDTO
│   │   │   │   └── ErrorResponse.java       # エラーレスポンスDTO
│   │   │   └── config/
│   │   │       └── WebConfig.java           # Web設定（CORS等）
│   │   └── resources/
│   │       ├── application.yml              # Spring Boot設定
│   │       ├── application-dev.yml          # 開発環境設定
│   │       ├── application-prod.yml         # 本番環境設定
│   │       └── static/                      # Vue.jsビルド成果物
│   └── test/java/com/example/timeapp/
│       └── TimeControllerTest.java
│
└── frontend/                        # Vue.jsプロジェクト
    ├── package.json
    ├── vite.config.js
    ├── index.html
    └── src/
        ├── main.js
        ├── App.vue
        ├── components/
        │   └── TimeDisplay.vue      # 時刻表示コンポーネント
        ├── services/
        │   └── timeApi.js           # API通信サービス
        └── assets/
            └── styles.css
```

### API仕様

| メソッド | パス | 説明 |
|---------|------|------|
| GET | `/api/v1/time` | 現在時刻を取得 |
| GET | `/actuator/health` | ヘルスチェック |

#### GET /api/v1/time レスポンス
```json
{
  "currentDateTime": "2025-12-14T18:30:45.123+09:00",
  "date": "2025-12-14",
  "time": "18:30:45",
  "zone": "Asia/Tokyo",
  "timestamp": 1734172245123
}
```

<!-- END AUTO-MANAGED -->

<!-- AUTO-MANAGED: conventions -->
## Code Conventions

### Java (Backend)
- **パッケージ構造**: `com.example.timeapp.{layer}.{name}`
- **命名規則**: クラス=PascalCase, メソッド/変数=camelCase
- **アノテーション**: Lombok (`@Data`, `@Builder`, `@Slf4j`)
- **DI**: コンストラクタインジェクション推奨
- **ログ形式**: JSON形式で標準出力

### Vue.js (Frontend)
- **コンポーネント**: Composition API (`<script setup>`)
- **命名規則**: コンポーネント=PascalCase, 変数/関数=camelCase
- **スタイル**: Scoped CSS
- **状態管理**: `ref`, `reactive`（Pinia不要）

### 全般
- **インデント**: 2スペース (Vue), 4スペース (Java)
- **文字コード**: UTF-8
- **改行**: LF

<!-- END AUTO-MANAGED -->

<!-- AUTO-MANAGED: patterns -->
## Detected Patterns

### アーキテクチャパターン
- **Single Container Deployment**: フロントエンド・バックエンドを単一JARに統合
- **Static Resource Serving**: Spring BootがVue.jsビルド成果物を配信
- **API Proxy**: 開発時はViteがAPI呼び出しをプロキシ

### 非機能要件
| 要件 | 基準 |
|------|------|
| API応答時間 | < 100ms |
| ユーザビリティ | PC・スマートフォン対応レスポンシブデザイン |
| ログ形式 | JSON形式で標準出力 |
| セキュリティ | コンテナは非rootユーザーで実行 |

### Dockerパターン
- **Multi-stage Build**: frontend-builder → backend-builder → runtime
- **Distroless/Alpine**: 軽量ランタイムイメージ (`eclipse-temurin:21-jre-alpine`)
- **Health Check**: `/actuator/health`エンドポイントで監視

<!-- END AUTO-MANAGED -->

<!-- AUTO-MANAGED: best-practices -->
## Best Practices

### 開発ワークフロー
1. Spring Boot起動 (`mvn spring-boot:run`) → http://localhost:8080
2. Vue.js開発サーバー起動 (`npm run dev`) → http://localhost:3000
3. Viteプロキシにより `/api/*` は自動的に `:8080` へ転送

### デプロイフロー
1. `docker build -t simple-time-app:1.0.0 .`
2. `docker save simple-time-app:1.0.0 > simple-time-app.tar`
3. SCP転送 → EC2上で `docker load` → `docker run`

### IntelliJ IDEA設定
- **Run Configuration**: Spring Boot (`TimeApp Backend`), npm (`Vue Dev Server`)
- **Node.js設定**: `frontend/node_modules/.bin/node` または システムNode.js
- **Vue.jsプラグイン**: 有効化済み

<!-- END AUTO-MANAGED -->

<!-- MANUAL -->
## Custom Notes

プロジェクト固有のメモをここに追加してください。このセクションは自動更新されません。

### TODO
- [ ] IntelliJ IDEAでSpring Bootプロジェクト作成
- [ ] Vue.js 3プロジェクト初期化
- [ ] ローカル環境での動作確認
- [ ] Dockerイメージのサイズ最適化
- [ ] HTTPS対応（AWS ALB または Let's Encrypt）

<!-- END MANUAL -->
