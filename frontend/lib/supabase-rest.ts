import { cookies } from 'next/headers'

const SUPABASE_URL = process.env.NEXT_PUBLIC_SUPABASE_URL ?? 'https://yguicuybvsvjsneftjpb.supabase.co'
const SUPABASE_KEY = process.env.NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY ?? 'sb_publishable_9UE80CKA8CCc8auw_HBQQw_dzgzeanf'

export type Course = { id: string; title: string; description: string | null; status: string; trainer_id: string | null }
export type Assignment = { id: string; course_id: string; title: string; description: string | null; due_at: string | null; max_score: number; status: string }
export type Quiz = { id: string; course_id: string; title: string; duration_minutes: number; max_score: number; status: string }
export type QuizQuestion = { id: string; quiz_id: string; question_text: string; option_a: string; option_b: string; option_c: string; option_d: string; points: number }
export type Enrollment = { id: string; course_id: string; student_id: string; status: string; progress: number }
export type Profile = { id: string; full_name: string; role: string }

function authHeaders() { const token = cookies().get('nypunya_access_token')?.value; return { apikey: SUPABASE_KEY, Authorization: `Bearer ${token ?? SUPABASE_KEY}` } }
async function query<T>(table: string, params = ''): Promise<T[]> { const response = await fetch(`${SUPABASE_URL}/rest/v1/${table}?${params}`, { headers: authHeaders(), cache: 'no-store' }); if (!response.ok) throw new Error(`Supabase ${table} request failed: ${response.status}`); return response.json() }
export async function getCourses() { return query<Course>('courses', 'select=id,title,description,status,trainer_id&order=title.asc') }
export async function getAssignments() { return query<Assignment>('assignments', 'select=id,course_id,title,description,due_at,max_score,status&order=due_at.asc.nullslast') }
export async function getQuizzes() { return query<Quiz>('quizzes', 'select=id,course_id,title,duration_minutes,max_score,status&order=created_at.desc') }
export async function getQuizQuestions(quizId: string) { return query<QuizQuestion>('quiz_questions', `select=id,quiz_id,question_text,option_a,option_b,option_c,option_d,points&quiz_id=eq.${quizId}&order=created_at.asc`) }
export async function getEnrollments(studentId?: string) { const id = studentId ?? (await getProfile())?.id; if (!id) return []; return query<Enrollment>('enrollments', `select=id,course_id,student_id,status,progress&student_id=eq.${id}&order=enrolled_at.desc`) }
export async function getProfile() { const rows = await query<Profile>('profiles', 'select=id,full_name,role&limit=1'); return rows[0] ?? null }
export async function getCourseMap() { const courses = await getCourses(); return new Map(courses.map((course) => [course.id, course])) }
export const DEMO_STUDENT_ID = '00000000-0000-0000-0000-000000000001'
