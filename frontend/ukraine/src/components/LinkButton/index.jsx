/*
 * @Author: Linhao Yu
 * @Date: 2022-03-29 14:27:05
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-03-29 14:30:24
 */
import React from 'react'
import './index.less'
export default function LinkButton(props) {
  return <button {...props}>{props.children}</button>
}
