# build
FROM gradle:8.7-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean installDist --no-daemon

# run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/install/ /app/install/
ENV PORT=8080
EXPOSE 8080
CMD ["sh", "-lc", "/app/install/*/bin/*"]
