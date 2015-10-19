1、工程构建:gradle   开发框架:  ajax + springmvc + spring + ibatis
2、mysql数据库脚本文件：doc/weizhang.sql
3、供应商相关配置：supplier.xml
4、系统配置的全国城市json在 js/cities.js文件中，其中suppliers的值对应的supplier.xml文件中配置的code值，默认是第一家供应商，如果想将某个城市改为其他供应商，只需要修改该值即可。


工程打war包方法
第一步：配置好gradle环境变量
第二步：通过命令行，在工程目录执行 gradle eclipse 进行项目构建，自动从网络下载相应jar包
第三步：执行 gradle clean uatWar命令进行打包,打包后的war包会在 build/libs/目录下
