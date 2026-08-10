import { NextResponse } from 'next/server'

const URL=process.env.NEXT_PUBLIC_SUPABASE_URL ?? 'https://yguicuybvsvjsneftjpb.supabase.co'
const KEY=process.env.NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY ?? 'sb_publishable_9UE80CKA8CCc8auw_HBQQw_dzgzeanf'

export async function GET(){
  const response=await fetch(`${URL}/rest/v1/courses?select=id&limit=1`,{headers:{apikey:KEY,Authorization:`Bearer ${KEY}`},cache:'no-store'})
  return NextResponse.json({service:'nypunya-lms-web',status:response.ok?'ok':'degraded',database:response.ok?'ok':'error',timestamp:new Date().toISOString()},{status:response.ok?200:503})
}
