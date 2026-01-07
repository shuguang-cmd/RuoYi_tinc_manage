import request from '@/utils/request'

/**
 * 查询管理员列表
 * @param {Object} query - 查询条件
 * @returns {Promise} - 管理员列表
 */
export function listUser(query) {
  return request({
    url: '/user/user/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询管理员详细
 * @param {Number} id - 管理员ID
 * @returns {Promise} - 管理员详情
 */
export function getUser(id) {
  return request({
    url: '/user/user/' + id,
    method: 'get'
  })
}

/**
 * 新增管理员
 * @param {Object} data - 管理员数据
 * @returns {Promise} - 新增结果
 */
export function addUser(data) {
  return request({
    url: '/user/user',
    method: 'post',
    data: data
  })
}

/**
 * 修改管理员
 * @param {Object} data - 管理员数据
 * @returns {Promise} - 修改结果
 */
export function updateUser(data) {
  return request({
    url: '/user/user',
    method: 'put',
    data: data
  })
}

/**
 * 删除管理员
 * @param {Number} id - 管理员ID
 * @returns {Promise} - 删除结果
 */
export function delUser(id) {
  return request({
    url: '/user/user/' + id,
    method: 'delete'
  })
}
