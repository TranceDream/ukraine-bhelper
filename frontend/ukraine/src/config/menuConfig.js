import {
  HomeOutlined,
  UserSwitchOutlined,
  SolutionOutlined,
  NotificationOutlined,
  FileSearchOutlined,
  AppstoreOutlined
} from '@ant-design/icons'
const menuList = [
  {
    title: '首页',
    key: '/home',
    icon: <HomeOutlined />
  },
  {
    title: '用户管理',
    key: '/user-control',
    icon: <UserSwitchOutlined />
  },
  {
    title: '角色管理',
    key: '/role-control',
    icon: <SolutionOutlined />
  },
  {
    title: '通知公告',
    key: '/announce',
    icon: <NotificationOutlined />
  },
  {
    title: '日志管理',
    key: '/log',
    icon: <FileSearchOutlined />,
    children: [
      {
        title: '操作日志',
        key: '/operation-log'
      },
      {
        title: '登录日志',
        key: '/login-log'
      }
    ]
  },
  {
    title: '系统监控',
    key: '/service',
    icon: <AppstoreOutlined />,
    children: [
      {
        title: '数据监控',
        key: '/data-monitor'
      },
      {
        title: '服务监控',
        key: '/service-monitor'
      }
    ]
  }
]

export default menuList
