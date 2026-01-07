<template>
  <div class="network-status-container">
    <h2>网络状态统计</h2>
    <p>实时监控网络状态和健康度</p>
    
    <!-- 网络状态卡片 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <!-- 网络在线状态 -->
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="network-card">
          <div class="card-header">
            <h3>网络在线状态</h3>
          </div>
          <div class="card-content">
            <div class="online-rate">
              <div class="circle-chart">
                <div class="circle-bg"></div>
                <div class="circle-progress" :style="{ '--progress': onlineRate + '%' }"></div>
                <div class="circle-text">
                  <span class="rate-value">{{ onlineRate }}%</span>
                </div>
              </div>
            </div>
            <div class="network-stats">
              <div class="stat-item">
                <span class="stat-label">总网络数</span>
                <span class="stat-value">{{ totalNetworks }}</span>
              </div>
              <div class="stat-item">
                <span class="stat-label">在线网络</span>
                <span class="stat-value online">{{ onlineNetworks }}</span>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <!-- 平均响应时间 -->
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="network-card">
          <div class="card-header">
            <h3>平均响应时间</h3>
          </div>
          <div class="card-content">
            <div class="response-time">
              <div class="time-value">{{ averageResponseTime }}ms</div>
              <div class="time-desc">最近7天平均</div>
            </div>
            <div class="response-chart">
              <div class="chart-container">
                <line-chart :chart-data="responseTimeChartData" :height="'100px'" />
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <!-- 网络健康平均分 -->
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="network-card">
          <div class="card-header">
            <h3>网络健康平均分</h3>
          </div>
          <div class="card-content">
            <div class="health-score">
              <div class="score-value">{{ healthScore }}</div>
              <div class="score-desc">最近7天平均</div>
            </div>
            <div class="health-status">
              <el-tag :type="healthStatusType">{{ healthStatus }}</el-tag>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <!-- 平均故障恢复时间 -->
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card class="network-card">
          <div class="card-header">
            <h3>平均故障恢复时间</h3>
          </div>
          <div class="card-content">
            <div class="recovery-time">
              <div class="time-value">{{ averageRecoveryTime }}分钟</div>
              <div class="time-desc">最近7天平均</div>
            </div>
            <div class="recovery-chart">
              <div class="chart-line"></div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <!-- 网络中断次数趋势 -->
    <el-card class="network-chart-card">
      <div class="card-header">
        <h3>网络中断次数趋势（过去7天）</h3>
      </div>
      <div class="card-content">
        <line-chart :chart-data="interruptChartData" :height="'200px'" />
      </div>
    </el-card>
  </div>
</template>

<script>
import LineChart from './LineChart.vue'

export default {
  components: {
    LineChart
  },
  data() {
    return {
      // 网络在线状态
      totalNetworks: 2,
      onlineNetworks: 2,
      onlineRate: 100,
      
      // 平均响应时间
      averageResponseTime: 50,
      
      // 网络健康分
      healthScore: 85,
      healthStatus: '健康状态良好',
      healthStatusType: 'success',
      
      // 平均故障恢复时间
      averageRecoveryTime: 0,
      
      // 图表数据
      responseTimeChartData: {
        expectedData: [],
        actualData: [45, 52, 48, 55, 49, 53, 50]
      },
      interruptChartData: {
        expectedData: [],
        actualData: [0, 0, 0, 0, 0, 0, 0]
      }
    }
  },
  mounted() {
    // 模拟数据更新
    this.simulateDataUpdate()
  },
  methods: {
    simulateDataUpdate() {
      // 这里可以添加实际的API调用，获取网络状态数据
      // 目前使用模拟数据
      setInterval(() => {
        // 随机更新一些数据
        this.averageResponseTime = Math.floor(Math.random() * 20) + 45
        this.healthScore = Math.floor(Math.random() * 10) + 80
        
        // 更新图表数据
        this.responseTimeChartData.actualData = this.responseTimeChartData.actualData.map(() => {
          return Math.floor(Math.random() * 20) + 40
        })
        
        this.interruptChartData.actualData = this.interruptChartData.actualData.map(() => {
          return Math.floor(Math.random() * 2)
        })
      }, 5000)
    }
  }
}
</script>

<style lang="scss" scoped>
.network-status-container {
  padding: 20px;
}

.network-card {
  height: 100%;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  
  .card-header {
    border-bottom: 1px solid #f0f0f0;
    padding-bottom: 10px;
    margin-bottom: 15px;
    
    h3 {
      margin: 0;
      font-size: 16px;
      color: #333;
    }
  }
  
  .card-content {
    padding: 10px 0;
  }
}

/* 网络在线状态卡片 */
.online-rate {
  display: flex;
  justify-content: center;
  margin-bottom: 15px;
}

.circle-chart {
  position: relative;
  width: 100px;
  height: 100px;
}

.circle-bg {
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #f0f0f0;
}

.circle-progress {
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background: conic-gradient(
    #409eff var(--progress),
    transparent 0%
  );
  mask-image: radial-gradient(circle at center, transparent 60%, black 60%);
  -webkit-mask-image: radial-gradient(circle at center, transparent 60%, black 60%);
}

.circle-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.rate-value {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
}

.network-stats {
  display: flex;
  justify-content: space-around;
}

.stat-item {
  text-align: center;
}

.stat-label {
  display: block;
  font-size: 12px;
  color: #666;
  margin-bottom: 5px;
}

.stat-value {
  display: block;
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.stat-value.online {
  color: #67c23a;
}

/* 平均响应时间卡片 */
.response-time {
  text-align: center;
  margin-bottom: 15px;
}

.time-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.time-desc {
  font-size: 12px;
  color: #666;
}

.chart-container {
  height: 100px;
}

/* 网络健康分卡片 */
.health-score {
  text-align: center;
  margin-bottom: 15px;
}

.score-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.score-desc {
  font-size: 12px;
  color: #666;
}

.health-status {
  text-align: center;
}

/* 平均故障恢复时间卡片 */
.recovery-time {
  text-align: center;
  margin-bottom: 15px;
}

.recovery-chart {
  height: 10px;
  background-color: #f0f0f0;
  border-radius: 5px;
  position: relative;
  overflow: hidden;
}

.chart-line {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #e6a23c;
}

/* 网络中断次数趋势图表 */
.network-chart-card {
  margin-top: 20px;
}
</style>