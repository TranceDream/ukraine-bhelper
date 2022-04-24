/**
 * Header
 * @file 前端通用的Header
 * @author TranceDream
 */
import { AlertOutlined, UserOutlined } from '@ant-design/icons'
import { Button, Dropdown, Menu } from 'antd'
import * as React from 'react'
import { NavLink, useLocation } from 'react-router-dom'
import styles from './Header.module.scss'

interface Props {
    hideNav?: boolean
    adminhideNav?: boolean
}

/**
 * 前端通用的Header
 * @param {boolean} hideNav 是否隐藏导航按钮(用于主页Cover处)
 * @constructor
 * @author TranceDream
 */
const Header = ({ hideNav = false, adminhideNav = false }: Props) => {
    const menu = (
        <Menu>
            <Menu.Item key='1'>
                <NavLink
                    to={
                        useLocation().pathname.indexOf('/admin') != -1
                            ? '/'
                            : '/admin/home'
                    }
                    replace={false}>
                    Dashboard
                </NavLink>
            </Menu.Item>
            <Menu.Divider />
            <Menu.Item key='3'>退出登录</Menu.Item>
        </Menu>
    )
    return (
        <header className={styles.header}>
            <NavLink
                to={
                    useLocation().pathname.indexOf('/admin') != -1
                        ? '/admin/home'
                        : '/'
                }
                replace>
                <h2 className={styles.logo}>Header</h2>
            </NavLink>
            <div className={styles.nav}>
                <ul
                    className={
                        (adminhideNav
                            ? styles.hidden
                            : hideNav
                            ? styles.hidden
                            : '') +
                        ' ' +
                        styles.list
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
