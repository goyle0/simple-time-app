/**
 * 時刻API通信サービス
 */

const API_BASE_URL = '/api/v1'
const TIMEOUT_MS = 5000

/**
 * 現在時刻を取得
 * @returns {Promise<Object>} 時刻データ
 * @throws {Error} 通信エラー
 */
export const fetchCurrentTime = async () => {
  const controller = new AbortController()
  const timeoutId = setTimeout(() => controller.abort(), TIMEOUT_MS)

  try {
    const response = await fetch(`${API_BASE_URL}/time`, {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
      },
      signal: controller.signal
    })

    clearTimeout(timeoutId)

    if (!response.ok) {
      throw new Error(`サーバーエラー: ${response.status}`)
    }

    const data = await response.json()
    return data
  } catch (err) {
    clearTimeout(timeoutId)

    if (err.name === 'AbortError') {
      throw new Error('リクエストがタイムアウトしました')
    }

    if (err.message.includes('Failed to fetch')) {
      throw new Error('サーバーに接続できません')
    }

    throw err
  }
}
