/*
 * @Author: Linhao Yu
 * @Date: 2022-04-16 22:28:43
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-04-27 18:58:03
 */
import { MenuFoldOutlined, MenuUnfoldOutlined } from '@ant-design/icons'
import { Button, Layout, Menu } from 'antd'
import React, { useEffect, useState } from 'react'
import { Link, useLocation } from 'react-router-dom'
import { reqModuleList } from '../../api'
import { menuList } from '../../menuConfig'
import PubSub from '../../Utils/pubsub'
import Icon from '../Icon/Icon'
import styles from './MySider.module.scss'
const { Sider } = Layout
const { SubMenu } = Menu

export default function MySider() {
    const [collapsed, setCollpased] = useState(false)
    const [submenu, setSubmenu] = useState([''])
    const [nodes, setNodes] = useState<any[]>([])
    const local = useLocation()
    const toggleCollapsed = () => {
        setCollpased(!collapsed)
    }
    //动态获取展示列表
    const getMenuNode = (menuList: any[], hasIcon: boolean) => {
        console.log('menu', menuList)
        return menuList.map((item) => {
            if (item.children && item.children.length !== 0) {
                const cItem = item.children.find(
                    (cItem: { key: string }) => cItem.key === local.pathname
                )
                if (cItem) {
                    setSubmenu([item.key])
                }
                return (
                    <SubMenu
                        key={Date.now() + item.title}
                        icon={Icon(item.icon)}
                        title={item.title}>
                        {getMenuNode(item.children, false)}
                    </SubMenu>
                )
            } else {
                if (hasIcon) {
                    return (
                        <Menu.Item
                            key={Date.now() + item.title}
                            icon={Icon(item.icon)}>
                            <Link to={item.key}>{item.title}</Link>
                        </Menu.Item>
                    )
                } else {
                    return (
                        <Menu.Item key={Date.now() + item.title}>
                            <Link to={item.key}>{item.title}</Link>
                        </Menu.Item>
                    )
                }
            }
        })
    }

    useEffect(() => {
        async function getMenulist() {
            const res = await reqModuleList()
            console.table(res.data)
            // !修改
            setNodes(getMenuNode(menuList, true))
            // setMenuList(res.data)
        }
        getMenulist()
    }, [])

    useEffect(() => {
        PubSub.publish('navCollpased', collapsed)
    }, [collapsed])
    return (
        <Sider
            className={styles.sider}
            trigger={null}
            collapsible
            collapsed={collapsed}
            theme='light'>
            <div className={styles.logo}>
                <img src='https://joeschmoe.io/api/v1/random' alt='avater' />
                <div
                    className={styles.title}
                    style={{ display: collapsed ? 'none' : 'block' }}>
                    北洋北后台
                </div>
            </div>
            <div>
                <Menu
                    mode='inline'
                    openKeys={submenu}
                    selectedKeys={[local.pathname]}
                    onOpenChange={(openKeys) => {
                        setSubmenu(openKeys)
                    }}>
                    {nodes}
                </Menu>
                <Button
                    className={styles.cbtn}
                    type='primary'
                    onClick={toggleCollapsed}
                    style={{ marginBottom: 16 }}>
                    {React.createElement(
                        collapsed ? MenuUnfoldOutlined : MenuFoldOutlined
                    )}
                </Button>
            </div>
        </Sider>
    )
}
