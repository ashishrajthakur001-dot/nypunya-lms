# NypunyaLMS — Resume-Faithful Implementation Plan

## Rule
The supplied CV is the authoritative specification for the original NypunyaLMS project. The implementation must use the project stack stated in the CV. No alternative backend stack may replace it.

## Exact CV project stack
- Java 8
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- JWT
- MySQL
- RESTful APIs
- React.js
- Next.js
- Redux
- Tailwind CSS
- Maven
- MySQL Workbench
- Postman
- Swagger/OpenAPI
- Docker
- GitHub Actions
- Microservices
- Eureka Service Discovery
- Spring Cloud API Gateway
- Apache PDFBox
- AWS S3
- SendGrid
- Twilio SMS/WhatsApp
- JUnit
- Bean Validation (Jakarta Validation)
- Zod
- Git/GitHub
- Agile Scrum/JIRA

The CV also lists Kafka, Redis/Spring Cache, Resilience4j, Prometheus/Grafana/OpenTelemetry/ELK, Jenkins, GCP and other technologies in the broader skills section. They must not be represented as confirmed NypunyaLMS project components unless the CV's project section explicitly says so.

## Architecture required by the CV
Microservices + Eureka Service Discovery + Spring Cloud API Gateway. Each service follows layered architecture and SOLID principles:

Controller -> DTO/Validation -> Service -> Repository -> Entity/JPA/Hibernate -> MySQL

Cross-cutting layers: Spring Security/JWT/RBAC, global @ControllerAdvice exception handling, configuration/profiles/CORS/Swagger, and reusable utilities.

## Execution phases

### 1. Backend foundation
- Java 8-compatible Maven project.
- Spring Boot/Spring MVC REST services.
- MySQL schema.
- Spring Data JPA/Hibernate mappings.
- normalized One-to-One, One-to-Many, Many-to-One and Many-to-Many relationships.
- pagination and optimized repository queries.
- DTO request/response mapping.
- Bean Validation.
- global exception handling.
- application profiles, CORS, Swagger/OpenAPI and environment properties.

### 2. Security
- Spring Security.
- JWT generation/validation.
- BCrypt password encryption.
- RBAC for Admin, Trainer and Student.
- authorization at endpoint and resource ownership level.

### 3. Resume-stated functional modules
- user management
- course management
- assignments
- quizzes
- content delivery
- examination
- course enrollment
- assignment workflows
- student progress tracking
- notifications
- report generation
- certificates
- feedback
- admin management of students, trainers, courses, batches, assignments, quizzes, certificates and feedback

### 4. Utilities/integrations
- AWS S3 for assignments, certificates, profile images and learning resources.
- Apache PDFBox for PDF certificates and reports.
- SendGrid for email.
- Twilio for SMS and WhatsApp.

### 5. Microservices infrastructure
- Eureka Service Discovery.
- Spring Cloud API Gateway for centralized routing and registration/discovery integration.

### 6. Frontend
- React.js + Next.js.
- Redux centralized state for authentication, course management, user profiles and assignment workflows.
- Tailwind CSS responsive UI.
- Zod client validation.
- role-based Admin/Trainer/Student portals integrated with backend REST APIs.

### 7. Testing
- JUnit unit tests for business services and REST controllers.
- Spring Boot integration tests.
- Postman API testing.
- Swagger/OpenAPI documentation.

### 8. Deployment and collaboration
- Docker and Docker Compose.
- Kubernetes-based deployment.
- GitHub Actions CI/CD.
- Git/GitHub feature branching, pull requests, code reviews and release management.
- Agile Scrum/JIRA workflow.

## Corner cases to implement and test

### Authentication/security
invalid credentials; missing token; malformed token; expired token; wrong role; disabled account; password handling; CORS failure; IDOR/resource ownership violation; unauthorized mutation.

### Users/courses/enrollment
duplicate email; duplicate enrollment; missing student/course; invalid course state; unauthorized trainer/admin operation; deleted/deactivated user with historical records; pagination boundaries; empty pages; concurrent update.

### Assignments/content
missing assignment; closed assignment; deadline boundary; duplicate submission; retry after timeout; non-enrolled submission; unauthorized grading; invalid score; missing file; unsupported file; oversized file; S3 upload/download failure; missing object; metadata/object inconsistency.

### Quizzes/examination
missing assessment; unauthorized attempt; duplicate attempt; duplicate submit; invalid question/answer; expired/closed attempt; invalid score; concurrent submission; retry causing duplicate result.

### Progress/reports/certificates
progress below 0/above 100; incomplete course; duplicate certificate; concurrent certificate request; PDFBox failure; S3 artifact missing; report generation failure.

### Notifications
invalid recipient; SendGrid/Twilio timeout; provider rejection; retryable failure; duplicate notification; partial channel failure; provider unavailable.

### Distributed system/data
DB constraint violation; transaction rollback; N+1 query; large pagination; gateway failure; Eureka unavailable; downstream service timeout; malformed downstream response; standardized 4xx/5xx response.

## Definition of done
A feature is not done when a screen exists. It must have its backend API, DTOs, validation, authorization, business rules, persistence, exception handling, test coverage, Swagger documentation and failure-path handling. Integrations must be behind configuration and must not expose secrets.

## Explicit boundary
Supabase is not the core NypunyaLMS persistence layer because the CV explicitly specifies MySQL. Existing Supabase work is treated as a separate prototype/demo artifact and must not replace the resume-stated Java/Spring/MySQL architecture.
