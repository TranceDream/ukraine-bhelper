/*
 * @Author: Linhao Yu
 * @Date: 2022-04-16 22:28:55
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-15 03:03:56
 */
import {
    AppstoreOutlined,
    FileTextOutlined,
    HomeOutlined,
    NotificationOutlined,
    SolutionOutlined,
    TagOutlined,
    ToolOutlined,
    UserOutlined
} from '@ant-design/icons'
export const mapIcon = {
    '<UserOutlined />': <UserOutlined />,
    '<FileTextOutlined />': <FileTextOutlined />,
    '<HomeOutlined />': <HomeOutlined />,
    '<TagOutlined />': <TagOutlined />,
    '<ToolOutlined />': <ToolOutlined />,
}
export const menuList = [
    // {
    //     title: '首页',
    //     key: '/admin/home',
    //     icon: <HomeOutlined />,
    // },
    {
        title: '用户管理',
        key: '/admin/user-control',
        icon: '<UserSwitchOutlined />',
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
        title: '举报管理',
        key: '/admin/report-control',
        icon: <NotificationOutlined />,
    },

    {
        title: '系统监控',
        key: '/service',
        icon: <AppstoreOutlined />,
        children: [
            // {
            //     title: '举报管理',
            //     key: '/admin/report-control',
            // },
            {
                title: '菜单管理',
                key: '/admin/menu-control',
            },
            {
                title: '服务监控',
                key: '/admin/service-monitor',
            },
        ],
    },
]

