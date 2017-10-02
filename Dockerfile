FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/bbijelic/config-service.git

FROM maven:3.5-jdk-8-alpine as build
WORKDIR /app
COPY --from=clone /app/config-service /app
RUN mvn install -P docker

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /app/assembly/target/service/service /app
EXPOSE 8443 9443
CMD ["sh /app/bin/start"]