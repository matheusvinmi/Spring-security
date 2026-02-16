FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR gerado pelo Maven/Gradle para o container
COPY target/spring-security-0.0.1-SNAPSHOT.jar app.jar

# Define a porta que a aplicação vai usar (padrão: 8080)
EXPOSE 8080

# Comando para iniciar a aplicação quando o container for rodado
ENTRYPOINT ["java", "-jar", "/app/app.jar"]