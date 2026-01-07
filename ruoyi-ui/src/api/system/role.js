import request from '@/utils/request'

/**
 * 查询角色列表
 * @param {Object} query - 查询条件
 * @returns {Promise} - 角色列表
 */
export function listRole(query) {
  return request({
    url: '/system/role/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询角色详细
 * @param {Number} roleId - 角色ID
 * @returns {Promise} - 角色详情
 */
export function getRole(roleId) {
  return request({
    url: '/system/role/' + roleId,
    method: 'get'
  })
}

/**
 * 新增角色
 * @param {Object} data - 角色数据
 * @returns {Promise} - 新增结果
 */
export function addRole(data) {
  return request({
    url: '/system/role',
    method: 'post',
    data: data
  })
}

/**
 * 修改角色
 * @param {Object} data - 角色数据
 * @returns {Promise} - 修改结果
 */
export function updateRole(data) {
  return request({
    url: '/system/role',
    method: 'put',
    data: data
  })
}

/**
 * 角色数据权限
 * @param {Object} data - 角色数据权限
 * @returns {Promise} - 数据权限设置结果
 */
export function dataScope(data) {
  return request({
    url: '/system/role/dataScope',
    method: 'put',
    data: data
  })
}

/**
 * 角色状态修改
 * @param {Number} roleId - 角色ID
 * @param {Number} status - 角色状态
 * @returns {Promise} - 修改结果
 */
export function changeRoleStatus(roleId, status) {
  const data = {
    roleId,
    status
  }
  return request({
    url: '/system/role/changeStatus',
    method: 'put',
    data: data
  })
}

/**
 * 删除角色
 * @param {Number} roleId - 角色ID
 * @returns {Promise} - 删除结果
 */
export function delRole(roleId) {
  return request({
    url: '/system/role/' + roleId,
    method: 'delete'
  })
}

/**
 * 查询角色已授权用户列表
 * @param {Object} query - 查询条件
 * @returns {Promise} - 已授权用户列表
 */
export function allocatedUserList(query) {
  return request({
    url: '/system/role/authUser/allocatedList',
    method: 'get',
    params: query
  })
}

/**
 * 查询角色未授权用户列表
 * @param {Object} query - 查询条件
 * @returns {Promise} - 未授权用户列表
 */
export function unallocatedUserList(query) {
  return request({
    url: '/system/role/authUser/unallocatedList',
    method: 'get',
    params: query
  })
}

/**
 * 取消用户授权角色
 * @param {Object} data - 取消授权数据
 * @returns {Promise} - 取消授权结果
 */
export function authUserCancel(data) {
  return request({
    url: '/system/role/authUser/cancel',
    method: 'put',
    data: data
  })
}

/**
 * 批量取消用户授权角色
 * @param {Object} data - 批量取消授权数据
 * @returns {Promise} - 批量取消授权结果
 */
export function authUserCancelAll(data) {
  return request({
    url: '/system/role/authUser/cancelAll',
    method: 'put',
    data: data
  })
}

/**
 * 授权用户选择
 * @param {Object} data - 授权选择数据
 * @returns {Promise} - 授权选择结果
 */
export function authUserSelectAll(data) {
  return request({
    url: '/system/role/authUser/selectAll',
    method: 'put',
    params: data
  })
}

/**
 * 根据角色ID查询部门树结构
 * @param {Number} roleId - 角色ID
 * @returns {Promise} - 部门树结构
 */
export function deptTreeSelect(roleId) {
  return request({
    url: '/system/role/deptTree/' + roleId,
    method: 'get'
  })
}
