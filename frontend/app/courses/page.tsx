import Link from 'next/link'
import { getCourses } from '../../lib/supabase-rest'

export const dynamic = 'force-dynamic'

export default async function Courses(){
  const courses = await getCourses()
  return <main style={{maxWidth:1100,margin:'0 auto',padding:40,fontFamily:'Arial,sans-serif'}}>
    <Link href="/" style={{textDecoration:'none'}}>← Dashboard</Link>
    <h1>Courses</h1>
    <p style={{color:'#666'}}>Published learning opportunities from Supabase.</p>
    <div style={{display:'grid',gridTemplateColumns:'repeat(auto-fit,minmax(260px,1fr))',gap:16,marginTop:28}}>
      {courses.map(c=><article key={c.id} style={{border:'1px solid #ddd',borderRadius:12,padding:20}}><h2>{c.title}</h2><p>{c.description ?? 'Course description coming soon.'}</p><p><strong>{c.status}</strong></p><Link href={'/courses/' + c.id} style={{display:'inline-block',padding:'8px 12px',border:'1px solid #bbb',borderRadius:8,textDecoration:'none'}}>View course</Link></article>)}
    </div>
  </main>
}
