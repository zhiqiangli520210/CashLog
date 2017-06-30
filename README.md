错误日志抓取，并上报到服务器
简介：
此项目为本地抓取错误日志，保存本地txt文本，退出app，再次打开app时，判断本地是否有日志txt，如果有，就上传到服务器，成功后进行删除。

1.定义一个类实现UncaughtExceptionHandler 

2.在Application中初始化，Thread.setDefaultUncaughtExceptionHandler(handler); 

3.将抓取到的错误保存到本地txt文件。

4.上传到服务器。
