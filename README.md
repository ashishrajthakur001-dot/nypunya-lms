# NypunyaLMS

Prototype Learning Management System reconstructed from the NypunyaLMS project described in the supplied resume.

## Canonical development branch

`feature/nypunya-canonical`

## Prototype scope

- Next.js frontend
- Supabase PostgreSQL target data layer
- Java 8 / Spring Boot backend services used for the implemented service-domain prototype
- Role model: Admin / Trainer / Student
- Courses and enrollment
- Assignments, submissions and grading
- Quizzes, questions, options, attempts and automatic scoring
- Learner course progress
- Certificates and feedback remain planned prototype domains
- Supabase Row Level Security remains part of the deployment/integration phase

## Backend domains implemented on the canonical branch

- User/authentication foundations
- Course and enrollment foundations
- Assignment and submission workflow
- Assessment/quiz workflow
- Progress aggregation

## Frontend

A Next.js/React/TypeScript learner dashboard shell is included under `frontend/` and is the base for connecting the backend APIs.

## Grounding

The supplied resume is the source of truth for the original project's documented technologies and capabilities. Architecture and implementation details not explicitly documented there are labeled as prototype/research-informed decisions.

## Build

Backend:

```bash
cd backend
mvn clean verify
```

Frontend:

```bash
cd frontend
npm install
npm run build
npm start
```

Environment-specific database, authentication, Supabase configuration, CI/CD and production deployment are intentionally treated as the completion/hardening phase rather than silently mocked.
