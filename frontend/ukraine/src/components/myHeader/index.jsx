import {
  DownOutlined,
  ExclamationCircleOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined
} from '@ant-design/icons'
import { Avatar, Button, Dropdown, Layout, Menu, Modal } from 'antd'
import PubSub from 'pubsub-js'
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import memoryUtil from '../../utils/memoryUtil'
import storageUtil from '../../utils/storageUtil'
import LinkButton from '../LinkButton'
import './index.less'

const { confirm } = Modal
const { Header } = Layout
export default function MyHeader() {
  const [collapsed, setCollapsed] = useState(true)
  const navigate = useNavigate()
  //登出回调函数
  const logout = () => {
    confirm({
      title: '确定要登出吗？',
      icon: <ExclamationCircleOutlined />,
      content: '',
      onOk: () => {
        console.log('OK')
        memoryUtil.user = undefined
        storageUtil.RemoveUser()
        navigate('/login', { replace: true })
      },
      onCancel: () => {
        console.log('Cancel')
      }
    })
  }
  const dropdownMenu = (
    <Menu>
      <Menu.Item key="modify">
        <LinkButton>修改信息</LinkButton>
      </Menu.Item>
      <Menu.Item key="logout">
        <LinkButton onClick={logout}>登出</LinkButton>
      </Menu.Item>
    </Menu>
  )
  //发布订阅
  const publish = () => {
    setCollapsed(!collapsed)
    PubSub.publish('collapsed', collapsed)
  }
  return (
    <div>
      <Header className="header">
        <div className="button">
          <Button type="primary" onClick={publish}>
            {React.createElement(!collapsed ? MenuUnfoldOutlined : MenuFoldOutlined)}
          </Button>
        </div>

        <div className="profilePart">
          <Dropdown overlay={dropdownMenu} placement="bottomRight">
            <a className="ant-dropdown-link" onClick={e => e.preventDefault()}>
              {'Hello, ' + memoryUtil.user.username} <DownOutlined />
            </a>
          </Dropdown>
          <Avatar className="avatar" src="https://joeschmoe.io/api/v1/random" />
        </div>
      </Header>
    </div>
  )
}
