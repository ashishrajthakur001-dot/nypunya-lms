import Link from 'next/link'
import { getCourseMap, getQuizzes } from '../../lib/supabase-rest'

export const dynamic = 'force-dynamic'

export default async function Assessments(){
  const [quizzes, courses] = await Promise.all([getQuizzes(), getCourseMap()])
  return <main style={{maxWidth:1100,margin:'0 auto',padding:40,fontFamily:'Arial,sans-serif'}}><Link href="/">← Dashboard</Link><h1>Assessments</h1><p style={{color:'#666'}}>Quizzes are loaded from the assessment domain.</p><div style={{display:'grid',gap:16,marginTop:28}}>{quizzes.map(q=><article key={q.id} style={{border:'1px solid #ddd',borderRadius:12,padding:20}}><h2>{q.title}</h2><p>{courses.get(q.course_id)?.title ?? 'Course'}</p><p>{q.duration_minutes} minutes · {q.max_score} points</p><p><strong>{q.status}</strong></p><Link href={'/assessments/' + q.id} style={{display:'inline-block',padding:'8px 12px',border:'1px solid #bbb',borderRadius:8,textDecoration:'none'}}>Start quiz</Link></article>)}</div>{!quizzes.length&&<p>No assessments are currently published.</p>}</main>
}
