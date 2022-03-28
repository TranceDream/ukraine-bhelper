import { Layout, Menu } from 'antd'
import PubSub from 'pubsub-js'
import React, { useEffect, useState } from 'react'
import { Link, useLocation } from 'react-router-dom'
import menuList from '../../config/menuConfig'
import './index.less'
const { Sider } = Layout
const { SubMenu } = Menu

export default function MySider() {
  const [collapsed, setCollpased] = useState(false)
  const [submenu, setSubmenu] = useState([''])
  const [nodes, setNodes] = useState([])
  const local = useLocation()
  const toggleCollapsed = (_, iscollapsed) => {
    setCollpased(iscollapsed)
  }
  //动态获取展示列表
  const getMenuNode = (menuList, hasIcon) => {
    return menuList.map(item => {
      if (item.children) {
        const cItem = item.children.find(cItem => cItem.key === local.pathname)
        if (cItem) {
          // !为什么没有效果
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
    const token = PubSub.subscribe('collapsed', toggleCollapsed)
    return () => PubSub.unsubscribe(token)
  }, [])

  return (
    <Sider className="sider" trigger={null} collapsible collapsed={collapsed} theme="light">
      <div className="logo">
        <img src="https://joeschmoe.io/api/v1/random" alt="avater" />
        <div className="title" style={{ display: collapsed ? 'none' : 'block' }}>
          北洋北后台
        </div>
      </div>
      <div>
        <Menu
          mode="inline"
          openKeys={submenu}
          selectedKeys={[local.pathname]}
          onOpenChange={openKeys => {
            setSubmenu(openKeys)
          }}
        >
          {nodes}
        </Menu>
      </div>
    </Sider>
  )
}
