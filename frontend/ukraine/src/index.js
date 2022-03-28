// 入口JS
import React from 'react'
import ReactDOM from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import 'antd/dist/antd.css'
import App from './App'
import storageUtil from './utils/storageUtil'
import memoryUtil from './utils/memoryUtil'
//读取local中保存的user,保存在内存中
const user = storageUtil.GetUser()
memoryUtil.user = user
ReactDOM.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>,
  document.getElementById('root')
)
