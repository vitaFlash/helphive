# HelpHive

> A lightweight, role-based ticket management system for teams.  
> Description
> HelpHive – Technical Incident Management System

HelpHive is a web application designed to manage technical support tickets in an organized and transparent way.
Users can create tickets to report issues, which are then reviewed and assigned by supervisors or administrators.
Technicians work on the assigned tickets, log their work, and add comments until the issue is resolved.

Roles
User: creates and tracks their own tickets.
Technician: manages and resolves assigned tickets.
Supervisor: monitors all tickets and assigns them to technicians.
Administrator: full control over users, roles, and system settings.
HelpHive ensures a clear workflow from problem reporting to resolution, improving communication and accountability across the team.

> Built with **Spring Boot + MySQL**, secured with **JWT**, and delivered via **Docker**.

![CI](https://github.com/vitaflash/helphive/actions/workflows/docker.yml/badge.svg)
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
