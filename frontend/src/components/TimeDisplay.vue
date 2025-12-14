<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { fetchCurrentTime } from '../services/timeApi'

// 状態管理
const currentTime = ref(null)
const isLoading = ref(true)
const error = ref(null)

// 更新インターバルID
let intervalId = null

// 時刻取得関数
const updateTime = async () => {
  try {
    const data = await fetchCurrentTime()
    currentTime.value = data
    error.value = null
    isLoading.value = false
  } catch (err) {
    error.value = err.message
    isLoading.value = false
  }
}

// ライフサイクル
onMounted(() => {
  updateTime()
  intervalId = setInterval(updateTime, 1000)
})

onUnmounted(() => {
  if (intervalId) {
    clearInterval(intervalId)
  }
})
</script>

<template>
  <div class="time-display">
    <!-- ローディング状態 -->
    <div v-if="isLoading" class="loading">
      <span class="loading-spinner"></span>
      <p>読み込み中...</p>
    </div>

    <!-- エラー状態 -->
    <div v-else-if="error" class="error">
      <p class="error-icon">&#x26A0;&#xFE0F;</p>
      <p class="error-message">{{ error }}</p>
      <button @click="updateTime" class="retry-button">再試行</button>
    </div>

    <!-- 正常表示 -->
    <div v-else-if="currentTime" class="time-content">
      <p class="date">{{ currentTime.date }}</p>
      <p class="time">{{ currentTime.time }}</p>
      <p class="timezone">{{ currentTime.zone }}</p>
    </div>
  </div>
</template>

<style scoped>
.time-display {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 40px 60px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  text-align: center;
  min-width: 300px;
}

/* 日付表示 */
.date {
  font-size: 1.5rem;
  color: #666;
  margin-bottom: 10px;
}

/* 時刻表示 */
.time {
  font-size: 4rem;
  font-weight: bold;
  color: #333;
  font-family: 'Courier New', monospace;
  letter-spacing: 2px;
}

/* タイムゾーン */
.timezone {
  font-size: 1rem;
  color: #888;
  margin-top: 10px;
}

/* ローディング */
.loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #e0e0e0;
  border-top-color: #667eea;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* エラー */
.error {
  color: #e74c3c;
}

.error-icon {
  font-size: 3rem;
}

.error-message {
  margin: 10px 0;
}

.retry-button {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
}

.retry-button:hover {
  background: #5a6fd6;
}

/* レスポンシブ対応 */
@media (max-width: 480px) {
  .time-display {
    padding: 30px 20px;
    min-width: auto;
    margin: 20px;
  }

  .time {
    font-size: 2.5rem;
  }

  .date {
    font-size: 1.2rem;
  }
}
</style>
