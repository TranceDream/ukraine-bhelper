/**
 * @Author: Linhao Yu & Xixian Li
 * @Date: 2022-04-14 22:29:06
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-11 00:16:40
 */
import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import NotFound from '../pages/404/404'
import Admin from '../pages/admin/Admin'
import AdminHome from '../pages/admin/pages/AdminHome'
import DataMonitor from '../pages/admin/pages/DataMonitor'
import HouseControl from '../pages/admin/pages/HouseControl'
import NewsControl from '../pages/admin/pages/NewsControl'
import OnlineUser from '../pages/admin/pages/OnlineUser'
import RoleControl from '../pages/admin/pages/RoleControl'
import ServiceMonitor from '../pages/admin/pages/ServiceMonitor'
import UserControl from '../pages/admin/pages/UserControl'
import Home from '../pages/home/Home'
import { StationList } from '../pages/station/StationList'
import Login from '../pages/user/Login'
import Station from '../pages/station/Station'
import Register from '../pages/user/Register'
import StationPost from '../pages/station/StationPost'
import NewsList from '../pages/news/NewsList'

export default function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/login' element={<Login />} />
                <Route path='/register' element={<Register />} />
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
                        path='/admin/house-control'
                        element={<HouseControl />}
                    />
                    <Route
                        path='/admin/news-control'
                        element={<NewsControl />}
                    />
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
                <Route path='/station' element={<StationList />} />
                <Route path='/station/detail' element={<Station />} />
                <Route path='/station/add' element={<StationPost />} />
                <Route path='*' element={<NotFound />} />
            </Routes>
        </BrowserRouter>
    )
}
