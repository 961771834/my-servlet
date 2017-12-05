# my-servlet
自己书写的servlet，仿照servlet源码


## ex03 
1. 本章的应用程序所包含的模块
+ 连接器及其支持类(HttpConnector和HttpProcessor);
+ 表示HTTP请求类(HttpRequest)及其支持类
+ 表示HTTP响应了(HttpResponse)及其支持类
+ 外观类(HttpRequestFacade和HttPResponseFacade)
+ 常量类

+ 核心模块，servletProcessor和StaticResourceProcessor类

+ 启动类，负责启动应用程序。

2. HttpConnector
该应用启动时，会另起一个线程来运行。
HttpConnector类和SimpleServeltContainer.ServletProcessor1大致类似。区别在与从java.net.ServerSocket类accept()方法接收一个套接字，创建一个HttpProcessor实时并传入该套接字，调用其parse方法。

3. HttpProcessor
+ 创建一个HttpRequest对象
+ 创建一个HttpResponse对象
+ 解析HTTP请求的第一行内容和请求头信息，填充HttpRequest对象。
+ 将HTTPRequest对象和HttpResponse对象传递给servletProcessor或者StaticResourceProcessor的process方法.