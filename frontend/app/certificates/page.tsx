import Link from 'next/link'
import { cookies } from 'next/headers'

const URL=process.env.NEXT_PUBLIC_SUPABASE_URL ?? 'https://yguicuybvsvjsneftjpb.supabase.co'
const KEY=process.env.NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY ?? 'sb_publishable_9UE80CKA8CCc8auw_HBQQw_dzgzeanf'
export const dynamic='force-dynamic'

export default async function Certificates(){
  const token=cookies().get('nypunya_access_token')?.value
  if(!token)return <main style={{padding:40,fontFamily:'Arial,sans-serif'}}><Link href="/">← Dashboard</Link><h1>Certificates</h1><p>Sign in to view certificates.</p></main>
  const response=await fetch(`${URL}/rest/v1/certificates?select=id,certificate_number,course_id,issued_at,status&order=issued_at.desc`,{headers:{apikey:KEY,Authorization:`Bearer ${token}`},cache:'no-store'})
  const certificates=response.ok?await response.json():[]
  return <main style={{maxWidth:900,margin:'0 auto',padding:40,fontFamily:'Arial,sans-serif'}}><Link href="/">← Dashboard</Link><h1>Certificates</h1><p style={{color:'#666'}}>Issued course completion certificates.</p>{certificates.length?certificates.map((c:{id:string;certificate_number:string;course_id:string;issued_at:string;status:string})=><article key={c.id} style={{border:'1px solid #ddd',borderRadius:12,padding:20,marginTop:12}}><h2>{c.certificate_number}</h2><p>Course: {c.course_id}</p><p>Issued: {new Date(c.issued_at).toLocaleDateString()} · {c.status}</p></article>):<p>No certificates have been issued yet.</p>}</main>
}
