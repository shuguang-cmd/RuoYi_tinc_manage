<template>
  <div class="single-network-control">
    <h2>单网监控面板</h2>
    <p>实时监控单个内网的状态与性能</p>
    
    <!-- 网络选择区域 -->
    <el-card v-if="!selectedNetwork">
      <div class="network-select-container">
        <h3>请选择要查看的内网</h3>
        <el-form :inline="true" class="network-select-form">
          <el-form-item label="服务器">
            <el-select v-model="selectedServer" placeholder="请选择服务器" @change="handleServerChange">
              <el-option
                v-for="server in servers"
                :key="server.value"
                :label="server.label"
                :value="server.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="内网">
            <el-select v-model="selectedNetworkId" placeholder="请选择内网" :disabled="!selectedServer">
              <el-option
                v-for="network in networks"
                :key="network.value"
                :label="network.label"
                :value="network.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="confirmSelection" :disabled="!selectedServer || !selectedNetworkId">确认</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    
    <!-- 网络监控详情区域 -->
    <div v-else>
      <!-- 网络基本信息 -->
      <div class="network-info">
        <h3>网络监控: {{ selectedNetworkName }} <el-tag type="success">在线</el-tag></h3>
        <el-button type="primary" size="small" @click="resetSelection">重新选择</el-button>
      </div>
      
      <!-- 监控指标卡片 -->
      <el-row :gutter="20" style="margin-bottom: 20px;">
        <!-- 响应时间卡片 -->
        <el-col :xs="24" :sm="12" :lg="6">
          <el-card class="monitor-card">
            <div class="card-header">
              <h4>响应时间</h4>
              <el-button type="text" size="small">DTU列表</el-button>
            </div>
            <div class="card-content">
              <div class="circle-chart">
                <div class="circle-bg"></div>
                <div class="circle-progress" :style="{ '--progress': responseTime.progress + '%' }"></div>
                <div class="circle-text">{{ responseTime.value }}</div>
              </div>
              <div class="monitor-details">
                <div class="detail-item">
                  <span class="detail-label">平均响应时间</span>
                  <span class="detail-value">{{ responseTime.avg }} ms</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">最长响应时间</span>
                  <span class="detail-value warning">{{ responseTime.max }} ms</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">最短响应时间</span>
                  <span class="detail-value">{{ responseTime.min }} ms</span>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
        
        <!-- 健康分数卡片 -->
        <el-col :xs="24" :sm="12" :lg="6">
          <el-card class="monitor-card">
            <div class="card-header">
              <h4>健康分数</h4>
              <el-button type="text" size="small">DTU列表</el-button>
            </div>
            <div class="card-content">
              <div class="circle-chart">
                <div class="circle-bg"></div>
                <div class="circle-progress health-score" :style="{ '--progress': healthScore.progress + '%' }"></div>
                <div class="circle-text">{{ healthScore.value }}</div>
              </div>
              <div class="monitor-details">
                <div class="detail-item">
                  <span class="detail-label">通信网络部分</span>
                  <span class="detail-value">{{ healthScore.network }} 分</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">测试服务部分</span>
                  <span class="detail-value">{{ healthScore.test }} 分</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">安全部分</span>
                  <span class="detail-value">{{ healthScore.security }} 分</span>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
        
        <!-- 流量卡片 -->
        <el-col :xs="24" :sm="12" :lg="6">
          <el-card class="monitor-card">
            <div class="card-header">
              <h4>流量</h4>
              <el-button type="text" size="small">DTU列表</el-button>
            </div>
            <div class="card-content">
              <div class="circle-chart">
                <div class="circle-bg"></div>
                <div class="circle-progress traffic" :style="{ '--progress': traffic.progress + '%' }"></div>
                <div class="circle-text">{{ traffic.value }}</div>
              </div>
              <div class="monitor-details">
                <div class="detail-item">
                  <span class="detail-label">上传速率</span>
                  <span class="detail-value">{{ traffic.upload }} Mbps</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">总速率</span>
                  <span class="detail-value">{{ traffic.total }} Mbps</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">接入带宽</span>
                  <span class="detail-value">{{ traffic.bandwidth }} Mbps</span>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
        
        <!-- 节点在线率卡片 -->
        <el-col :xs="24" :sm="12" :lg="6">
          <el-card class="monitor-card">
            <div class="card-header">
              <h4>节点在线率</h4>
              <el-button type="text" size="small">DTU列表</el-button>
            </div>
            <div class="card-content">
              <div class="circle-chart">
                <div class="circle-bg"></div>
                <div class="circle-progress node-rate" :style="{ '--progress': nodeRate.progress + '%' }"></div>
                <div class="circle-text">{{ nodeRate.value }}%</div>
              </div>
              <div class="monitor-details">
                <div class="detail-item">
                  <span class="detail-label">在线节点</span>
                  <span class="detail-value success">{{ nodeRate.online }} 个</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">离线节点</span>
                  <span class="detail-value danger">{{ nodeRate.offline }} 个</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">总计</span>
                  <span class="detail-value">{{ nodeRate.total }} 个</span>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 刷新按钮 -->
      <div class="refresh-container">
        <el-button type="primary" @click="refreshChartData">刷新数据</el-button>
      </div>
      
      <!-- 监控图表区域 -->
      <el-row :gutter="20">
        <!-- 健康百分趋势图表 -->
        <el-col :xs="24" :lg="12">
          <el-card class="chart-card">
            <div class="card-header">
              <h4>健康百分趋势（前7天）</h4>
            </div>
            <div class="card-content">
              <div class="chart-container">
                     <bar-chart ref="healthBarChart" :height="'200px'" />
              </div>
            </div>
          </el-card>
        </el-col>
        
        <!-- 网络中断事件图表 -->
        <el-col :xs="24" :lg="12">
          <el-card class="chart-card">
            <div class="card-header">
              <h4>网络中断事件（前7天）</h4>
            </div>
            <div class="card-content">
              <div class="chart-container">
                <line-chart :chart-data="interruptEventData" :height="'200px'" />
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import LineChart from './LineChart.vue'
import BarChart from './BarChart.vue'
import { listManger } from '@/api/manger/manger'
import { listTincNetworkMange } from '@/api/TincNetworkMange/TincNetworkMange'

export default {
  components: {
    LineChart,
    BarChart
  },
  data() {
    return {
      // 服务器列表
      servers: [],
      // 内网列表
      networks: [],
      // 选中的服务器和内网
      selectedServer: '',
      selectedNetworkId: '',
      selectedNetwork: false,
      selectedNetworkName: '',
      
      // 监控指标数据
      responseTime: {
        value: 69,
        progress: 69,
        avg: 43,
        max: 200,
        min: 43
      },
      healthScore: {
        value: 100,
        progress: 100,
        network: 63,
        test: 100,
        security: 5
      },
      traffic: {
        value: 71,
        progress: 71,
        upload: 1628.8,
        total: 2815.0,
        bandwidth: 10000
      },
      nodeRate: {
        value: 0,
        progress: 0,
        online: 0,
        offline: 6,
        total: 6
      },
      
      // 图表数据
      healthTrendData: [100, 100, 100, 100, 100, 100, 100],
      // 格式化日期标签
      dateLabels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      interruptEventData: {
        expectedData: [],
        actualData: [0, 0, 0, 0, 0, 0, 0]
      }
    }
  },
   created() {
    // 页面加载时获取服务器列表
    this.getServerOptions();
  },
  methods: {
    // 处理服务器选择变化
    /**
     * 获取服务器列表
     * 从服务器集群管理API获取所有可用服务器
     */
    getServerOptions() {
      listManger({}).then(response => {
        this.servers = response.rows.map(server => ({
          value: server.serverName,
          label: server.serverName
        }));
      }).catch(error => {
        console.error('获取服务器列表失败:', error);
        this.$message.error('获取服务器列表失败');
      });
    },
    
    /**
     * 根据服务器获取内网列表
     * 从Tinc内网集群管理API获取指定服务器下的所有内网
     */
    getNetworksByServer(serverName) {
      listTincNetworkMange({ serverName: serverName }).then(response => {
        this.networks = response.rows.map(network => ({
          value: network.id,
          label: network.networkName
        }));
      }).catch(error => {
        console.error('获取内网列表失败:', error);
        this.$message.error('获取内网列表失败');
      });
    },
    
    /**
     * 处理服务器选择变化
     * 当用户选择不同的服务器时，动态加载该服务器下的内网列表
     */
    handleServerChange(serverName) {
      if (serverName) {
        this.getNetworksByServer(serverName);
      } else {
        this.networks = [];
      }
      this.selectedNetworkId = '';
    },
    
    // 确认选择
    confirmSelection() {
       if (!this.selectedServer || !this.selectedNetworkId) {
        return;
      }
      // 根据选中的内网ID获取内网名称
      const network = this.networks.find(n => n.value === this.selectedNetworkId);
      if (network) {
        this.selectedNetworkName = network.label;
        this.selectedNetwork = true;
      }
    },
    
    // 重置选择
    resetSelection() {
      this.selectedServer = '';
      this.selectedNetworkId = '';
      this.selectedNetwork = false;
      this.selectedNetworkName = '';
      this.networks = [];
    },
    // 刷新图表数据
    refreshChartData() {
      // 这里可以添加实际的刷新逻辑，比如从API获取最新数据
      // 目前使用随机数据模拟刷新
      this.healthTrendData = this.healthTrendData.map(() => {
        // 生成95-100之间的随机整数
        return Math.floor(Math.random() * 6) + 95;
      });
      // 随机更新日期标签（实际应用中应该保持日期连续）
      this.dateLabels = this.dateLabels;
      // 可以添加一个加载状态提示
      this.$message.success('数据已刷新');
    }
  }
}
</script>

<style lang="scss" scoped>
.single-network-control {
  padding: 20px;
}

.network-select-container {
  max-width: 600px;
  margin: 0 auto;
  text-align: center;
  padding: 20px;
}

.network-select-container h3 {
  margin-bottom: 30px;
  color: #333;
}

.network-select-form {
  justify-content: center;
}

/* 网络信息区域 */
.network-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
}

/* 监控卡片样式 */
.monitor-card {
  height: 100%;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 10px;
    margin-bottom: 15px;
    border-bottom: 1px solid #f0f0f0;
    
    h4 {
      margin: 0;
      font-size: 16px;
      color: #333;
    }
  }
  
  .card-content {
    padding: 10px 0;
  }
}

/* 圆形图表样式 */
.circle-chart {
  position: relative;
  width: 100px;
  height: 100px;
  margin: 0 auto 15px;
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

.circle-progress.health-score {
  background: conic-gradient(
    #67c23a var(--progress),
    transparent 0%
  );
}

.circle-progress.traffic {
  background: conic-gradient(
    #e6a23c var(--progress),
    transparent 0%
  );
}

.circle-progress.node-rate {
  background: conic-gradient(
    #f56c6c var(--progress),
    transparent 0%
  );
}

.circle-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

/* 监控详情样式 */
.monitor-details {
  margin-top: 15px;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
  font-size: 14px;
}

.detail-label {
  color: #666;
}

.detail-value {
  color: #333;
  font-weight: bold;
}

.detail-value.warning {
  color: #e6a23c;
}

.detail-value.success {
  color: #67c23a;
}

.detail-value.danger {
  color: #f56c6c;
}

/* 图表卡片样式 */
.chart-card {
  height: 100%;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  
  .card-header {
    padding-bottom: 10px;
    margin-bottom: 15px;
    border-bottom: 1px solid #f0f0f0;
    
    h4 {
      margin: 0;
      font-size: 16px;
      color: #333;
    }
  }
  
  .card-content {
    padding: 10px 0;
  }
  
  .chart-container {
    height: 250px;
  }
}
</style>