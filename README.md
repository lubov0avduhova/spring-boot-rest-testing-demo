# 📚 Book CRUD API (Spring Boot)

Этот проект — простой REST API для управления книгами (Book), реализованный с использованием Spring Boot, Spring Data JPA и H2-базы данных.

Проект покрыт как юнит-, так и интеграционными тестами с использованием JUnit 5, Mockito и MockMvc.

---

## 🚀 Функционал

- 📖 Получить книгу по ID (`GET /books/{id}`)
- ➕ Создать книгу (`POST /books`)
- ✏️ Обновить книгу (`PUT /books/{id}`)
- 🗑 Удалить книгу (`DELETE /books/{id}`)

---

## 💡 Технологии

- Java 17
- Spring Boot 3.4
- Spring Web
- Spring Data JPA
- H2 Database
- Bean Validation (JSR-380)
- JUnit 5
- Mockito
- MockMvc

---

## 🧪 Покрытие тестами

- `BookServiceTest` — юнит-тесты логики сервиса
- `BookRepositoryTest` — тесты JPA-репозитория на H2
- `BookControllerIntegrationTest` — полный цикл:
    - создание, получение, изменение, удаление
    - 404 и 400 ошибки
    - валидация DTO

---

## 📦 Как запустить

1. Открой проект в IntelliJ IDEA
2. Убедись, что установлена JDK 17+
3. Выполни:
   ```bash
   ./mvnw spring-boot:run
Приложение будет доступно на:
http://localhost:8080

## 📂 Структура
```
src
├── main
│   └── java
│       └── org.example
│           └── Main.java
│           └── testing
│               ├── controller
│               ├── service
│               ├── repository
│               ├── dto
│               ├── entity
│               ├── exception
├── test
│   └── java
│       └── org.example.testing
│           ├── BookServiceTest
│           ├── BookRepositoryTest
│           ├── BookControllerIntegrationTest
```