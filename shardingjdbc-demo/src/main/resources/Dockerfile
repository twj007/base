# 设置容器，安装相关依赖包
# docker指令发布相关容器（注意后面跟的点，代表是在当前目录下）
# docker build -t first:1.0 --rm=true .
FROM alpine:edge
MAINTAINER biegaowo
RUN apk add --no-cache openjdk8
VOLUME /tmp
ADD  leaf.jar leaf.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/leaf.jar"]
