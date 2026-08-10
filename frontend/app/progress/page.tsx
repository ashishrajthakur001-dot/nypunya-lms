import Link from 'next/link'
import { getCourses, getEnrollments, DEMO_STUDENT_ID } from '../../lib/supabase-rest'

export const dynamic = 'force-dynamic'

export default async function Progress(){
  const [courses, enrollments] = await Promise.all([getCourses(), getEnrollments(DEMO_STUDENT_ID)])
  const courseMap = new Map(courses.map(course => [course.id, course.title]))
  const overall = enrollments.length ? Math.round(enrollments.reduce((sum, item) => sum + Number(item.progress), 0) / enrollments.length) : 0
  return <main style={{maxWidth:1100,margin:'0 auto',padding:40,fontFamily:'Arial,sans-serif'}}>
    <Link href="/">← Dashboard</Link><h1>My Progress</h1>
    <div style={{marginTop:28,border:'1px solid #ddd',borderRadius:12,padding:24}}><h2>Overall completion</h2><div style={{fontSize:42,fontWeight:700}}>{overall}%</div><p style={{color:'#666'}}>Progress is calculated from learner enrollments.</p></div>
    <section style={{marginTop:28}}><h2>Enrolled courses</h2>{enrollments.length ? enrollments.map(item=><article key={item.id} style={{border:'1px solid #ddd',borderRadius:10,padding:16,marginTop:10}}><strong>{courseMap.get(item.course_id) ?? 'Course'}</strong><p>{item.progress}% complete · {item.status}</p></article>) : <p>No enrollments yet. Enrollments will appear here as the learner starts courses.</p>}</section>
  </main>
}
