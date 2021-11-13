# eacapi
 earthcoin blockchain browser and api
###Manual
Web 页面访问路径：http://ip:port/eac 如：http://localhost:9000/eac
Api 访问路径：http://ip:port/eac/uri 如：http://localhost:9000/eac/getinfo
注：多个参数通过 / 进行分隔

配置web访问和api接口的关闭或开启，需要在application.properties 中配置conf.open-web=true  web访问开启，false 访问关闭
conf.open-api=true   api 访问开启，false 访问关闭

配置 jar 使用外部配置文件
java -jar xxx.jar --spring.config.location=外部配置文件的路径