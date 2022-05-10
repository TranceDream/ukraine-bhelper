/*
 * @Author: Linhao Yu
 * @Date: 2022-04-28 22:28:04
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-11 01:15:19
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
// let BASE = 'http://139.9.231.20:81'
let BASE = 'http://192.168.3.2:9001'
export const reqGetAllUser = (params) => {
    return ajax(BASE + '/user/getUserList', params, 'POST')
}

// 删除用户
export const reqDelUser = (user) => {
    return ajax(BASE + '/user/deleteUser', user, 'POST')
}

// 更新用户信息
export const reqUpdateUser = (user) => {
    return ajax(BASE + '/user/updateUser', user, 'POST')
}


// 请求用户详细信息
export const reqUserDetail = (userId) => {
    return ajax(BASE + '/user/getUserDetail', userId, 'POST')
}

// 解冻/冻结用户
export const reqLockUser = (params) => {
    return ajax(BASE + '/user/manageRole', params, 'POST')
}


// 给用户增加角色
export const reqAddRole = (params) => {
    return ajax(BASE + '/user/userEmpowerment', params, 'POST')
}

// 获取角色列表
export const reqGetRoleList = (params) => {
    return ajax(BASE + '/user/getRoleList', params, 'POST')
}

//获取角色详情
export const reqRoleDetail = (params) => {
    return ajax(BASE + '/user/getRoleDetail', params, 'POST')
}

// 权限管理
export const reqPermissionManage = (params) => {
    return ajax(BASE + '/user/managePermission', params, 'POST')
}

// 获取权限列表
export const reqPermissionList = (params) => {
    return ajax(BASE + '/user/getPermissionList', params, 'POST')
}


// 增加新角色
export const reqAddNewRole = (params) => {
    return ajax(BASE + '/user/addRole', params, 'POST')
}

// 更新角色信息

export const reqUpdateRole = (params) => {
    return ajax(BASE + '/user/updateRole', params, 'POST')
}

// ! 房源 ------------------------------------------


// 获取房源信息
export const reqHouseList = (params) => {
    return ajax(BASE + '/house/selectHouseAdmin', params, 'POST')
}