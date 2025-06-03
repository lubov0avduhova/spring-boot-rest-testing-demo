# Book CRUD API (Spring Boot + PostgreSQL + Docker + Flyway + Swagger)

REST API для управления книгами: создание, обновление, удаление и получение книг по ID.  
Проект построен с использованием Spring Boot, PostgreSQL, Flyway для миграций, Testcontainers для тестов и полностью задокументирован через Swagger/OpenAPI.

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
- MapStruct — автоматический маппинг DTO ↔ Entity
- Flyway — управление миграциями схемы БД
- Swagger / Springdoc OpenAPI — автоматическая документация
- PostgreSQL (через Docker)
- Bean Validation (JSR-380)
- JUnit 5
- Mockito
- MockMvc
- Testcontainers (PostgreSQL)
- Docker / Docker Compose
- Lombok

---
## Архитектура DTO ↔ Entity

Для преобразования между слоями Entity и DTO используется библиотека **MapStruct**:

- `BookMapper` — интерфейс маппера между `Book`, `BookRequest` и `BookResponse`
- Генерация кода выполняется на этапе компиляции
- Повышает читаемость, тестируемость и устраняет ручной маппинг
---

## API-документация (Swagger)

После запуска проекта Swagger UI доступен по адресу:  
 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- Все методы контроллера задокументированы
- Примеры запросов/ответов
- Описание структуры ошибок (`ErrorResponse`)

---

## Покрытие тестами

- `BookDTOServiceTest` — юнит-тесты (Mockito + `@Mock`)
- `BookRepositoryTest` — интеграционные тесты JPA с **Testcontainers**
- `BookDTOControllerTest` — тест контроллера с моками (`@WebMvcTest`)
- `BookControllerIntegrationTest` — полный API-флоу: создание, обновление, удаление, ошибки (`@SpringBootTest + PostgreSQL`)
- `AbstractTestcontainersTest` — конфигурация Testcontainers с `@DynamicPropertySource`
---

## Как запустить

### Через Docker + PostgreSQL + Flyway

1. Собери проект:
   ```bash
   mvn clean package -DskipTests
2. Запусти приложение и базу:

 ```bash
docker-compose up --build
```

Приложение будет доступно на:
http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui.html
---

## Миграции Flyway
- Все миграции находятся в src/main/resources/db/migration

- Применяются автоматически при запуске через Spring Boot

Текущие миграции:

- V1__create_book_table.sql

- V2__insert_sample_data.sql
---

## Как запустить тесты
Прогнать все тесты (юнит, интеграционные, Testcontainers):

 ```bash
mvn clean test
```
- Автоматически поднимается PostgreSQL через Testcontainers

- Flyway применяет миграции в изолированной среде

- Проверяется поведение API, JPA и схемы БД
___

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
                └── mapper
├── test
│   └── java
│       └── org.example.testing
│           ├── BookDTOServiceTest
│           ├── BookDTOControllerTest
│           ├── BookRepositoryTest
│           ├── BookControllerIntegrationTest
│           └── AbstractTestcontainersTest
└── resources/db/migration
    ├── V1__create_book_table.sql
    └── V2__insert_sample_data.sql
```