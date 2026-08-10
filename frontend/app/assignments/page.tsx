import Link from 'next/link'
import SubmissionForm from './SubmissionForm'
import { getAssignments, getCourseMap } from '../../lib/supabase-rest'

export const dynamic = 'force-dynamic'

export default async function Assignments(){
  const [assignments, courses] = await Promise.all([getAssignments(), getCourseMap()])
  return <main style={{maxWidth:1100,margin:'0 auto',padding:40,fontFamily:'Arial,sans-serif'}}><Link href="/">← Dashboard</Link><h1>Assignments</h1><p style={{color:'#666'}}>Submission and grading work is backed by the assignment domain.</p><div style={{display:'grid',gap:16,marginTop:28}}>{assignments.map(a=><article key={a.id} style={{border:'1px solid #ddd',borderRadius:12,padding:20}}><h2>{a.title}</h2><p>{courses.get(a.course_id)?.title ?? 'Course'}</p><p>{a.description ?? 'No description provided.'}</p><p><strong>{a.status}</strong> · Max score {a.max_score}{a.due_at ? ` · Due ${new Date(a.due_at).toLocaleString()}` : ''}</p><SubmissionForm assignmentId={a.id}/></article>)}</div>{!assignments.length&&<p>No assignments are currently published.</p>}</main>
}
