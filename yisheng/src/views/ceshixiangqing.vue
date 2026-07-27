<template>
  <div class="task-detail-container">
    <!-- 1. 顶部核心控制与状态卡片 -->
    <el-card shadow="hover" class="top-header-card">
      <div class="header-main">
        <!-- 左侧：任务基本信息 -->
        <div class="info-meta">
          <div class="title-row">
            <h2 class="task-title">任务详情：{{ mockData.taskInfo.name }}</h2>
            <el-tag :type="getStatusTagType(mockData.taskInfo.status)" effect="dark" round>
              {{ mockData.taskInfo.status }}
            </el-tag>
          </div>
          <div class="sub-meta">
            <span>任务 ID: <code>{{ mockData.taskInfo.id }}</code></span>
            <el-divider direction="vertical" />
            <span>创建时间: {{ mockData.taskInfo.createTime }}</span>
            <el-divider direction="vertical" />
            <span>目标端点: {{ mockData.taskInfo.endpoint }}</span>
          </div>
        </div>

        <!-- 右侧：操作按钮组（取消、重跑、作废、跳转独立详情页） -->
        <div class="action-buttons">
          <!-- 取消：仅运行中可用 -->
          <el-button 
            type="warning" 
            plain 
            :disabled="mockData.taskInfo.status !== 'RUNNING'"
            @click="handleCancel"
          >
            取消任务
          </el-button>

          <!-- 重跑：非运行状态可用 -->
          <el-button 
            type="primary" 
            plain 
            :disabled="mockData.taskInfo.status === 'RUNNING'"
            @click="handleRerun"
          >
            重新运行
          </el-button>

          <!-- 作废：危险操作 -->
          <el-button type="danger" plain @click="handleDiscard">
            作废任务
          </el-button>

          <!-- 跳转独立 Run 详情页 -->
          <el-button type="primary" @click="goToRunDetailPage">
            查看 Run 独立详情页 &gt;
          </el-button>
        </div>
      </div>

      <el-divider class="my-4" />

      <!-- 核心指标汇总大字 -->
      <el-row :gutter="20">
        <el-col :span="6">
          <el-statistic title="当前二分收敛 Mid" :value="mockData.taskInfo.currentMid">
            <template #suffix>concurrency</template>
          </el-statistic>
        </el-col>
        <el-col :span="6">
          <el-statistic title="搜索轮数" :value="`${mockData.taskInfo.stepCount} 轮`" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="最佳吞吐量 (Throughput)" :value="1420.5" :precision="1">
            <template #suffix>tokens/s</template>
          </el-statistic>
        </el-col>
        <el-col :span="6">
          <el-statistic title="首 Token 延迟 (TTFT)" :value="18.4" :precision="1">
            <template #suffix>ms</template>
          </el-statistic>
        </el-col>
      </el-row>
    </el-card>

    <!-- 2. 中部内容导航与主体区域 -->
    <el-card shadow="never" class="main-body-card">
      <template #header>
        <div class="body-header">
          <!-- el-segmented 分段控制器 -->
          <el-segmented
            v-model="currentTab"
            :options="['搜索路径', '搜索列表', '配置信息']"
            size="large"
          />
        </div>
      </template>

      <!-- 视图 1：搜索路径 (el-timeline 时间轴演进) -->
      <div v-if="currentTab === '搜索路径'" class="tab-content">
        <el-timeline>
          <el-timeline-item
            v-for="step in mockData.searchPath"
            :key="step.step"
            :type="step.isSuccess ? 'success' : 'danger'"
            :timestamp="`Step ${step.step} · ${step.time}`"
            placement="top"
          >
            <el-card shadow="hover" class="step-card">
              <div class="step-card-content">
                <div>
                  <strong>测试并发点 (Mid): {{ step.mid }}</strong>
                  <p class="text-gray">搜索区间缩小至: [ {{ step.low }}, {{ step.high }} ]</p>
                </div>
                <div class="step-metrics">
                  <span v-if="step.isSuccess" class="text-success">
                    ✓ 成功 (Throughput: {{ step.throughput }} tokens/s, TTFT: {{ step.ttft }}ms)
                  </span>
                  <span v-else class="text-danger">
                    ✗ 失败 (原因: {{ step.reason }})
                  </span>
                </div>
              </div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>

      <!-- 视图 2：搜索列表 (使用 el-card + el-descriptions 精细化展现各步骤) -->
      <div v-else-if="currentTab === '搜索列表'" class="tab-content">
        <el-card 
          v-for="step in mockData.searchList" 
          :key="step.step" 
          shadow="hover" 
          class="step-desc-card"
        >
          <template #header>
            <div class="step-card-header">
              <span class="step-title">第 {{ step.step }} 轮评估详情</span>
              <el-tag :type="step.status === 'SUCCESS' ? 'success' : 'danger'">
                {{ step.status }}
              </el-tag>
            </div>
          </template>

          <el-descriptions :column="3" border size="small">
            <el-descriptions-item label="测试并发 (Mid)">
              <strong class="highlight-mid">{{ step.mid }}</strong>
            </el-descriptions-item>
            <el-descriptions-item label="收敛区间">
              <code>[{{ step.low }}, {{ step.high }}]</code>
            </el-descriptions-item>
            <el-descriptions-item label="单轮压测耗时">
              {{ step.duration }}s
            </el-descriptions-item>

            <el-descriptions-item label="吞吐量 (Throughput)">
              {{ step.throughput }} tokens/s
            </el-descriptions-item>
            <el-descriptions-item label="首 Token 延迟 (TTFT)">
              {{ step.ttft }} ms
            </el-descriptions-item>
            <el-descriptions-item label="逐 Token 延迟 (TPOT)">
              {{ step.tpot }} ms
            </el-descriptions-item>

            <el-descriptions-item label="运行说明/错误信息" :span="3">
              <span v-if="step.status === 'SUCCESS'" class="text-success">
                运行正常，性能指标达标
              </span>
              <span v-else class="text-danger">
                {{ step.errorMessage }}
              </span>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </div>

      <!-- 视图 3：配置信息 (el-descriptions 结构化配置) -->
      <div v-else-if="currentTab === '配置信息'" class="tab-content">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="任务名称">{{ mockData.config.taskName }}</el-descriptions-item>
          <el-descriptions-item label="关联 Endpoint">{{ mockData.config.endpoint }}</el-descriptions-item>
          <el-descriptions-item label="搜索算法">二分查找 (Binary Search Sweep)</el-descriptions-item>
          <el-descriptions-item label="二分区间范围">[ {{ mockData.config.minParam }}, {{ mockData.config.maxParam }} ]</el-descriptions-item>
          <el-descriptions-item label="收敛容忍度 (Tolerance)">high - low &lt;= 2</el-descriptions-item>
          <el-descriptions-item label="压测引擎">vllmbench (HTTP SSE)</el-descriptions-item>
          <el-descriptions-item label="单次压测超时时间">180 秒</el-descriptions-item>
          <el-descriptions-item label="并发隔离策略">单端点互斥排队</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 当前选中的选项卡
const currentTab = ref('搜索路径')

// Mock 数据结构
const mockData = reactive({
  taskInfo: {
    id: 'sweep_20260724_001',
    name: 'LLM 最大并发吞吐二分搜索',
    status: 'RUNNING', // RUNNING / COMPLETED / CANCELLED / DISCARDED
    createTime: '2026-07-24 10:30:00',
    endpoint: 'http://192.168.1.100:8000',
    currentMid: 128,
    stepCount: 3
  },
  // 视图1：搜索路径
  searchPath: [
    { step: 1, time: '10:30:15', mid: 256, low: 1, high: 512, isSuccess: false, reason: '显存溢出 (OOM)' },
    { step: 2, time: '10:31:02', mid: 128, low: 1, high: 255, isSuccess: true, throughput: 1420.5, ttft: 18.4 },
    { step: 3, time: '10:32:10', mid: 191, low: 129, high: 255, isSuccess: true, throughput: 1100.2, ttft: 42.1 }
  ],
  // 视图2：搜索列表（以描述列表对象方式呈现）
  searchList: [
    { 
      step: 1, mid: 256, low: 1, high: 512, 
      throughput: 0, ttft: 0, tpot: 0, duration: 42, 
      status: 'FAILED', errorMessage: 'CUDA out of memory. Tried to allocate 4.20 GiB' 
    },
    { 
      step: 2, mid: 128, low: 1, high: 255, 
      throughput: 1420.5, ttft: 18.4, tpot: 12.1, duration: 60, 
      status: 'SUCCESS', errorMessage: '' 
    },
    { 
      step: 3, mid: 191, low: 129, high: 255, 
      throughput: 1100.2, ttft: 42.1, tpot: 18.5, duration: 58, 
      status: 'SUCCESS', errorMessage: '' 
    }
  ],
  // 视图3：配置信息
  config: {
    taskName: 'LLM 最大并发吞吐二分搜索',
    endpoint: 'http://192.168.1.100:8000',
    minParam: 1,
    maxParam: 512
  }
})

// 状态 Tag 样式逻辑
const getStatusTagType = (status) => {
  switch (status) {
    case 'RUNNING': return 'primary'
    case 'COMPLETED': return 'success'
    case 'CANCELLED': return 'warning'
    case 'DISCARDED': return 'info'
    default: return 'info'
  }
}

// 按钮动作
const handleCancel = () => {
  ElMessageBox.confirm('确定要取消当前正在进行的压测任务吗？', '提示', {
    confirmButtonText: '确定取消',
    cancelButtonText: '暂不取消',
    type: 'warning'
  }).then(() => {
    mockData.taskInfo.status = 'CANCELLED'
    ElMessage.warning('任务已取消')
  })
}

const handleRerun = () => {
  ElMessage.success('已发起重新运行任务...')
  mockData.taskInfo.status = 'RUNNING'
}

const handleDiscard = () => {
  ElMessageBox.confirm('作废后该任务数据将不再计入最佳参数计算，确定作废？', '警告', {
    confirmButtonText: '确定作废',
    cancelButtonText: '取消',
    type: 'error'
  }).then(() => {
    mockData.taskInfo.status = 'DISCARDED'
    ElMessage.info('任务已作废')
  })
}

const goToRunDetailPage = () => {
  ElMessage.info(`跳转到特定的 Run 独立页面，Run ID: ${mockData.taskInfo.id}`)
}
</script>

<style scoped>
.task-detail-container {
  padding: 24px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.top-header-card, .main-body-card {
  border-radius: 8px;
  margin-bottom: 20px;
}

.header-main {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.title-row {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 8px;
}

.task-title {
  margin: 0;
  font-size: 20px;
  font-weight: 600;
  color: #303133;
}

.sub-meta {
  font-size: 13px;
  color: #909399;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.body-header {
  display: flex;
  justify-content: flex-start;
}

.tab-content {
  padding-top: 10px;
}

/* 搜索路径样式 */
.step-card {
  border-radius: 6px;
}

.step-card-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 搜索列表（描述卡片）样式 */
.step-desc-card {
  margin-bottom: 16px;
  border-radius: 8px;
}

.step-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.step-title {
  font-weight: 600;
  font-size: 15px;
}

.highlight-mid {
  color: #409eff;
  font-size: 14px;
}

/* 通用字体颜色辅助类 */
.text-gray {
  color: #909399;
  font-size: 13px;
  margin: 4px 0 0 0;
}

.text-success {
  color: #67c23a;
  font-weight: 600;
}

.text-danger {
  color: #f56c6c;
  font-weight: 600;
}

.my-4 {
  margin: 16px 0;
}
</style>