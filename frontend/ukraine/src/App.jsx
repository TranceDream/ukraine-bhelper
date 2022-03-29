/*
 * @Author: Linhao Yu
 * @Date: 2022-03-29 14:28:55
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-03-29 14:30:40
 */
// 根应用组件
import React, { Component } from 'react'
import './App.less'
import Router from './router'
export default class App extends Component {
  render() {
    return <Router></Router>
  }
}
