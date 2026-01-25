## Stage 1: build with Maven
#FROM maven:3.9.4-eclipse-temurin-17 AS build
#WORKDIR /workspace/app
#
#ENV LANG=C.UTF-8 \
#    LC_ALL=C.UTF-8 \
#    MAVEN_OPTS="-Dfile.encoding=UTF-8"
#
## Copia somente o pom primeiro para aproveitar cache do Docker
#COPY pom.xml .
## Se houver arquivos de configuração adicionais (settings.xml), copie-os aqui
#
#COPY src ./src
#RUN mvn -B -DskipTests -Dproject.build.sourceEncoding=UTF-8 -Dproject.reporting.outputEncoding=UTF-8 package
#
## Stage 2: runtime
#FROM eclipse-temurin:17-jre
#ARG JAR_FILE=target/*.jar
#WORKDIR /app
#
## Copia o jar construído da stage de build
#COPY --from=build /workspace/app/target/*.jar app.jar
#
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]
