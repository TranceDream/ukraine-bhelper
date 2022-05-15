import React, { useState } from 'react'
import MarkdownIt from 'markdown-it'
import MdEditor from 'react-markdown-editor-lite'
import 'react-markdown-editor-lite/lib/index.css'
import { Button, Form, Input } from 'antd'
import Cookie from 'universal-cookie'
import { useNavigate } from 'react-router-dom'
import styles from './NewsEdit.module.scss'

const upload = async (file: File): Promise<string> => {
    const formData = new FormData()
    formData.append('newsPic', file)
    // const raw = await axios({
    //     method: 'post',
    //     url: 'https://sm.ms/api/v2/upload',
    //     data: formData,
    //     headers: {
    //         'Content-Type': 'multipart/form-data',
    //         Authorization: 'clDg9R7N9nK3yDTwNvHL0EHqxRGosFmV',
    //     },
    // })

    const cookie = new Cookie()

    const raw = await fetch('http://139.9.231.20:81/news/fileupload', {
        method: 'POST',
        mode: 'cors',
        headers: {
            token: cookie.get('token'),
        },
        body: formData,
    })
    console.log(raw)
    const res = await raw.json()
    console.log(res.data)
    return 'http://139.9.231.20:8007/' + res.data.url
}

const addArticle = async (title: string, content: string) => {
    const cookie = new Cookie()
    const token = cookie.get('token')

    const raw = await fetch('http://139.9.231.20:81/news/addArticle', {
        method: 'post',
        mode: 'cors',
        headers: {
            token,
            'content-type': 'application/json',
        },
        body: JSON.stringify({
            title,
            content,
        }),
    })
    const res = await raw.json()
    console.log(res)
}

const NewsEdit = () => {
    const [title, setTitle] = useState<string>('')
    const [content, setContent] = useState<string>('')
    const navigate = useNavigate()

    const mdParser = new MarkdownIt()
    return (
        <div className={styles.container}>
            <Form.Item>
                <span className={styles.label}>编辑新闻</span>
            </Form.Item>
            <Form.Item label={'输入标题'}>
                <Input
                    value={title}
                    size={'middle'}
                    maxLength={200}
                    onChange={(e) => {
                        setTitle(e.target.value)
                    }}></Input>
            </Form.Item>
            <MdEditor
                onChange={(text) => {
                    setContent(text.text)
                }}
                renderHTML={(text) => mdParser.render(text)}
                onImageUpload={(file: File) => {
                    return upload(file)
                }}
            />
            <Button
                type={'primary'}
                style={{ marginTop: '16px' }}
                onClick={() => {
                    addArticle(title, content).then((res) => {
                        navigate('/admin/news-control')
                    })
                }}>
                提交
            </Button>
        </div>
    )
}

export default NewsEdit
