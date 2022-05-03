/*
 * @Author: Linhao Yu
 * @Date: 2022-04-15 00:10:15
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-04-27 17:07:05
 */
import { Layout, Menu, Typography } from 'antd'
import React, { useEffect, useState } from 'react'
import { Link, Outlet, useLocation, useNavigate } from 'react-router-dom'
import Header from '../../components/Header'
import styles from './Admin.module.scss'
import MySider from './components/AdminSider/MySider'
import menuList from './menuConfig'

const { SubMenu } = Menu
const { Title } = Typography
const { Sider, Content, Footer } = Layout
export default function Admin() {
    const navigate = useNavigate()
    const [collapsed, setCollpased] = useState(false)
    const [submenu, setSubmenu] = useState([''])
    const [nodes, setNodes] = useState<any[] | null>([])
    const local = useLocation()
    const toggleCollapsed = (
        _: any,
        iscollapsed: boolean | ((prevState: boolean) => boolean)
    ) => {
        setCollpased(iscollapsed)
    }

    //动态获取展示列表
    const getMenuNode = (menuList: any[], hasIcon: boolean) => {
        return menuList.map((item) => {
            if (item.children) {
                const cItem = item.children.find(
                    (cItem: { key: string }) => cItem.key === local.pathname
                )
                if (cItem) {
                    setSubmenu([item.key])
                }
                return (
                    <SubMenu key={item.key} icon={item.icon} title={item.title}>
                        {getMenuNode(item.children, false)}
                    </SubMenu>
                )
            } else {
                if (hasIcon) {
                    return (
                        <Menu.Item key={item.key} icon={item.icon}>
                            <Link to={item.key}>{item.title}</Link>
                        </Menu.Item>
                    )
                } else {
                    return (
                        <Menu.Item key={item.key}>
                            <Link to={item.key}>{item.title}</Link>
                        </Menu.Item>
                    )
                }
            }
        })
    }

    useEffect(() => {
        setNodes(getMenuNode(menuList, true))
    }, [])
    return (
        <Layout className={styles.layout}>
            <Header adminhideNav={true} />
            <Layout className={styles.sublayout}>
                <MySider />
                <Content className={styles.content}>
                    <Outlet />
                </Content>
            </Layout>
            <Footer className={styles.footer}>Footer</Footer>
        </Layout>
    )
}
