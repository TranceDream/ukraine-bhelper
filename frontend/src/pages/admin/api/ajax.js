/*
 * @Author: Linhao Yu
 * @Date: 2022-04-28 16:48:46
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-07 11:53:46
 */
// 发送异步ajax请求的函数
// 封装axios库
// 优化：1. 统一处理请求异常--在外层包裹promise对象，
//          在请求出错的时候不去reject，而是显示错误提示，这样在onfinish函数中就不用try catch
//      2. 异步得到的不是response,而是response.data
import { message } from 'antd'
import axios from 'axios'
export default function ajax(url, data = {}, type = 'GET') {
    return new Promise((resolve, reject) => {
        let promise
        if (type === 'GET') {
            promise = axios.get(url, {
                params: data,
                headers: {
                    'Content-Type': 'application/octet-stream',
                    token: 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSMjQAAiUdpdSKAiUrQzNTQ1NLQ3Mzy1oA2Czs8SAAAAA.T6csDOU-w-uImXscI8ghv80eiZzihTbm4aYOQbuc0tae4ikxIU89jIhFcrZIOBpFDpAXS6y9sfMOGgJX_V0LSQ',
                    'Access-Control-Allow-Origin': '*',
                },
            })
        } else {
            // post
            promise = axios.post(url, data, {
                headers: {
                    'Content-Type': 'application/json',
                    token: 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSMjQAAiUdpdSKAiUrQzNTQ0sDIwsD01oAXE64piAAAAA.fC9RSJj6u5FIkOCnXPd_finS0t1axnW0Zv7SqxlEAz71nph6qkktYTeqxsDfeoxXuE3pQJHdu2BiG8FAn_Ot9g',
                    'Access-Control-Allow-Origin': '*',
                },
            })
        }
        promise
            .then((response) => {
                resolve(response.data) //异步得到的不是response,而是response.data
            })
            .catch((error) => message.error('请求出错了：' + error.message))
    })
}
