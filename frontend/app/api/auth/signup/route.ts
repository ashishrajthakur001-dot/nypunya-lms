import { NextResponse } from 'next/server'

const SUPABASE_URL = process.env.NEXT_PUBLIC_SUPABASE_URL ?? 'https://yguicuybvsvjsneftjpb.supabase.co'
const SUPABASE_KEY = process.env.NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY ?? 'sb_publishable_9UE80CKA8CCc8auw_HBQQw_dzgzeanf'

export async function POST(request: Request) {
  const { email, password, fullName } = await request.json()
  if (!email || !password || !fullName) return NextResponse.json({ error: 'Name, email and password are required.' }, { status: 400 })
  if (password.length < 8) return NextResponse.json({ error: 'Password must be at least 8 characters.' }, { status: 400 })
  const response = await fetch(`${SUPABASE_URL}/auth/v1/signup`, { method: 'POST', headers: { apikey: SUPABASE_KEY, 'Content-Type': 'application/json' }, body: JSON.stringify({ email, password, data: { full_name: fullName, role: 'STUDENT' } }) })
  const data = await response.json()
  if (!response.ok) return NextResponse.json({ error: data.msg ?? data.error_description ?? 'Unable to create account.' }, { status: response.status })
  const result = NextResponse.json({ ok: true, needsConfirmation: !data.access_token })
  if (data.access_token) result.cookies.set('nypunya_access_token', data.access_token, { httpOnly: true, secure: true, sameSite: 'lax', path: '/', maxAge: data.expires_in ?? 3600 })
  return result
}
