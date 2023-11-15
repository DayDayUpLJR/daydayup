# 添加 Java 8 镜像来源
FROM java:8

# 添加参数
ARG JAR_FILE

# 添加 Spring Boot 包, JAR_FILE 参数就是从 Docker Maven 插件中指定的构建参数
ADD target/${JAR_FILE} app.jar

# 执行启动命令,   -Djava.security.egd=file:/dev/./urandom优化随数生成策略的
#   java -jar -Dspring.profiles.active=prod -Djava.security.egd=file:/dev/./urandom  app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]