import React from 'react'
import styles from './Tag.module.scss'

interface TagProps {
    content: string
    icon: React.ReactNode
    bg: string
}

const Tag = (props: TagProps) => {
    return (
        <div className={styles.container} style={{ background: props.bg }}>
            {props.icon}
            <span>{props.content}</span>
        </div>
    )
}

export default Tag
