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
import Home from '../pages/home/Home'
export default function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/admin' element={<Admin />} />
                <Route path='*' element={<NotFound />} />
            </Routes>
        </BrowserRouter>
    )
}
