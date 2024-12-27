# Используем официальный образ OpenJDK 17
FROM openjdk:17-jdk-slim AS build

# Устанавливаем рабочую директорию для Gradle
WORKDIR /app

# Устанавливаем необходимые пакеты (wget, unzip) и Gradle
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    && wget https://services.gradle.org/distributions/gradle-8.10.2-bin.zip -P /tmp \
    && unzip /tmp/gradle-8.10.2-bin.zip -d /opt/gradle \
    && ln -s /opt/gradle/gradle-8.10.2 /opt/gradle/latest \
    && echo "export PATH=\$PATH:/opt/gradle/latest/bin" >> ~/.bashrc \
    # Удаляем временные файлы, чтобы уменьшить размер образа
    && rm -rf /tmp/* /var/lib/apt/lists/*

# Копируем локальный код в контейнер
COPY . .

# Загружаем зависимости Gradle (кэширование)
# Запускаем Gradle для создания JAR файла
RUN ./gradlew --no-daemon bootJar

# Используем официальный образ для Spring Boot приложения
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию для финального приложения
WORKDIR /app

# Копируем .env файл в контейнер
COPY .env .env

# Копируем JAR файл из стадии сборки
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Открываем порт для приложения
EXPOSE 8081

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]