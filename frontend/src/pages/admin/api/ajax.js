/*
 * @Author: Linhao Yu
 * @Date: 2022-04-28 16:48:46
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-08 16:00:44
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
                    token: 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSMjQAAiUdpdSKAiUrQzNTI0MDU0sDs1oAbu9pOiAAAAA.icekPC_t-qDnDcZrxByRT9VD2RmmYPNOuCrz4q8nI1vLp2U6xNRNZGr2sfWnKrDRiERd29Imnx6F8f05dR1yuw',
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
