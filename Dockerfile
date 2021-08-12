# java 8 jdk 이미지 사용.
# 해당 이미지는 linux에 jdk 8만 설치되어있다.
FROM openjdk:8-jdk-alpine

# bash를 사용하기 위한 설정
RUN apk add --no-cache \
        bash

USER root

# 연결할 포트
EXPOSE 8080

# 기본 디렉토리 설정
WORKDIR /var/www/dev-meeting-study
