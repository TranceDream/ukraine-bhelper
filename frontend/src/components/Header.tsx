/**
 * Header
 * @file 前端通用的Header
 * @author TranceDream
 */
import { UserOutlined } from '@ant-design/icons'
import { Dropdown, Menu } from 'antd'
import logo from '../header.png'
import * as React from 'react'
import { useEffect, useState } from 'react'
import { NavLink, useLocation, useNavigate } from 'react-router-dom'
import styles from './Header.module.scss'
import Cookie from 'universal-cookie'
import { cleanCookies } from 'universal-cookie/es6/utils'

interface Props {
    hideNav?: boolean
    hideUser?: boolean
}

/**
 * 前端通用的Header
 * @param {boolean} hideNav 是否隐藏导航按钮(用于主页Cover处)
 * @param {boolean} hideUser 是否隐藏登录按钮/个人中心(用于登录注册处)
 * @constructor
 * @author TranceDream
 */
const Header = ({ hideNav = false, hideUser = false }: Props) => {
    const [loginStatus, setLoginStatus] = useState(false)
    const [menuItems, setMenuItems] = useState<Array<any>>([])
    const navigate = useNavigate()

    useEffect(() => {
        const cookie = new Cookie()
        const token = cookie.get('token')
        if (token) {
            setLoginStatus(true)
            if (localStorage.getItem('menus')) {
                console.log('hello')
                setMenuItems(JSON.parse(localStorage.getItem('menus') ?? '[]'))
            }
        }
    }, [])
    return (
        <header className={styles.header}>
            <NavLink
                to={
                    useLocation().pathname.indexOf('/admin') !== -1
                        ? '/admin/home'
                        : '/'
                }
                replace>
                <img className={styles.logo} src={logo} alt={'Logo'}></img>
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
                        <NavLink to={'/news'}>新闻中心</NavLink>
                    </li>
                </ul>
                {loginStatus ? (
                    <Dropdown.Button
                        className={
                            (hideUser ? styles.hidden : '') +
                            ' ' +
                            styles.avatar
                        }
                        size={'large'}
                        overlay={
                            <Menu>
                                {menuItems.map((m) => (
                                    <Menu.Item
                                        key={m.menuId}
                                        style={{ padding: '8px' }}>
                                        <NavLink to={m.url} replace={true}>
                                            {m.menuName}
                                        </NavLink>
                                    </Menu.Item>
                                ))}
                                <Menu.Divider />
                                <Menu.Item
                                    key='logout'
                                    onClick={() => {
                                        cleanCookies()
                                        setLoginStatus(false)
                                        navigate('/')
                                    }}>
                                    退出登录
                                </Menu.Item>
                            </Menu>
                        }
                        icon={<UserOutlined />}
                    />
                ) : (
                    <>
                        <NavLink
                            to={'/register'}
                            replace
                            className={
                                (hideUser ? styles.hidden : '') +
                                ' ' +
                                styles.link
                            }>
                            注册
                        </NavLink>
                        <NavLink
                            to={'/login'}
                            replace
                            className={
                                (hideUser ? styles.hidden : '') +
                                ' ' +
                                styles.link
                            }>
                            登录
                        </NavLink>
                    </>
                )}
            </div>
        </header>
    )
}

export default Header
