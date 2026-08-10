import Link from 'next/link'
import EnrollButton from '../EnrollButton'
import FeedbackForm from '../../feedback/FeedbackForm'
import { getAssignments, getCourses, getEnrollments, getQuizzes, getProfile } from '../../../lib/supabase-rest'

export const dynamic = 'force-dynamic'

export default async function CourseDetail({ params }: { params: { id: string } }) {
  const [courses, assignments, quizzes, profile] = await Promise.all([getCourses(), getAssignments(), getQuizzes(), getProfile()])
  const course = courses.find(item => item.id === params.id)
  if (!course) return <main style={{padding:40,fontFamily:'Arial,sans-serif'}}><h1>Course not found</h1><Link href="/courses">Back to courses</Link></main>
  const courseAssignments = assignments.filter(item => item.course_id === course.id)
  const courseQuizzes = quizzes.filter(item => item.course_id === course.id)
  const enrollments = profile ? await getEnrollments(profile.id) : []
  const enrolled = enrollments.some(item => item.course_id === course.id)
  return <main style={{maxWidth:900,margin:'0 auto',padding:40,fontFamily:'Arial,sans-serif'}}><Link href="/courses">← Courses</Link><h1>{course.title}</h1><p>{course.description}</p><p><strong>Status:</strong> {course.status}</p>{enrolled ? <p style={{color:'green'}}><strong>You are enrolled.</strong></p> : <EnrollButton courseId={course.id} />}<section style={{marginTop:32}}><h2>Assignments</h2>{courseAssignments.length ? courseAssignments.map(a=><article key={a.id} style={{border:'1px solid #ddd',borderRadius:10,padding:16,marginTop:10}}><strong>{a.title}</strong><p>{a.description ?? 'No description provided.'}</p><small>Max score: {a.max_score}</small></article>) : <p>No assignments published yet.</p>}</section><section style={{marginTop:32}}><h2>Assessments</h2>{courseQuizzes.length ? courseQuizzes.map(q=><article key={q.id} style={{border:'1px solid #ddd',borderRadius:10,padding:16,marginTop:10}}><strong>{q.title}</strong><p>{q.duration_minutes} minutes · {q.max_score} points</p></article>) : <p>No assessments published yet.</p>}</section>{enrolled&&<section style={{marginTop:32}}><h2>Course feedback</h2><FeedbackForm courseId={course.id}/></section>}</main>
}
