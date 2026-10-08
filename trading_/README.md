# 🚀 TradeSphere — Enterprise Trading Platform

<div align="center">

### *A Production-Ready Full Stack Trading Web Application*

Built with **Spring Boot 4 • Angular 20 • MySQL • JWT Security • Docker**

![Java](https://img.shields.io/badge/Java-21-red?style=for-the-badge\&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-6DB33F?style=for-the-badge\&logo=springboot)
![Angular](https://img.shields.io/badge/Angular-20-DD0031?style=for-the-badge\&logo=angular)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?style=for-the-badge\&logo=mysql)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge\&logo=docker)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

**Enterprise Architecture • Secure REST APIs • Docker Deployment**

</div>

---

## 📖 About The Project

**TradeSphere** is an enterprise-grade trading platform that demonstrates modern full-stack software development practices. The application enables secure user authentication, asset management, portfolio operations, and market data visualization through a scalable Spring Boot backend and Angular frontend.

This project is designed using **layered architecture**, **JWT authentication**, **RESTful APIs**, and **Docker containerization**, making it suitable for real-world deployment and DevOps workflows.

---

## ✨ Features

* 🔐 JWT Authentication & Authorization
* 👤 Secure User Login System
* 📊 Market & Asset Management APIs
* 📈 Candlestick Chart Integration
* 💼 Portfolio Management
* 🌐 RESTful Spring Boot Backend
* ⚡ Responsive Angular Frontend
* 🗄️ MySQL Database with JPA/Hibernate
* 🐳 Docker & Docker Compose Support
* ☁️ Production Deployment Ready

---

## 🏛️ Enterprise Architecture

```text
                    🌍 Client Browser
                           │
                    Angular Frontend
                           │
                 HTTP + JWT Authentication
                           │
                  Spring Boot REST API
                           │
          Spring Security + Business Services
                           │
             JPA / Hibernate Repository Layer
                           │
                    MySQL 8 Database
```

---

## 🛠️ Technology Stack

| Category         | Technology                  |
| ---------------- | --------------------------- |
| Frontend         | Angular 20 + TypeScript     |
| Backend          | Spring Boot 4               |
| Security         | Spring Security + JWT       |
| Database         | MySQL 8                     |
| ORM              | Spring Data JPA + Hibernate |
| Build Tool       | Maven                       |
| Containerization | Docker & Docker Compose     |
| API Testing      | Postman                     |

---

## 📂 Project Structure

```text
TradeSphere
│
├── src/
│   ├── main/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   ├── config/
│   │   └── security/
│   └── resources/
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

## 🔐 Authentication Flow

```text
User Login
     │
     ▼
Spring Security
     │
     ▼
Generate JWT Token
     │
     ▼
Frontend Stores Token
     │
     ▼
Authorization Header
Bearer <JWT_TOKEN>
     │
     ▼
Protected REST APIs
```

---

## 🚀 Getting Started

### 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/TradeSphere.git
cd TradeSphere
```

### 2️⃣ Build Project

```bash
mvn clean package
```

### 3️⃣ Run Application

```bash
mvn spring-boot:run
```

Application starts at:

```text
http://localhost:8080
```

---

# 🐳 Docker Deployment

### Build Docker Image

```bash
docker build -t tradesphere-api:v1 .
```

### Run Container

```bash
docker run -d \
  --name tradesphere-api \
  -p 8080:8080 \
  tradesphere-api:v1
```

### Using Docker Compose

```bash
docker compose up -d --build
```

Stop services:

```bash
docker compose down
```

---

## ⚙️ Environment Variables

Create a `.env` file:

```env
DB_HOST=mysql
DB_PORT=3306
DB_NAME=trading
DB_USER=root
DB_PASSWORD=root

JWT_SECRET=TRADING2026
```

---

## 📡 REST API Modules

| Module       | Description          |
| ------------ | -------------------- |
| `/auth`      | User Authentication  |
| `/assets`    | Asset CRUD           |
| `/market`    | Market Data          |
| `/portfolio` | Portfolio Management |
| `/chart`     | Candlestick Data     |

Example Request:

```http
GET /api/assets
Authorization: Bearer <JWT_TOKEN>
```

---

## 📸 Application Preview

> Add screenshots inside `docs/images/`

```text
docs/images/login.png
docs/images/dashboard.png
docs/images/chart.png
docs/images/portfolio.png
```

Example:

```md
![Dashboard](docs/images/dashboard.png)
```

---

## 🔒 Security

* JWT Stateless Authentication
* BCrypt Password Encryption
* Spring Security Filter Chain
* Protected REST Endpoints
* Environment-based Secrets
* Docker Ready Configuration

---

## 📈 Future Roadmap

* [ ] WebSocket Live Market Prices
* [ ] Redis Caching
* [ ] RabbitMQ Event Streaming
* [ ] GitHub Actions CI/CD
* [ ] AWS EC2 Deployment
* [ ] Kubernetes Support
* [ ] Prometheus & Grafana Monitoring

---

## 👨‍💻 Developer

**Arnave Dubey**

Java Full Stack Developer | Spring Boot | Angular | Docker | MySQL

* 💼 Enterprise Java Development
* ☁️ DevOps & Docker Enthusiast
* 📊 Trading Platform Architecture

---

<div align="center">

### ⭐ If you like this project, give it a Star!

**Built with ❤️ using Spring Boot & Angular**

</div>
