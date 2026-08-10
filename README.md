# NypunyaLMS

Prototype Learning Management System reconstructed from the NypunyaLMS project described in the supplied resume.

## Canonical branch

`main`

## Live prototype

Frontend is deployed on Vercel from `frontend/`.

## Implemented scope

- Next.js / React / TypeScript frontend
- Supabase PostgreSQL data layer
- Supabase Auth sign-up and sign-in with HTTP-only session cookie
- Role model foundation: Admin / Trainer / Student
- Courses and learner enrollment
- Assignments, text submissions and grading data model
- Quizzes, questions, attempts, automatic server-side scoring and answers
- Learner progress aggregation
- Certificates and course feedback data model
- Row Level Security policies for learner-owned records
- Java 8 / Spring Boot backend services for the documented service-domain prototype

## Backend domains

- User/authentication foundations
- Course and enrollment foundations
- Assignment and submission workflow
- Assessment/quiz workflow
- Progress aggregation

## Frontend routes

- `/` dashboard
- `/login` authentication
- `/courses` course catalogue
- `/courses/[id]` course detail and enrollment
- `/assignments` assignment submission workflow
- `/assessments` quiz catalogue
- `/assessments/[id]` interactive quiz attempt and server-side scoring
- `/progress` learner progress
- `/api/health` deployment health endpoint

## Supabase

The project is connected to the configured Supabase project. Public configuration can be supplied with:

```text
NEXT_PUBLIC_SUPABASE_URL
NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY
```

The frontend contains safe public-key fallbacks for the configured prototype environment so the deployment remains runnable without local environment setup. Never place a Supabase service-role key in frontend code.

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

## Prototype completion notes

The production UI and database are connected. The Java/Spring Boot services remain the reference backend implementation and CI build target; the hosted learner prototype uses Supabase-backed Next.js routes for the end-to-end browser workflow because the current deployment environment does not provide a Java application host.
