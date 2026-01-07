import request from '@/utils/request'

/**
 * 查询参数列表
 * @param {Object} query - 查询条件
 * @returns {Promise} - 参数列表
 */
export function listConfig(query) {
  return request({
    url: '/system/config/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询参数详细
 * @param {Number} configId - 参数ID
 * @returns {Promise} - 参数详情
 */
export function getConfig(configId) {
  return request({
    url: '/system/config/' + configId,
    method: 'get'
  })
}

/**
 * 根据参数键名查询参数值
 * @param {String} configKey - 参数键名
 * @returns {Promise} - 参数值
 */
export function getConfigKey(configKey) {
  return request({
    url: '/system/config/configKey/' + configKey,
    method: 'get'
  })
}

/**
 * 新增参数配置
 * @param {Object} data - 参数配置数据
 * @returns {Promise} - 新增结果
 */
export function addConfig(data) {
  return request({
    url: '/system/config',
    method: 'post',
    data: data
  })
}

/**
 * 修改参数配置
 * @param {Object} data - 参数配置数据
 * @returns {Promise} - 修改结果
 */
export function updateConfig(data) {
  return request({
    url: '/system/config',
    method: 'put',
    data: data
  })
}

/**
 * 删除参数配置
 * @param {Number} configId - 参数ID
 * @returns {Promise} - 删除结果
 */
export function delConfig(configId) {
  return request({
    url: '/system/config/' + configId,
    method: 'delete'
  })
}

/**
 * 刷新参数缓存
 * @returns {Promise} - 刷新结果
 */
export function refreshCache() {
  return request({
    url: '/system/config/refreshCache',
    method: 'delete'
  })
}
