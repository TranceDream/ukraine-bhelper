/*
 * @Author: Linhao Yu
 * @Date: 2022-05-15 02:21:01
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-15 04:18:38
 */
import {
    FileTextOutlined,
    HomeOutlined,
    TagOutlined,
    ToolOutlined,
    UserOutlined,
} from '@ant-design/icons'
import React from 'react'
const Icon = (name: any) => {
    switch (name) {
        default:
            return <>111</>
        case '<UserOutlined />':
            return <UserOutlined />
        case '<FileTextOutlined />':
            return <FileTextOutlined />
        case '<HomeOutlined />':
            return <HomeOutlined />
        case '<TagOutlined />':
            return <TagOutlined />
        case '<ToolOutlined />':
            return <ToolOutlined />
    }
}

export default Icon
// export const mapIcon = {
//     '<UserOutlined />': <UserOutlined />,
//     '<FileTextOutlined />': <FileTextOutlined />,
//     '<HomeOutlined />': <HomeOutlined />,
//     '<TagOutlined />': <TagOutlined />,
//     '<ToolOutlined />': <ToolOutlined />,
// }
