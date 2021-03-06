/*
 * @Author: Linhao Yu
 * @Date: 2022-04-28 16:48:46
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-11 01:47:02
 */
// 发送异步ajax请求的函数
// 封装axios库
// 优化：1. 统一处理请求异常--在外层包裹promise对象，
//          在请求出错的时候不去reject，而是显示错误提示，这样在onfinish函数中就不用try catch
//      2. 异步得到的不是response,而是response.data
import { message } from 'antd'
import axios from 'axios'
import Cookies from 'universal-cookie'
import { cleanCookies } from 'universal-cookie/es6/utils'
export default function useAjax(url, data = {}, type = 'GET') {
    // const navigate = useNavigate()

    const cookie = new Cookies()
    const token = cookie.get('token')
    if (!token) {
        window.location.replace('/login')
    }
    return new Promise((resolve, reject) => {
        let promise
        if (type === 'GET') {
            promise = axios.get(url, {
                params: data,
                // headers: {
                //     'Content-Type': 'application/octet-stream',
                //     token: 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSMjQAAiUdpdSKAiUrQzNTQ1NLQ3Mzy1oA2Czs8SAAAAA.T6csDOU-w-uImXscI8ghv80eiZzihTbm4aYOQbuc0tae4ikxIU89jIhFcrZIOBpFDpAXS6y9sfMOGgJX_V0LSQ',
                //     'Access-Control-Allow-Origin': '*',
                // },
            })
        } else {
            // post
            promise = axios.post(url, data, {
                headers: {
                    'Content-Type': 'application/json',
                    token: token,
                },
            })
        }
        promise
            .then((response) => {
                // console.log('pr', response)
                if (response.data.code === 401) {
                    cleanCookies()
                    window.location.replace('/login')
                } else {
                    resolve(response.data) //异步得到的不是response,而是response.data
                }
            })
            .catch((error) => {
                if (error.response.status === 401) {
                    cleanCookies()
                    window.location.replace('/login')
                }
                message.error('请求出错了：' + error.message)
            })
    })
}
