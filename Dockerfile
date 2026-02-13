FROM gradle:8.7-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle installDist --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/install/getstarted-test/ /app/
ENV PORT=8080
EXPOSE 8080
CMD ["bin/getstarted-test"]
