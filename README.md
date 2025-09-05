# HelpHive

> A lightweight, role-based ticket management system for teams.  
> Description
> HelpHive – Technical Incident Management System

HelpHive is a web application designed to manage technical support tickets in an organized and transparent way.
Users can create tickets to report issues, which are then reviewed and assigned by supervisors or administrators.
Technicians work on the assigned tickets, log their work, and add comments until the issue is resolved.



**HelpHive** is a Spring Boot REST API (Java 21) with MySQL persistence.  
Core pieces you already have in the repo:

- Entities: `User`, `Ticket`, `Comment`, `WorkLog` (with enums for roles, ticket status/priority/color).
- DTOs: `TicketCreateDto`, `TicketUpdateDto`, `TicketDto`, `TicketStatusUpdateDto`, `TicketAssignDto`, etc.
- Layers: `controller`, `service` (+ `service.impl`), `repository`, `mapper` (MapStruct), `security` (JWT), `exception`.
- Swagger/OpenAPI via `springdoc-openapi` — `/v3/api-docs` and /swagger-ui.
- Security: JWT tokens containing user id (uid) and role, `CustomUserPrincipal`, `JwtTokenProvider`, `JwtAuthenticationFilter`, `CustomUserDetailsService`.
- Docker + Docker Compose for app + MySQL.
- GitHub Actions pipeline that builds, tests, and pushes Docker images to Docker Hub.

Roles:
- `ROLE_USER` — create & track own tickets
- `ROLE_TECHNICIAN` — work on assigned tickets
- `ROLE_SUPERVISOR` — assign tickets, monitor
- `ROLE_ADMIN` — manage users & admin actions

---

## Features

- Role-based access control (JWT)
- Ticket lifecycle: create → assign → progress → resolve
- Comments & worklogs on tickets
- Ticket color classification (green/orange/red) based on age
- OpenAPI/Swagger for API exploration
- Dockerized for easy local/dev setups
- CI: GitHub Actions building & publishing Docker images

---

> Built with **Spring Boot + MySQL**, secured with **JWT**, and delivered via **Docker**.

![CI](https://github.com/vitaflash/helphive/actions/workflows/ci-build.yml/badge.svg)
[![Docker Pulls](https://img.shields.io/docker/pulls/vitaflash/helphive?logo=docker)](https://hub.docker.com/r/vitaflash/helphive)
[![Docker Image Size](https://img.shields.io/docker/image-size/vitaflash/helphive/latest?logo=docker)](https://hub.docker.com/r/vitaflash/helphive)


## Run with Docker

This project publishes images automatically to [Docker Hub](https://hub.docker.com/r/vitaflash/helphive).

### Pull the latest image
```bash
docker pull vitaflash/helphive:latest

Run the container (with MySQL via Docker Compose)
# Run app + MySQL (from docker-compose.yml)
docker compose up -d

The API will be available at:
http://localhost:8080

Swagger UI:
http://localhost:8080/swagger-ui/index.html

Tags
latest → always points to the most recent build from main
sha-<shortsha> → immutable image built from a specific commit
