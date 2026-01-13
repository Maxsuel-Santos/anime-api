# Anime API 🎌

API REST desenvolvida em **Spring Boot** para gerenciamento de animes com autenticação JWT, PostgreSQL e Docker.

---

## 🚀 Tecnologias
- Java 21
- Spring Boot
- Spring Security + JWT
- PostgreSQL
- Docker & Docker Compose
- Maven

---

## 🔐 Autenticação
A API usa autenticação **JWT Stateless**.

Fluxo:
1. Usuário faz login
2. Recebe token JWT
3. Usa o token no header Authorization

```
Authorization: Bearer SEU_TOKEN
```

---

## 📌 Endpoints

### 🔑 Auth
| Método | Rota | Descrição |
|------|------|-----------|
| POST | /auth/login | Autentica e retorna JWT |
| POST | /auth/register | Cria novo usuário |

### 🎬 Animes
| Método | Rota | Protegido |
|-------|------|----------|
| GET | /animes | Sim |
| POST | /animes | Sim |
| PUT | /animes/{id} | Sim |
| DELETE | /animes/{id} | Sim |

---

## 🐳 Docker

Subir tudo:
```bash
docker compose up --build
```

Portas:
- API: http://localhost:8080
- PostgreSQL: 5433
- PgAdmin: http://localhost:5050

---

## 🧪 Testar com Postman

### 1️⃣ Criar usuário
POST `/auth/register`
```json
{
  "username": "admin",
  "password": "123"
}
```

### 2️⃣ Login
POST `/auth/login`
```json
{
  "username": "admin",
  "password": "123"
}
```

Copie o token retornado.

### 3️⃣ Usar token
Header:
```
Authorization: Bearer SEU_TOKEN
```

Agora você pode acessar `/animes`.

---

## 🧩 Arquitetura

```
controller → service → repository → database
                ↓
           security (JWT)
```

---
