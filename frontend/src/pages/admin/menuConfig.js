/*
 * @Author: Linhao Yu
 * @Date: 2022-04-16 22:28:55
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-04-16 23:05:09
 */
import {
    AppstoreOutlined,
    HomeOutlined,
    NotificationOutlined,
    SolutionOutlined,
    UserSwitchOutlined,
} from '@ant-design/icons'
const menuList = [
    {
        title: '首页',
        key: '/home',
        icon: <HomeOutlined />,
    },
    {
        title: '用户管理',
        key: '/user-control',
        icon: <UserSwitchOutlined />,
    },
    {
        title: '角色管理',
        key: '/role-control',
        icon: <SolutionOutlined />,
    },
    {
        title: '菜单管理',
        key: '/menu-control',
        icon: <SolutionOutlined />,
    },
    {
        title: '通知公告',
        key: '/announce',
        icon: <NotificationOutlined />,
    },

    {
        title: '系统监控',
        key: '/service',
        icon: <AppstoreOutlined />,
        children: [
            {
                title: '在线用户',
                key: '/online-user',
            },
            {
                title: '数据监控',
                key: '/data-monitor',
            },
            {
                title: '服务监控',
                key: '/service-monitor',
            },
        ],
    },
]

export default menuList
