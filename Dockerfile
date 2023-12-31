FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /workspace/app

COPY . .

RUN chmod 777 ./gradlew 
RUN ./gradlew build -x test
RUN mkdir build/dependency && (cd build/dependency; jar -xf ../libs/sbb-0.7.5.jar)


FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.mygroup.sbb.SbbApplication"]
