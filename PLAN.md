# NypunyaLMS Prototype — Implementation Plan

## Source-of-truth rule

The supplied resume is the authoritative source for what the original NypunyaLMS project explicitly contained. The resume states Java 8, Spring Boot, Spring MVC, Spring Security, Spring Data JPA, Hibernate, JWT, MySQL, REST APIs, React.js, Next.js, Redux; Docker, GitHub Actions, Postman, Swagger/OpenAPI; microservices, Eureka and Spring Cloud API Gateway; and Agile Scrum. It explicitly lists user management, course management, assignments, quizzes, content delivery, examination, enrollment, progress tracking, notifications, report generation, certificates, feedback, RBAC for Admin/Trainer/Student, DTOs, global exception handling, configuration, utilities, S3, PDFBox, SendGrid, Twilio, validation, JUnit, Kubernetes deployment, Git/GitHub workflows and code reviews.

Anything not explicitly supported by the resume must be labeled as prototype/research-informed. In particular, this plan must not invent production metrics, exact historical endpoints, exact service names, Kafka usage, Redis usage, observability topology, or business rules that the resume does not state.

## Phase 1 — faithful backend foundation
- Java 8-compatible Maven multi-module structure.
- Spring Boot services with layered architecture: Controller, Service, Repository, Entity, DTO, Security, Exception Handling, Configuration, Utility.
- REST APIs for user management, course management, assignments, quizzes, content delivery and examination.
- MySQL schema using normalized relationships and JPA/Hibernate.
- Pagination and query optimization hooks.
- Bean Validation.
- Global `@ControllerAdvice` exception responses.
- Swagger/OpenAPI.
- JWT + Spring Security + RBAC: Admin, Trainer, Student.
- BCrypt password handling and CORS configuration.

## Phase 2 — business workflows
- Course enrollment.
- Assignment workflow.
- Student progress tracking.
- Notifications.
- Report generation.
- Certificates.
- Feedback.
- Admin portal operations for students, trainers, courses, batches, assignments, quizzes, certificates and feedback.

## Phase 3 — frontend
- React.js / Next.js.
- Redux state for authentication, course management, user profiles and assignment workflows.
- Tailwind CSS responsive UI.
- Zod client validation.
- Role-based Admin/Trainer/Student experiences.

## Phase 4 — integrations
- AWS S3 for assignments, certificates, profile images and learning resources.
- Apache PDFBox for certificate/report generation.
- SendGrid email notifications.
- Twilio SMS and WhatsApp notifications.

## Phase 5 — microservices and delivery
- Eureka Service Discovery.
- Spring Cloud API Gateway.
- Docker / Docker Compose.
- Kubernetes deployment manifests.
- GitHub Actions CI/CD.
- Git feature branches, pull requests and review-ready changes.

## Phase 6 — test and hardening
- JUnit unit tests for business services and REST controllers.
- Postman API collection.
- Authorization matrix tests.
- Validation tests.
- Persistence and relationship tests.
- Failure-path tests for files, PDF generation and notification providers.
- Pagination and duplicate-operation tests.
- Deployment smoke tests.

## Corner-case matrix

### Authentication/security
- invalid credentials
- expired/invalid JWT
- missing bearer token
- insufficient role
- disabled user
- password handling
- CORS failures
- direct access to another user's resource

### Courses/enrollment
- duplicate enrollment
- enrollment for missing course/user
- inactive/invalid course state
- unauthorized trainer mutation
- pagination boundaries and empty pages

### Assignments
- missing assignment
- closed assignment
- duplicate submission
- missing/oversized/unsupported file
- submission by non-enrolled student
- trainer grading unauthorized submission
- score outside allowed range
- concurrent submission attempts

### Quizzes/examination
- missing assessment
- unauthorized attempt
- duplicate submission
- invalid answer/question IDs
- attempt after close/expiry
- score consistency
- concurrent submit/retry behavior

### Progress/certificates
- progress below 0 or above 100
- duplicate certificate issuance
- incomplete course certificate request
- PDF generation failure
- stale/missing S3 artifact

### Notifications
- invalid recipient
- provider timeout
- provider rejection
- retry safety / duplicate notification prevention
- partial provider failure

### Data/infrastructure
- DB constraint violations
- transaction rollback
- N+1 queries
- pagination on large data
- S3 failure
- service unavailable through gateway
- discovery failure
- malformed requests
- standardized 4xx/5xx responses

## Acceptance criteria

The prototype is complete only when every resume-stated responsibility has a corresponding code/module, API or documented integration, with unsupported historical claims explicitly marked as prototype decisions. A feature is not considered done merely because a UI screen exists; it must have validation, authorization, persistence behavior and failure-path handling appropriate to the feature.
