FROM anapsix/alpine-java:latest

WORKDIR /build
COPY . /build/
COPY ../gradle /build/
COPY ..
RUN ./gradlew build

FROM anapsix/alpine-java:latest

RUN mkdir /app
COPY --from=build build/libs/*.jar /app/server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/server.jar"]
