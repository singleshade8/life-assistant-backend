# Life Assistant Backend (Spring Boot)

A personal productivity backend built with Spring Boot and MySQL.

## Features
- Task management (create, list, complete, today status)
- Habit tracking with daily logs
- Habit streak calculation
- Weekly insights & best habit analytics
- Daily reminder scheduler (background job)

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL
- Postman (API testing)

## APIs (Sample)
- POST /api/tasks
- GET /api/tasks
- PUT /api/tasks/{id}/complete
- POST /api/habits
- POST /api/habits/{id}/today/complete
- GET /api/insights/habits/weekly/summary

## How to Run
1. Configure MySQL in `application.properties`
2. Run the Spring Boot application
3. Test APIs using Postman

---

Built as a personal project to explore backend development and productivity tools.
