FROM openjdk:8-jre-slim as base

FROM openjdk:8-jdk-alpine as build
COPY . /src
WORKDIR /src

RUN ./gradlew clean shadowJar

FROM base
RUN mkdir /app
COPY --from=build /src/build/chipseq-choose-ctl-*.jar /app/chipseq.jar