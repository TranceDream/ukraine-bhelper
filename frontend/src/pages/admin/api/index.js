/*
 * @Author: Linhao Yu
 * @Date: 2022-04-28 22:28:04
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-03 17:28:02
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
    // return fetch(BASE + '/user/getUserList?' + new URLSearchParams(params), {
    //     mode: 'no-cors',
    //     method: 'GET',
    //     headers: {
    //         'Content-Type': 'application/json',
    //         'token': 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSMjQAAiUdpdSKAiUrQzNTQ1MLcwMzo1oAr-vTyyAAAAA.-RRwdXvu8GtDRvFHnTF98po8bXgw_H1ipKUb2Pft74IC1RhtcdSIkHVG6BEuVUgHuPAg4OSaPvJP26xhQdjZkQ',
    //     },
    // })
    return ajax(BASE + '/user/getUserList',params,"POST")

    // 模拟返回
    // let promise = new Promise((resolve, reject) => {
    //     let goodres = {
    //         status: 200,
    //         data: [
    //             {
    //                 key: 1,
    //                 id: 1,
    //                 name: '张三',
    //                 role: 'Admin',
    //                 status: 'on',
    //                 country: 'China',
    //                 city: 'Tianjin',
    //                 createAt: '2022-04-22',
    //             },
    //             {
    //                 key: 2,
    //                 id: 2,
    //                 name: '李四',
    //                 role: 'Refugee',
    //                 status: 'off',
    //                 country: 'China',
    //                 city: 'Tianjin',
    //                 createAt: '2022-04-20',
    //             },
    //         ],
    //     }
    //     let badres = {
    //         status: 400,
    //         data: [],
    //         errormsg: '这时一条errormessage',
    //     }
    //     resolve(goodres)
    // })
    // return promise
}

export const reqDelUser = (user) => {
    ajax(BASE + '/user/getAllUser', user, 'POST')
}

export const reqUpdateUser = (user) => {
    ajax(BASE + '/user/getAllUser', user, 'POST')
}
