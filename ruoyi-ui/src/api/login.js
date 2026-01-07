import request from '@/utils/request'

/**
 * 登录方法
 * @param {String} username - 用户名
 * @param {String} password - 密码
 * @param {String} code - 验证码
 * @param {String} uuid - 验证码UUID
 * @returns {Promise} - 登录结果
 */
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    headers: {
      isToken: false,
      repeatSubmit: false
    },
    method: 'post',
    data: data
  })
}

/**
 * 注册方法
 * @param {Object} data - 注册数据
 * @returns {Promise} - 注册结果
 */
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

/**
 * 获取用户详细信息
 * @returns {Promise} - 用户信息
 */
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

/**
 * 退出方法
 * @returns {Promise} - 退出结果
 */
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

/**
 * 获取验证码
 * @returns {Promise} - 验证码图片
 */
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}