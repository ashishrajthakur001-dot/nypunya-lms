import { cookies } from 'next/headers'
import { NextResponse } from 'next/server'

const URL = process.env.NEXT_PUBLIC_SUPABASE_URL ?? 'https://yguicuybvsvjsneftjpb.supabase.co'
const KEY = process.env.NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY ?? 'sb_publishable_9UE80CKA8CCc8auw_HBQQw_dzgzeanf'

export async function POST(request: Request) {
  const token = cookies().get('nypunya_access_token')?.value
  if (!token) return NextResponse.json({ error: 'Sign in required.' }, { status: 401 })
  const body = await request.json()
  const response = await fetch(`${URL}/functions/v1/submit-quiz`, { method: 'POST', headers: { apikey: KEY, Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }, body: JSON.stringify(body) })
  const data = await response.json()
  return NextResponse.json(data, { status: response.status })
}
