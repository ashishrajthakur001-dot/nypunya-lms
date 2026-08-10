import { NextResponse } from 'next/server';
export async function GET(){return NextResponse.json({service:'nypunya-lms-web',status:'ok',timestamp:new Date().toISOString()});}
