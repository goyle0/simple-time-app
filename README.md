# Simple Time App

AWS EC2環境で稼働するシンプルな時刻表示Webアプリケーション

## 主要機能

- 画面中央にサーバー時刻（日付・時刻）を表示
- 1秒ごとに自動更新
- タイムゾーン: Asia/Tokyo（日本標準時）
- PC・スマートフォン対応レスポンシブデザイン

## 技術スタック

| レイヤー | 技術 | バージョン |
|---------|------|-----------|
| フロントエンド | Vue.js 3 + Vite | Vue 3.4+, Vite 5.x |
| バックエンド | Java + Spring Boot | Java 21 LTS, Spring Boot 3.2+ |
| ビルドツール | Maven | 3.9+ |
| コンテナ | Docker | Multi-stage build |

## クイックスタート

### Docker（推奨）

```bash
# イメージビルド
docker build -t simple-time-app .

# コンテナ起動
docker run -d --name simple-time-app -p 8080:8080 simple-time-app

# ブラウザで確認: http://localhost:8080
```

### ローカル開発

```bash
# バックエンド起動
mvn spring-boot:run

# フロントエンド開発サーバー（別ターミナル）
cd frontend
npm install
npm run dev

# ブラウザで確認: http://localhost:3000
```

## API

| メソッド | パス | 説明 |
|---------|------|------|
| GET | `/api/v1/time` | 現在時刻を取得 |
| GET | `/actuator/health` | ヘルスチェック |

## ディレクトリ構成

```
simple-time-app/
├── frontend/          # Vue.js フロントエンド
├── src/               # Spring Boot バックエンド
├── pom.xml            # Maven設定
└── Dockerfile         # マルチステージビルド
```

## 詳細情報

詳細な仕様・開発ガイドラインは [CLAUDE.md](./CLAUDE.md) を参照してください。
