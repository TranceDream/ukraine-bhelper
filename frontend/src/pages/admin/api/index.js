/*
 * @Author: Linhao Yu
 * @Date: 2022-04-28 22:28:04
 * @Last Modified by:   Linhao Yu
 * @Last Modified time: 2022-04-28 22:28:04
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
let BASE = '127.0.0.1'
export const reqGetAllUser = (params) => {
    // ajax(BASE + '/user/getAllUser', params, 'POST')
    // 模拟返回
    let promise = new Promise((resolve, reject) => {
        let goodres = {
            status: 200,
            data: [
                {
                    key: 1,
                    id: 1,
                    name: '张三',
                    nickname: 'hasuer',
                    role: 'Admin',
                    status: 'on',
                    country: 'China',
                    city: 'Tianjin',
                    createAt: '2022-04-22',
                },
                {
                    key: 2,
                    id: 2,
                    name: '李四',
                    nickname: 'hasuer',
                    role: 'Refugee',
                    status: 'off',
                    country: 'China',
                    city: 'Tianjin',
                    createAt: '2022-04-20',
                },
            ],
        }
        let badres = {
            status: 400,
            data: [],
            errormsg: '这时一条errormessage',
        }
        resolve(goodres)
    })
    return promise
}

export const reqDelUser = (user) => {
    ajax(BASE + '/user/getAllUser', user, 'POST')
}

export const reqUpdateUser = (user) => {
    ajax(BASE + '/user/getAllUser', user, 'POST')
}
