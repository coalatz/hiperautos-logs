# Etapa 1: build com Maven (não precisa ter Maven instalado localmente)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Baixa dependências separado do build (aproveita cache do Docker)
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn package -DskipTests -q

# Etapa 2: imagem de runtime enxuta (sem Maven, sem JDK completo)
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
