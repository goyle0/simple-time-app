<script setup>
import TimeDisplay from './components/TimeDisplay.vue'
</script>

<template>
  <div class="app-container">
    <TimeDisplay />
  </div>
</template>

<style scoped>
.app-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
</style>
