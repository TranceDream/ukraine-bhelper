/**
 * @Author: Linhao Yu & Xixian Li
 * @Date: 2022-04-14 22:29:06
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-15 22:20:04
 */
import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import NotFound from '../pages/404/404'
import Admin from '../pages/admin/Admin'
import DataMonitor from '../pages/admin/pages/DataMonitor'
import HouseControl from '../pages/admin/pages/HouseControl'
import LogControl from '../pages/admin/pages/LogControl'
import MenuControl from '../pages/admin/pages/Menu-control'
import MyNews from '../pages/admin/pages/MyNews'
import NewsControl from '../pages/admin/pages/NewsControl'
import NewsEdit from '../pages/admin/pages/NewsControl/NewsEdit'
import ReportControl from '../pages/admin/pages/ReportControl'
import ReportDetail from '../pages/admin/pages/ReportDetail'
import RoleControl from '../pages/admin/pages/RoleControl'
import UserControl from '../pages/admin/pages/UserControl'
import Home from '../pages/home/Home'
import News from '../pages/news/News'
import NewsList from '../pages/news/NewsList'
import Station from '../pages/station/Station'
import { StationList } from '../pages/station/StationList'
import StationManagement from '../pages/station/StationManagement'
import StationPost from '../pages/station/StationPost'
import Login from '../pages/user/Login'
import Register from '../pages/user/Register'

export default function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/login' element={<Login />} />
                <Route path='/register' element={<Register />} />
                <Route path='/admin' element={<Admin />}>
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
                    <Route path='/admin/news-edit' element={<NewsEdit />} />
                    <Route
                        path='/admin/report-control'
                        element={<ReportControl />}
                    />
                    <Route
                        path='/admin/data-monitor'
                        element={<DataMonitor />}
                    />
                    <Route path='/admin/MyNews' element={<MyNews />} />

                    <Route
                        path='/admin/menu-control'
                        element={<MenuControl />}
                    />
                    <Route path='/admin/report' element={<ReportDetail />} />
                    <Route path='/admin/log-control' element={<LogControl />} />
                </Route>
                <Route path='/station' element={<StationList />} />
                <Route path='/station/detail' element={<Station />} />
                <Route path='/station/add' element={<StationPost />} />
                <Route
                    path='/station/management'
                    element={<StationManagement />}
                />
                <Route path='/news' element={<NewsList />} />
                <Route path='/news/detail' element={<News />} />
                <Route path='*' element={<NotFound />} />
            </Routes>
        </BrowserRouter>
    )
}
