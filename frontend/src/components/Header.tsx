/**
 * Header
 * @file 前端通用的Header
 * @author TranceDream
 */
import { Button, Dropdown, Menu } from 'antd'
import * as React from 'react'
import { NavLink } from 'react-router-dom'
import styles from './Header.module.scss'
import { AlertOutlined, UserOutlined } from '@ant-design/icons'

interface Props {
    hideNav?: boolean
}

/**
 * 前端通用的Header
 * @param {boolean} hideNav 是否隐藏导航按钮(用于主页Cover处)
 * @constructor
 * @author TranceDream
 */
const Header = ({ hideNav = false }: Props) => {
    const menu = (
        <Menu>
            <Menu.Item key='1'>
                <NavLink to={'/admin'} replace={false}>
                    Dashboard
                </NavLink>
            </Menu.Item>
            <Menu.Divider />
            <Menu.Item key='3'>退出登录</Menu.Item>
        </Menu>
    )
    return (
        <header className={styles.header}>
            <NavLink to={'/'} replace>
                <h2 className={styles.logo}>Header</h2>
            </NavLink>
            <div className={styles.nav}>
                <ul
                    className={
                        (hideNav ? styles.hidden : '') + ' ' + styles.list
                    }>
                    <li>
                        <NavLink to={'/station'}>寻求援助</NavLink>
                    </li>
                    <li>
                        <NavLink to={'/'}>新闻中心</NavLink>
                    </li>
                </ul>
                <Button
                    className={styles.notifications}
                    shape={'circle'}
                    size={'large'}>
                    <AlertOutlined />
                </Button>
                <Dropdown.Button
                    className={styles.avatar}
                    size={'large'}
                    overlay={menu}
                    icon={<UserOutlined />}
                />
            </div>
        </header>
    )
}

export default Header
