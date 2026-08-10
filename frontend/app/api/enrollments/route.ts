import { cookies } from 'next/headers'
import { NextResponse } from 'next/server'

const SUPABASE_URL = process.env.NEXT_PUBLIC_SUPABASE_URL ?? 'https://yguicuybvsvjsneftjpb.supabase.co'
const SUPABASE_KEY = process.env.NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY ?? 'sb_publishable_9UE80CKA8CCc8auw_HBQQw_dzgzeanf'

export async function POST(request: Request) {
  const token = cookies().get('nypunya_access_token')?.value
  if (!token) return NextResponse.json({ error: 'Sign in required.' }, { status: 401 })
  const { courseId } = await request.json()
  if (!courseId) return NextResponse.json({ error: 'courseId is required.' }, { status: 400 })
  const userResponse = await fetch(`${SUPABASE_URL}/auth/v1/user`, { headers: { apikey: SUPABASE_KEY, Authorization: `Bearer ${token}` } })
  if (!userResponse.ok) return NextResponse.json({ error: 'Session expired. Sign in again.' }, { status: 401 })
  const user = await userResponse.json()
  const response = await fetch(`${SUPABASE_URL}/rest/v1/enrollments`, { method: 'POST', headers: { apikey: SUPABASE_KEY, Authorization: `Bearer ${token}`, 'Content-Type': 'application/json', Prefer: 'return=minimal,resolution=ignore-duplicates' }, body: JSON.stringify({ course_id: courseId, student_id: user.id, status: 'ACTIVE', progress: 0 }) })
  if (!response.ok) { const data = await response.text(); return NextResponse.json({ error: data || 'Unable to enroll.' }, { status: response.status }) }
  return NextResponse.json({ ok: true })
}
