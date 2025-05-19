# Book CRUD API (Spring Boot + PostgreSQL + Docker)

Этот проект — REST API для управления книгами, реализованный на Spring Boot с использованием PostgreSQL, Docker и полной системой тестирования (юнит, интеграционные, Testcontainers).

---

## Функционал

- Получить книгу по ID (`GET /books/{id}`)
- Создать книгу (`POST /books`)
- Обновить книгу (`PUT /books/{id}`)
- Удалить книгу (`DELETE /books/{id}`)

---

## Технологии

- Java 17
- Spring Boot 3.4
- Spring Web / Spring Data JPA
- PostgreSQL (через Docker)
- Bean Validation (JSR-380)
- JUnit 5
- Mockito
- MockMvc
- **Testcontainers** (PostgreSQL)
- Docker / Docker Compose

---

## Покрытие тестами

- `BookDTOServiceTest` — юнит-тесты (Mockito + `@Mock`)
- `BookRepositoryTest` — интеграционные тесты JPA с **Testcontainers**
- `BookDTOControllerTest` — тест контроллера с моками (`@WebMvcTest`)
- `BookControllerIntegrationTest` — полный API-флоу: создание, обновление, удаление, ошибки (`@SpringBootTest + PostgreSQL`)

---

## Как запустить

### Через Docker + PostgreSQL

1. Собери проект:
   ```bash
   mvn clean package -DskipTests
2. Запусти приложение и базу:

 ```bash
docker-compose up --build
```

Приложение будет доступно на:
http://localhost:8080

## Как запустить тесты
Прогнать все тесты (юнит, интеграционные, Testcontainers):

 ```bash
mvn clean test
```
При этом будет автоматически запущен контейнер с PostgreSQL в памяти (через Testcontainers).

## Структура проекта
```
src
├── main
│   └── java
│       └── org.example
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
│           ├── BookDTOServiceTest
│           ├── BookDTOControllerTest
│           ├── BookRepositoryTest
│           ├── BookControllerIntegrationTest
│           └── AbstractTestcontainersTest
```