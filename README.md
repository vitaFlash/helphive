# 🐝 HelpHive

> A lightweight, role-based ticket management system for teams.  
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
