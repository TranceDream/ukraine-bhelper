/*
 * @Author: Linhao Yu
 * @Date: 2022-04-16 22:28:55
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-11 00:16:23
 */
import {
    AppstoreOutlined,
    HomeOutlined,
    NotificationOutlined,
    SolutionOutlined,
    UserSwitchOutlined
} from '@ant-design/icons'
const menuList = [
    {
        title: '首页',
        key: '/admin/home',
        icon: <HomeOutlined />,
    },
    {
        title: '用户管理',
        key: '/admin/user-control',
        icon: <UserSwitchOutlined />,
    },
    {
        title: '角色管理',
        key: '/admin/role-control',
        icon: <SolutionOutlined />,
    },
    {
        title: '房源管理',
        key: '/admin/house-control',
        icon: <SolutionOutlined />,
    },
    {
        title: '新闻管理',
        key: '/admin/news-control',
        icon: <NotificationOutlined />,
    },

    {
        title: '系统监控',
        key: '/service',
        icon: <AppstoreOutlined />,
        children: [
            {
                title: '在线用户',
                key: '/admin/online-user',
            },
            {
                title: '数据监控',
                key: '/admin/data-monitor',
            },
            {
                title: '服务监控',
                key: '/admin/service-monitor',
            },
        ],
    },
]

export default menuList
