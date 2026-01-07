import request from '@/utils/request'

// 查询Tinc内网集群管理列表
export function listTincNetworkMange(query) {
  return request({
    url: '/TincNetworkMange/TincNetworkMange/list',
    method: 'get',
    params: query
  })
}

// 查询Tinc内网集群管理详细
export function getTincNetworkMange(id) {
  return request({
    url: '/TincNetworkMange/TincNetworkMange/' + id,
    method: 'get'
  })
}

// 新增Tinc内网集群管理
export function addTincNetworkMange(data) {
  return request({
    url: '/TincNetworkMange/TincNetworkMange',
    method: 'post',
    data: data
  })
}

// 修改Tinc内网集群管理
export function updateTincNetworkMange(data) {
  return request({
    url: '/TincNetworkMange/TincNetworkMange',
    method: 'put',
    data: data
  })
}

// 删除Tinc内网集群管理
export function delTincNetworkMange(id) {
  return request({
    url: '/TincNetworkMange/TincNetworkMange/' + id,
    method: 'delete'
  })
}
