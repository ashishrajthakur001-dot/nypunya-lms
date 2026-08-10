insert into public.courses (title,description,status)
select * from (values
  ('Java & Spring Boot Fundamentals','Build production-style Java and Spring Boot services with validation, persistence and testing.','PUBLISHED'),
  ('Microservices Architecture','Explore service boundaries, API gateways, discovery and resilient service communication.','PUBLISHED'),
  ('React & Next.js Application Development','Build a modern learner-facing application with React and the Next.js App Router.','PUBLISHED')
) as seed(title,description,status)
where not exists (select 1 from public.courses c where c.title=seed.title);

insert into public.assignments (course_id,title,description,due_at,max_score,status)
select c.id,'Build a REST API','Implement a small Spring Boot REST API with validation and tests.',now()+interval '7 days',100,'PUBLISHED'
from public.courses c where c.title='Java & Spring Boot Fundamentals'
and not exists (select 1 from public.assignments a where a.course_id=c.id and a.title='Build a REST API');

insert into public.assignments (course_id,title,description,due_at,max_score,status)
select c.id,'Next.js Dashboard','Create a responsive dashboard using the App Router.',now()+interval '10 days',100,'PUBLISHED'
from public.courses c where c.title='React & Next.js Application Development'
and not exists (select 1 from public.assignments a where a.course_id=c.id and a.title='Next.js Dashboard');

insert into public.quizzes (course_id,title,duration_minutes,max_score,status)
select c.id,'Spring Boot Fundamentals Quiz',20,100,'PUBLISHED'
from public.courses c where c.title='Java & Spring Boot Fundamentals'
and not exists (select 1 from public.quizzes q where q.course_id=c.id and q.title='Spring Boot Fundamentals Quiz');

insert into public.quizzes (course_id,title,duration_minutes,max_score,status)
select c.id,'Next.js Application Quiz',15,100,'PUBLISHED'
from public.courses c where c.title='React & Next.js Application Development'
and not exists (select 1 from public.quizzes q where q.course_id=c.id and q.title='Next.js Application Quiz');

insert into public.quiz_questions (quiz_id,question_text,option_a,option_b,option_c,option_d,correct_option,points)
select q.id,'Which annotation exposes a REST controller in Spring Boot?','@Entity','@RestController','@Repository','@Component','B',1
from public.quizzes q where q.title='Spring Boot Fundamentals Quiz'
and not exists (select 1 from public.quiz_questions qq where qq.quiz_id=q.id);

insert into public.quiz_questions (quiz_id,question_text,option_a,option_b,option_c,option_d,correct_option,points)
select q.id,'Which file convention is used by the Next.js App Router?','pages/index.tsx','app/page.tsx','src/routes.ts','routes/index.ts','B',1
from public.quizzes q where q.title='Next.js Application Quiz'
and not exists (select 1 from public.quiz_questions qq where qq.quiz_id=q.id);
