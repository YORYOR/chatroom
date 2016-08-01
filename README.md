## Chatroom

### server    

    1. 启动server    
    cd chatroom
    bin/server --port=$port --maxUser=$userCount // 自由指定端口号和最大用户数量
    
### client    
    1. 安装代码的方式
    cd chatroom
    bin/server --port=$server_port --serverIp=$serverIp //server端的ip和端口
    
    
    2. nc连接
    nc $serverIp $serverPort
    
    
    
