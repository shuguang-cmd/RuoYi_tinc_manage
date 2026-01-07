import request from '@/utils/request'

/**
 * 查询菜单列表
 * @param {Object} query - 查询条件
 * @returns {Promise} - 菜单列表
 */
export function listMenu(query) {
  return request({
    url: '/system/menu/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询菜单详细
 * @param {Number} menuId - 菜单ID
 * @returns {Promise} - 菜单详情
 */
export function getMenu(menuId) {
  return request({
    url: '/system/menu/' + menuId,
    method: 'get'
  })
}

/**
 * 查询菜单下拉树结构
 * @returns {Promise} - 菜单下拉树结构
 */
export function treeselect() {
  return request({
    url: '/system/menu/treeselect',
    method: 'get'
  })
}

/**
 * 根据角色ID查询菜单下拉树结构
 * @param {Number} roleId - 角色ID
 * @returns {Promise} - 角色菜单下拉树结构
 */
export function roleMenuTreeselect(roleId) {
  return request({
    url: '/system/menu/roleMenuTreeselect/' + roleId,
    method: 'get'
  })
}

/**
 * 新增菜单
 * @param {Object} data - 菜单数据
 * @returns {Promise} - 新增结果
 */
export function addMenu(data) {
  return request({
    url: '/system/menu',
    method: 'post',
    data: data
  })
}

/**
 * 修改菜单
 * @param {Object} data - 菜单数据
 * @returns {Promise} - 修改结果
 */
export function updateMenu(data) {
  return request({
    url: '/system/menu',
    method: 'put',
    data: data
  })
}

/**
 * 删除菜单
 * @param {Number} menuId - 菜单ID
 * @returns {Promise} - 删除结果
 */
export function delMenu(menuId) {
  return request({
    url: '/system/menu/' + menuId,
    method: 'delete'
  })
}