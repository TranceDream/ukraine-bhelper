// 入口JS
import ReactDOM from 'react-dom'
import './index.scss'
import Router from './router/Router'

//读取local中保存的user,保存在内存中
ReactDOM.render(<Router />, document.getElementById('root'))
