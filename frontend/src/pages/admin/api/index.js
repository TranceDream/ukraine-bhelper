/*
 * @Author: Linhao Yu
 * @Date: 2022-04-28 22:28:04
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-07 20:55:36
 */
/*
包含应用中所有接口请求函数的模块
*/

import ajax from './ajax'
// 登录
/* export default reqLogin(username, password){
  return ajax('./login', {username, password})
} */

export const reqLogin = (username, password) =>
    ajax('./login', { username, password })

// add user
let BASE = 'http://139.9.231.20:81'
export const reqGetAllUser = (params) => {
    return ajax(BASE + '/user/getUserList', params, 'POST')
}

export const reqDelUser = (user) => {
    return ajax(BASE + '/user/getAllUser', user, 'POST')
}

export const reqUpdateUser = (user) => {
    return ajax(BASE + '/user/updateUser', user, 'POST')
}

export const reqUserDetail = (userId) => {
    return ajax(BASE + '/user/getUserDetail', userId, 'POST')
}


export const reqLockUser = (params) => {
    return ajax(BASE + '/user/manageRole', params, 'POST')
}

export const reqAddRole = (params) => {
    return ajax(BASE + '/user/userEmpowerment', params, 'POST')
}


export const reqGetRoleList = (params) => {
    return ajax(BASE + '/user/getRoleList', params, 'POST')
}