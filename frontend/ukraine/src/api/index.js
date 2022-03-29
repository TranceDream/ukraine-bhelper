/*
 * @Author: Linhao Yu
 * @Date: 2022-03-29 14:26:52
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-03-29 14:30:26
 */
/*
包含应用中所有接口请求函数的模块
*/

import ajax from './ajax'
// 登录
/* export default reqLogin(username, password){
  return ajax('./login', {username, password})
} */

export const reqLogin = (username, password) => ajax('./login', { username, password })

// add user

export const reqAddUser = user => ajax('/manage/user/login', user, 'POST')
