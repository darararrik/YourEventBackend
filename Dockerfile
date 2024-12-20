# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim

# Указываем рабочую директорию внутри контейнера
WORKDIR /app
# Копируем файл .env в контейнер
COPY .env /app/.env

# Копируем собранный .jar файл в контейнер
COPY build/libs/app-0.0.1-SNAPSHOT.jar /app/app-0.0.1-SNAPSHOT.jar

# Открываем порт для приложения
EXPOSE 8081

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app-0.0.1-SNAPSHOT.jar"]
