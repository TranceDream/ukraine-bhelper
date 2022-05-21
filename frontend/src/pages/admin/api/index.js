/*
 * @Author: Linhao Yu
 * @Date: 2022-04-28 22:28:04
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-15 23:31:42
 */
/*
包含应用中所有接口请求函数的模块
*/

import ajax from './ajax'
// add user
let BASE = 'http://139.9.231.20:81'
// let BASE = 'http://192.168.3.2:9001'
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

// 修改密码
export const reqChangePwd = (params) => {
    return ajax(BASE + '/user/changePwd', params, 'POST')
}

// ! 房源 ------------------------------------------


// 获取房源信息
export const reqHouseList = (params) => {
    return ajax(BASE + '/house/selectHouseAdmin', params, 'POST')
}

// 修改房源信息

export const reqUpdateHouse = (params) => {
    return ajax(BASE + '/house/updateinfo', params, 'POST')
}

// 删除房源

export const reqDelHouse = (params) => {
    return ajax(BASE + '/house/deleteinfo', params, 'POST')
}

// !举报

//获取举报信息

export const reqReportList = (params) => {
    return ajax(BASE + '/report/getReportList', params, 'POST')
}

// 管理员获取举报类别列表
export const reqObjtypeList = (params) => {
    return ajax(BASE + '/report/getObjtypeList', params, 'POST')
}

// 获取举报组详情信息
export const reqReportDetail = (params) => {
    return ajax(BASE + '/report/getReportDetail', params, 'POST')
}

// 处理举报
export const reqDoAudit = (params) => {
    return ajax(BASE + '/report/doAudit', params, 'POST')
}

// !模块
export const reqModuleList = (params) => {
    return ajax(BASE + '/user/getModuleList', params, 'POST')
}

// 获取列表
export const reqMenuPermissionData = (params) => {
    return ajax(BASE + '/user/getPermissionList', params, 'POST')
}


// 管理员增加模块
export const reqAddPermission = (params) => {
    return ajax(BASE + '/user/addPermission', params, 'POST')
}


// 管理员更新模块
export const reqUpdatePermission = (params) => {
    return ajax(BASE + '/user/updatePermission', params, 'POST')
}

//!系统。。。。。。。。。。。。。。。。。。。。。。。。。
//管理员获取类别列表下拉框
export const reqAdminObjtypeList = (params) => {
    return ajax(BASE + '/sys/getAdminObjtypeList', params, 'POST')
}   

// 管理员获取日志列表
export const reqSyslogList = (params) => {
    return ajax(BASE + '/sys/getSyslogList', params, 'POST')
}   

//管理员获取操作列表下拉框
export const reqOperationList = (params) => {
    return ajax(BASE + '/sys/getOperationList', params, 'POST')
}   

// ! 新闻

// 管理获取新闻
export const reqSelectArticle = (params) => {
    return ajax(BASE + '/news/selectArticle', params, 'POST')
}   

//管理员审核新闻
export const reqNewsDoAudit = (params) => {
    return ajax(BASE + '/news/doAudit', params, 'POST')
}  

// 删除文章
export const reqDelNews = (params) => {
    return ajax(BASE + '/news/deleteArticle', params, 'POST')
}  

// 获取新闻组列表

export const reqNewsGroups = (params) => {
    return ajax(BASE + '/news/getNewsGroup', params, 'POST')
} 


// 普通编辑者获取articcle
export const reqselectYourArticle = (params) => {
    return ajax(BASE + '/news/selectYourArticle', params, 'POST')
} 