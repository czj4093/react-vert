import React from 'react';
import ReactDOM from 'react-dom';

// 我们的前端包含一个单一的 ReactGreeter组件
class Greeter extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            // Greeter组件保持状态，即在浏览器中显示的消息
            message: "Default message"
        }
    }

    componentDidMount() {
        // 初始渲染后，向后端发送 HTTP 请求；结果用于更新组件状态
        fetch("/api/message")
            .then(response => response.text())
            .then(text => this.setState({message: text}));
    }

    render() {
        // 消息显示在一个简单的 HTML 中span
        return (
            <div>
                <span>{this.state.message}</span>
            </div>
        );
    }
}

// 组件在Greeter网页中渲染
ReactDOM.render(
    <Greeter/>,
    document.getElementById('root')
);