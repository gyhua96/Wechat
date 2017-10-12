#  WeChat 
    JavaFX是Sun公司提出的，在2008年发布了正式版。
    最近要做一个网络方面的实训，所骗打算写个聊天软件。前一断时间看到JavaFX的介绍，觉得挺不错的，所以这次正好练练手
    基本构想：
    打开软件 -> 输入用户名 -> 启动UDP服务(处理用户状态，固定端口) -> 启动UDP服务(处理用户消息，随机端口) -> 广播上线消息 -> 接收回应消息 -> 添加已在线用户 -> 就绪状态
    
###    2017-10-5更新：
    已经完成了基本聊天功能，包括登录，自动检测在线用户，自动侦测新上线用户，发送消息，接收消息，状态更新（在线，来信，离线）。
<img src="/ueditor/php/upload/image/20171005/1507173667974542.png" title="1507173667974542.png" alt="image.png" width="214" height="250" style="width: 214px; height: 250px;">

<img src="/ueditor/php/upload/image/20171005/1507173785295096.png" title="1507173785295096.png" alt="image.png" width="600" height="400" style="width: 600px; height: 400px;">
