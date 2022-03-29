/*
 * @Author: Linhao Yu
 * @Date: 2022-03-29 14:28:45
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-03-29 14:30:01
 */
import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Admin from '../pages/admin'
import Announce from '../pages/announce'
import DataMonitor from '../pages/data-monitor'
import Home from '../pages/home'
import Login from '../pages/login'
import LoginLog from '../pages/login-log'
import OperationLog from '../pages/operation-log'
import RoleControl from '../pages/role-control'
import ServiceMonitor from '../pages/service-monitor'
import UserControl from '../pages/user-control'
export default function Router() {
  return (
    <Routes>
      <Route path="/" element={<Admin />}>
        <Route path="/home" element={<Home />} />
        <Route path="/user-control" element={<UserControl />} />
        <Route path="/role-control" element={<RoleControl />} />
        <Route path="/announce" element={<Announce />} />
        <Route path="/operation-log" element={<OperationLog />} />
        <Route path="/login-log" element={<LoginLog />} />
        <Route path="/data-monitor" element={<DataMonitor />} />
        <Route path="/service-monitor" element={<ServiceMonitor />} />
      </Route>
      <Route path="/login" element={<Login />} />
    </Routes>
  )
}
