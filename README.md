# AI Auto-Category & Tag Generator

A full-stack catalog automation app built with Spring Boot, React, Groq AI, JWT authentication, and Neon PostgreSQL.

## Features

- Register and log in with JWT authentication
- Protect catalog APIs with `Authorization: Bearer <token>`
- Auto-assign a primary category from a predefined list
- Suggest a sub-category
- Generate 5-10 SEO tags
- Suggest sustainability filters such as plastic-free, compostable, vegan, organic, and recycled
- Store generated structured JSON in PostgreSQL using JSONB columns
- Swagger UI for backend API documentation
- Docker and Render deployment support

## Tech Stack

- Backend: Spring Boot 3, Spring Security 6, JPA/Hibernate, PostgreSQL
- Frontend: React, Vite
- AI: Groq chat completions API
- Database: Neon PostgreSQL
- Docs: Springdoc OpenAPI / Swagger

## Environment Variables

Backend variables are read from environment variables. For local development, copy `backend/.env.example` to `backend/.env`.

```properties
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

Frontend variables can be set in `frontend/.env`.

```properties
VITE_API_URL=http://localhost:8081
```

## Local Development

Start the backend:

```bash
cd backend
./mvnw spring-boot:run
```

Start the frontend:

```bash
cd frontend
npm install
npm run dev
```

Open:

- Frontend: `http://localhost:5173`
- Swagger UI: `http://localhost:8081/swagger-ui.html`

## Docker

```bash
docker compose up --build
```

The Docker setup reads backend secrets from `backend/.env`.

## API Flow

1. `POST /api/auth/register` creates a user with a BCrypt password.
2. `POST /api/auth/login` returns a JWT token and user details.
3. `POST /api/products/generate-and-save` requires `Authorization: Bearer <token>`.
4. The backend asks Groq for structured product metadata.
5. The product and generated JSON are stored in Neon PostgreSQL.
