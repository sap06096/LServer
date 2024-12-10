# Step 1: Build stage
FROM openjdk:17-jdk-slim AS builder

# Maven 3.9.9 설치
RUN apt-get update && apt-get install -y wget \
    && wget https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz \
    && tar -xzf apache-maven-3.9.9-bin.tar.gz -C /opt/ \
    && ln -s /opt/apache-maven-3.9.9 /opt/maven \
    && ln -s /opt/maven/bin/mvn /usr/bin/mvn

# 프로젝트 소스 복사
WORKDIR /app
COPY pom.xml ./  # pom.xml을 먼저 복사하여 의존성만 다운로드
RUN mvn dependency:go-offline -B

# 소스 코드만 변경되었을 때만 빌드가 실행되도록 설정
COPY src /app/src  # 소스 코드 복사
RUN mvn clean package -DskipTests  # 빌드

# Step 2: Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드된 JAR 파일을 빌드된 이미지로 복사
COPY --from=builder /app/target/LServer-0.0.1-SNAPSHOT.jar LServer-0.0.1-SNAPSHOT.jar

EXPOSE 9090 5005

# JAVA_OPTS 환경변수로 설정
ENV JAVA_OPTS=""

# ENTRYPOINT: JAVA_OPTS에 따라 실행 방식 다르게
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar LServer-0.0.1-SNAPSHOT.jar"]
