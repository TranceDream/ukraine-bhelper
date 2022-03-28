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
      })
    } else {
      // post
      promise = axios.post(url, data)
    }
    promise
      .then((response) => {
        resolve(response.data) //异步得到的不是response,而是response.data
      })
      .catch((error) => message.error('请求出错了：' + error.message))
  })
}
