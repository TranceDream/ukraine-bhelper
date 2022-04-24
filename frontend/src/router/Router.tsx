/*
 * @Author: Linhao Yu & Xixian Li
 * @Date: 2022-04-14 22:29:06
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-04-15 00:10:54
 */
import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import NotFound from '../pages/404/404'
import Admin from '../pages/admin/Admin'
import AdminHome from '../pages/admin/pages/AdminHome/AdminHome'
import Announce from '../pages/admin/pages/Announce/Announce'
import DataMonitor from '../pages/admin/pages/DataMonitor/DataMonitor'
import MenuControl from '../pages/admin/pages/MenuControl/MenuControl'
import OnlineUser from '../pages/admin/pages/OnlineUser/OnlineUser'
import RoleControl from '../pages/admin/pages/RoleControl/RoleControl'
import ServiceMonitor from '../pages/admin/pages/ServiceMonitor/ServiceMonitor'
import UserControl from '../pages/admin/pages/UserControl/UserControl'
import Home from '../pages/home/Home'
export default function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/admin' element={<Admin />}>
                    <Route path='/admin/home' element={<AdminHome />} />
                    <Route
                        path='/admin/user-control'
                        element={<UserControl />}
                    />
                    <Route
                        path='/admin/role-control'
                        element={<RoleControl />}
                    />
                    <Route
                        path='/admin/menu-control'
                        element={<MenuControl />}
                    />
                    <Route path='/admin/announce' element={<Announce />} />
                    <Route path='/admin/online-user' element={<OnlineUser />} />
                    <Route
                        path='/admin/data-monitor'
                        element={<DataMonitor />}
                    />
                    <Route
                        path='/admin/service-monitor'
                        element={<ServiceMonitor />}
                    />
                </Route>
                <Route path='*' element={<NotFound />} />
            </Routes>
        </BrowserRouter>
    )
}
