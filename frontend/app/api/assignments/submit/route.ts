import { cookies } from 'next/headers'
import { NextResponse } from 'next/server'

const URL = process.env.NEXT_PUBLIC_SUPABASE_URL ?? 'https://yguicuybvsvjsneftjpb.supabase.co'
const KEY = process.env.NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY ?? 'sb_publishable_9UE80CKA8CCc8auw_HBQQw_dzgzeanf'

export async function POST(request: Request) {
  const token = cookies().get('nypunya_access_token')?.value
  if (!token) return NextResponse.json({ error:'Sign in required.' }, {status:401})
  const { assignmentId, content } = await request.json()
  if (!assignmentId || !content?.trim()) return NextResponse.json({error:'Assignment and submission content are required.'},{status:400})
  const headers={apikey:KEY,Authorization:`Bearer ${token}`,'Content-Type':'application/json',Prefer:'return=minimal'}
  const userResponse=await fetch(`${URL}/auth/v1/user`,{headers})
  if(!userResponse.ok)return NextResponse.json({error:'Session expired.'},{status:401})
  const user=await userResponse.json()
  const response=await fetch(`${URL}/rest/v1/assignment_submissions`,{method:'POST',headers,body:JSON.stringify({assignment_id:assignmentId,student_id:user.id,content:content.trim(),status:'SUBMITTED'})})
  if(!response.ok)return NextResponse.json({error:await response.text()},{status:500})
  return NextResponse.json({ok:true})
}
