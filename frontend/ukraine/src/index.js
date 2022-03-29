/*
 * @Author: Linhao Yu
 * @Date: 2022-03-29 14:29:01
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-03-29 14:30:44
 */
// 入口JS
import 'antd/dist/antd.css'
import React from 'react'
import ReactDOM from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import App from './App'
import memoryUtil from './utils/memoryUtil'
import storageUtil from './utils/storageUtil'
//读取local中保存的user,保存在内存中
const user = storageUtil.GetUser()
memoryUtil.user = user
ReactDOM.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>,
  document.getElementById('root')
)
