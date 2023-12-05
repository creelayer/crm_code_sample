FROM --platform=linux/arm64 amazoncorretto:17.0.7-alpine as build-stage
WORKDIR /app
COPY ./ ./
RUN ./gradlew bootJar


FROM --platform=linux/arm64 amazoncorretto:17.0.7-alpine as production-stage
MAINTAINER creelayer

RUN mkdir /http
COPY --from=build-stage /app/build/libs /app

EXPOSE 8080
CMD ["java", "-Xmx100m", "-jar", "/app/marketplace.crm-0.0.1-SNAPSHOT.jar"]