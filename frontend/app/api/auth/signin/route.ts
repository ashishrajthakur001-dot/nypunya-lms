import { NextResponse } from 'next/server'

const SUPABASE_URL = process.env.NEXT_PUBLIC_SUPABASE_URL ?? 'https://yguicuybvsvjsneftjpb.supabase.co'
const SUPABASE_KEY = process.env.NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY ?? 'sb_publishable_9UE80CKA8CCc8auw_HBQQw_dzgzeanf'

export async function POST(request: Request) {
  const { email, password } = await request.json()
  if (!email || !password) return NextResponse.json({ error: 'Email and password are required.' }, { status: 400 })
  const response = await fetch(`${SUPABASE_URL}/auth/v1/token?grant_type=password`, { method: 'POST', headers: { apikey: SUPABASE_KEY, 'Content-Type': 'application/json' }, body: JSON.stringify({ email, password }) })
  const data = await response.json()
  if (!response.ok) return NextResponse.json({ error: data.error_description ?? data.msg ?? 'Unable to sign in.' }, { status: response.status })
  const result = NextResponse.json({ ok: true })
  result.cookies.set('nypunya_access_token', data.access_token, { httpOnly: true, secure: true, sameSite: 'lax', path: '/', maxAge: data.expires_in ?? 3600 })
  return result
}
