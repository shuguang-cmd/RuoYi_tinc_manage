import request from '@/utils/request'

// 查询Tinc节点集群管理列表
export function listNode_mange(query) {
  return request({
    url: '/node_mange/node_mange/list',
    method: 'get',
    params: query
  })
}

// 查询Tinc节点集群管理详细
export function getNode_mange(id) {
  return request({
    url: '/node_mange/node_mange/' + id,
    method: 'get'
  })
}

// 新增Tinc节点集群管理
export function addNode_mange(data) {
  return request({
    url: '/node_mange/node_mange',
    method: 'post',
    data: data
  })
}

// 修改Tinc节点集群管理
export function updateNode_mange(data) {
  return request({
    url: '/node_mange/node_mange',
    method: 'put',
    data: data
  })
}

// 删除Tinc节点集群管理
export function delNode_mange(id) {
  return request({
    url: '/node_mange/node_mange/' + id,
    method: 'delete'
  })
}
