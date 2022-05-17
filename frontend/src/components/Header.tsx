/**
 * Header
 * @file 前端通用的Header
 * @author TranceDream
 */
import { UserOutlined } from '@ant-design/icons'
import { Dropdown, Menu, Select } from 'antd'
import * as React from 'react'
import { useEffect, useState } from 'react'
import { NavLink, useLocation, useNavigate } from 'react-router-dom'
import Cookie from 'universal-cookie'
import { cleanCookies } from 'universal-cookie/es6/utils'
import logo from '../header.png'
import styles from './Header.module.scss'
import { Option } from 'antd/lib/mentions'
import { useTranslation } from 'react-i18next'

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
    const { t, i18n } = useTranslation()

    const [loginStatus, setLoginStatus] = useState(false)
    const [menuItems, setMenuItems] = useState<Array<any>>([])
    const navigate = useNavigate()

    useEffect(() => {
        const cookie = new Cookie()
        const token = cookie.get('token')
        if (token) {
            setLoginStatus(true)
            if (localStorage.getItem('menus')) {
                setMenuItems(JSON.parse(localStorage.getItem('menus') ?? '[]'))
            }
        }
    }, [])
    return (
        <header className={styles.header}>
            <NavLink
                to={
                    useLocation().pathname.indexOf('/admin') !== -1
                        ? '/admin/user-control'
                        : '/'
                }
                replace>
                <img className={styles.logo} src={logo} alt={'Logo'}></img>
            </NavLink>
            <div className={styles.nav}>
                <ul
                    className={
                        (hideNav ? styles.hidden : styles.revealed) +
                        ' ' +
                        styles.list
                    }>
                    <li>
                        <NavLink to={'/station'}>
                            {t('station.stationList')}
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to={'/news'}>{t('news.newsList')}</NavLink>
                    </li>
                </ul>
                <div className={styles.lng}>
                    <Select
                        size={'large'}
                        style={{ width: '120px' }}
                        defaultValue={localStorage.getItem('lng') ?? 'zh-CN'}
                        onChange={(e) => {
                            localStorage.setItem('lng', e)
                            i18n.changeLanguage(e).then()
                        }}>
                        <Option value={'zh-CN'}>中文</Option>
                        <Option value={'en'}>English</Option>
                    </Select>
                </div>
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
                                    {t('logOut')}
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
                            {t('register')}
                        </NavLink>
                        <NavLink
                            to={'/login'}
                            replace
                            className={
                                (hideUser ? styles.hidden : '') +
                                ' ' +
                                styles.link
                            }>
                            {t('login')}
                        </NavLink>
                    </>
                )}
            </div>
        </header>
    )
}

export default Header
