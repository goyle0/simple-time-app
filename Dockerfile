# ============================================
# Stage 1: Frontend Builder (Vue.js)
# ============================================
FROM docker.io/library/node:20-alpine AS frontend-builder

WORKDIR /app/frontend

# package.jsonとpackage-lock.jsonをコピー（キャッシュ効率化）
COPY frontend/package*.json ./

# 依存関係インストール
RUN npm ci --silent

# ソースコードをコピーしてビルド
COPY frontend/ ./
RUN npm run build

# ============================================
# Stage 2: Backend Builder (Spring Boot)
# ============================================
FROM docker.io/library/maven:3.9-eclipse-temurin-21-alpine AS backend-builder

WORKDIR /app

# pom.xmlをコピー（依存関係キャッシュ効率化）
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# ソースコードをコピー
COPY src ./src

# フロントエンドビルド成果物をstaticディレクトリにコピー
COPY --from=frontend-builder /app/frontend/dist ./src/main/resources/static

# Mavenビルド（テストスキップ）
RUN mvn clean package -DskipTests -B

# ============================================
# Stage 3: Runtime
# ============================================
FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runtime

# セキュリティ：非rootユーザーを作成
RUN addgroup -g 1000 appgroup && \
    adduser -u 1000 -G appgroup -D appuser

WORKDIR /app

# JARファイルをコピー
COPY --from=backend-builder /app/target/*.jar app.jar

# 所有権を変更
RUN chown -R appuser:appgroup /app

# 非rootユーザーに切り替え
USER appuser

# ポート公開
EXPOSE 8080

# ヘルスチェック
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# タイムゾーン設定とアプリケーション起動
ENV TZ=Asia/Tokyo
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
