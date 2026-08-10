# NypunyaLMS deployment

## Vercel full-stack deployment

The repository is configured for Vercel Services: Next.js frontend plus five Java 8/Spring Boot services built as OCI containers.

### Vercel project settings

Set the project's **Framework Preset** to **Services**. The repository root contains `vercel.json` with the service definitions and rewrites.

Services:

- `frontend` -> `frontend/` -> Next.js
- `user_service` -> `backend/Dockerfile.user.vercel`
- `course_service` -> `backend/Dockerfile.course.vercel`
- `assignment_service` -> `backend/Dockerfile.assignment.vercel`
- `assessment_service` -> `backend/Dockerfile.assessment.vercel`
- `progress_service` -> `backend/Dockerfile.progress.vercel`

Public API paths are routed by prefix:

- `/api/users/**`
- `/api/courses/**`
- `/api/assignments/**`
- `/api/assessments/**`
- `/api/progress/**`

### Required production environment variables

The Java services use these variables:

```text
DB_URL=jdbc:mysql://<mysql-host>:3306/<database>?useSSL=true&serverTimezone=UTC
DB_USERNAME=<mysql-user>
DB_PASSWORD=<mysql-password>
JWT_SECRET=<long-random-secret>
JWT_EXPIRATION_MS=3600000
```

Do not commit real credentials.

PlanetScale is MySQL-compatible and can be connected through Vercel's Marketplace. If using the PlanetScale integration, map its generated connection details to the `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` variables expected by Spring Boot.

### Local container verification

From `backend/`:

```bash
docker build -f Dockerfile.user.vercel .
docker build -f Dockerfile.course.vercel .
docker build -f Dockerfile.assignment.vercel .
docker build -f Dockerfile.assessment.vercel .
docker build -f Dockerfile.progress.vercel .
```

Each container listens on `$PORT` and is therefore compatible with Vercel's container runtime.

### Important runtime constraint

Vercel container services are stateless HTTP workloads. MySQL is external persistent infrastructure; do not attempt to persist database files inside a Vercel container.
