import request from '@/utils/request'

//读取模型信息
export function readModulesInfo(path) {
  return request({
    url: '/modules/modulesInfo/read',
    method: 'post',
    data: path,
  })
}

// 查询模型信息列表
export function listModulesInfo(query) {
  return request({
    url: '/modules/modulesInfo/list',
    method: 'get',
    params: query
  })
}

// 查询模型信息详细
export function getModulesInfo(moduleId) {
  return request({
    url: '/modules/modulesInfo/' + moduleId,
    method: 'get'
  })
}

// 新增模型信息
export function addModulesInfo(data) {
  return request({
    url: '/modules/modulesInfo',
    method: 'post',
    data: data
  })
}

// 修改模型信息
export function updateModulesInfo(data) {
  return request({
    url: '/modules/modulesInfo',
    method: 'put',
    data: data
  })
}

// 删除模型信息
export function delModulesInfo(moduleId) {
  return request({
    url: '/modules/modulesInfo/' + moduleId,
    method: 'delete'
  })
}
