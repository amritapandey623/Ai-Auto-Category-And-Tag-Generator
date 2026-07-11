# 🚀 AI Auto-Category & Tag Generator

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot)
![React](https://img.shields.io/badge/React-Vite-61DAFB?style=for-the-badge&logo=react)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon-336791?style=for-the-badge&logo=postgresql)
![Groq](https://img.shields.io/badge/AI-Groq-black?style=for-the-badge)
![JWT](https://img.shields.io/badge/Auth-JWT-blue?style=for-the-badge)

### 🤖 AI-Powered Catalog Automation Platform

Automatically classify products, generate SEO tags, recommend sustainability filters, and store structured AI-generated metadata securely.

</div>

---

# ✨ Features

- 🔐 JWT Authentication (Register & Login)
- 🛡️ Protected REST APIs using Bearer Token
- 🤖 AI-powered Product Categorization
- 📂 Automatic Primary Category Detection
- 📑 Smart Sub-Category Suggestions
- 🏷️ AI Generated SEO Tags (5-10)
- 🌱 Sustainability Filter Recommendations
- 🗄️ JSONB Storage in Neon PostgreSQL
- 📖 Swagger API Documentation
- 🐳 Docker Support
- ☁️ Render Deployment Ready

---

# 🏗️ System Architecture

```mermaid
flowchart LR

User([User])

Frontend[React + Vite Frontend]

Backend[Spring Boot REST API]

Security[Spring Security + JWT]

AI[Groq AI API]

DB[(Neon PostgreSQL)]

User --> Frontend

Frontend -->|HTTP REST API| Backend

Backend --> Security

Security --> Backend

Backend -->|AI Prompt| AI

AI -->|Structured JSON| Backend

Backend --> DB

DB --> Backend

Backend --> Frontend

Frontend --> User
```
---

# ⚙️ Tech Stack

| Category | Technologies |
|-----------|-------------|
| Backend | Spring Boot 3, Spring Security 6, Spring Data JPA, Hibernate |
| Frontend | React, Vite |
| AI | Groq Chat Completions API |
| Database | Neon PostgreSQL (JSONB) |
| Authentication | JWT |
| API Docs | Swagger / Springdoc OpenAPI |
| Deployment | Docker, Render |

---

# 📊 Project Workflow

```mermaid
flowchart TD

A[User Login]
-->B[JWT Generated]

B
-->C[User Enters Product]

C
-->D[Spring Boot API]

D
-->E[Groq AI]

E
-->F[Primary Category]

E
-->G[Sub Category]

E
-->H[SEO Tags]

E
-->I[Sustainability Filters]

F --> J[Create Structured JSON]
G --> J
H --> J
I --> J

J
-->K[Save JSONB to PostgreSQL]

K
-->L[Return Response]
```

---

# 🔐 Authentication Flow

```mermaid
sequenceDiagram

actor User
participant Frontend
participant Backend
participant Database

User->>Frontend: Register/Login
Frontend->>Backend: Credentials

Backend->>Database: Verify User

Database-->>Backend: User Found

Backend-->>Frontend: JWT Token

Frontend->>Backend: Authorization: Bearer JWT

Backend-->>Frontend: Protected API Response
```

---

# 🤖 AI Processing Flow

```mermaid
graph TD

A[Product Name & Description]

-->B[Groq AI]

B

-->C[Primary Category]

B

-->D[Sub Category]

B

-->E[SEO Tags]

B

-->F[Sustainability Filters]

C --> G[Structured JSON]
D --> G
E --> G
F --> G

G --> H[(PostgreSQL JSONB)]
```

---

# 📂 Project Structure

```text
AI-Auto-Category-Tag-Generator
│
├── backend
│   ├── controller
│   ├── security
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   ├── config
│   ├── resources
│   └── Dockerfile
│
├── frontend
│   ├── src
│   │   ├── pages
│   │   ├── components
│   │   ├── services
│   │   └── App.jsx
│   └── Dockerfile
│
├── docker-compose.yml
└── README.md
```

---

# 🔑 Environment Variables

## Backend

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://your-neon-host/neondb?sslmode=require&channelBinding=require
SPRING_DATASOURCE_USERNAME=your_neon_username
SPRING_DATASOURCE_PASSWORD=your_neon_password

GROQ_API_KEY=your_groq_api_key
GROQ_MODEL=llama-3.1-8b-instant

JWT_SECRET=replace_with_a_32_plus_character_secret
JWT_EXPIRATION_MS=86400000

APP_CORS_ALLOWED_ORIGINS=http://localhost:5173

PORT=8081
```

---

## Frontend

```env
VITE_API_URL=http://localhost:8081
```

---

# 🚀 Local Development

## Backend

```bash
cd backend
./mvnw spring-boot:run
```

---

## Frontend

```bash
cd frontend

npm install

npm run dev
```

---

## Open in Browser

Frontend

```
http://localhost:5173
```

Swagger UI

```
http://localhost:8081/swagger-ui.html
```

---

# 🐳 Docker

```bash
docker compose up --build
```

---

# 📡 API Flow

```mermaid
flowchart LR

A[POST Register]
-->B[BCrypt Password]

B
-->C[POST Login]

C
-->D[JWT Token]

D
-->E[Generate Product]

E
-->F[Groq AI]

F
-->G[Structured JSON]

G
-->H[Save to PostgreSQL]

H
-->I[Return Product]
```

---

# 📌 API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/auth/register` | Register User |
| POST | `/api/auth/login` | Login & Get JWT |
| POST | `/api/products/generate-and-save` | Generate AI Metadata |

---

# 🎯 Example AI Response

```json
{
  "category": "Home & Kitchen",
  "subcategory": "Reusable Bottles",
  "seoTags": [
    "Eco Friendly",
    "Reusable",
    "Water Bottle",
    "Leak Proof",
    "Stainless Steel"
  ],
  "sustainabilityFilters": [
    "Plastic Free",
    "Recycled",
    "Reusable"
  ]
}
```

---

# ⭐ Future Improvements

- 📷 Image-based Product Categorization
- 🌍 Multi-language Support
- 📊 Analytics Dashboard
- 🔍 Semantic Product Search
- 📦 Batch Product Processing
- 🤖 Multiple AI Model Support

---

# 👨‍💻 Author

**Amrita Pandey**

Java Full Stack Developer • AI/ML Enthusiast

---

<div align="center">

### ⭐ If you found this project useful, consider giving it a Star ⭐

Made with ❤️ using Spring Boot, React, Groq AI & PostgreSQL

</div>