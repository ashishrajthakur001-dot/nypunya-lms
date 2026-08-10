import Link from 'next/link'
import { getAssignments, getCourses, getEnrollments, getQuizzes, getProfile } from '../lib/supabase-rest'

export const dynamic = 'force-dynamic'

export default async function Home() {
  const [courses, assignments, quizzes, enrollments, profile] = await Promise.all([getCourses(), getAssignments(), getQuizzes(), getEnrollments(), getProfile()])
  const averageProgress = enrollments.length ? Math.round(enrollments.reduce((sum, item) => sum + Number(item.progress), 0) / enrollments.length) : 0
  const modules = [['/courses','Courses',`${courses.length} published learning paths`],['/assignments','Assignments',`${assignments.length} assignments available`],['/assessments','Assessments',`${quizzes.length} quizzes available`],['/progress','Progress',`${averageProgress}% average completion`]]
  return <main style={{maxWidth:1100,margin:'0 auto',padding:40,fontFamily:'Arial,sans-serif'}}><header style={{display:'flex',justifyContent:'space-between',alignItems:'center',marginBottom:48}}><div><h1 style={{margin:0}}>NypunyaLMS</h1><p style={{color:'#666'}}>Learning management prototype · Supabase connected</p></div>{profile ? <span style={{padding:'8px 12px',borderRadius:999,background:'#eef6ff',fontSize:13}}>{profile.full_name} · {profile.role}</span> : <Link href="/login" style={{padding:'10px 16px',border:'1px solid #bbb',borderRadius:8,textDecoration:'none'}}>Sign in</Link>}</header><section style={{padding:'32px 0'}}><h2>Continue learning</h2><p>Courses, assignments, assessments and learner progress are backed by the project database.</p></section><section style={{display:'grid',gridTemplateColumns:'repeat(auto-fit,minmax(220px,1fr))',gap:16}}>{modules.map(([href,title,description])=><article key={title} style={{border:'1px solid #ddd',borderRadius:12,padding:20}}><h3>{title}</h3><p style={{color:'#666'}}>{description}</p><Link href={href} style={{display:'inline-block',padding:'8px 12px',border:'1px solid #bbb',borderRadius:8,textDecoration:'none'}}>Open</Link></article>)}</section></main>
}
