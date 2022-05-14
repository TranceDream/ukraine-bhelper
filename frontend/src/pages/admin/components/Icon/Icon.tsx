import {
    FileTextOutlined,
    HomeOutlined,
    TagOutlined,
    ToolOutlined,
    UserOutlined,
} from '@ant-design/icons'
import React from 'react'
const Icon = (name: string) => {
    switch (name) {
        default:
            return <></>
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
