import { cookies } from 'next/headers'
import { NextResponse } from 'next/server'

const URL = process.env.NEXT_PUBLIC_SUPABASE_URL ?? 'https://yguicuybvsvjsneftjpb.supabase.co'
const KEY = process.env.NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY ?? 'sb_publishable_9UE80CKA8CCc8auw_HBQQw_dzgzeanf'

export async function POST(request: Request) {
  const token = cookies().get('nypunya_access_token')?.value
  if (!token) return NextResponse.json({ error: 'Sign in required.' }, { status: 401 })
  const { quizId, answers } = await request.json()
  if (!quizId || !Array.isArray(answers)) return NextResponse.json({ error: 'quizId and answers are required.' }, { status: 400 })
  const headers = { apikey: KEY, Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }
  const userResponse = await fetch(`${URL}/auth/v1/user`, { headers })
  if (!userResponse.ok) return NextResponse.json({ error: 'Session expired.' }, { status: 401 })
  const user = await userResponse.json()
  const questionResponse = await fetch(`${URL}/rest/v1/quiz_questions?quiz_id=eq.${quizId}&select=id,correct_option,points`, { headers })
  const questions = await questionResponse.json()
  if (!questionResponse.ok) return NextResponse.json({ error: 'Unable to load quiz.' }, { status: 500 })
  const answerMap = new Map(answers.map((answer: {questionId:string;selectedOption:string}) => [answer.questionId, answer.selectedOption]))
  let score = 0
  for (const question of questions) if (answerMap.get(question.id) === question.correct_option) score += Number(question.points)
  const maxScore = questions.reduce((sum: number, question: {points:number}) => sum + Number(question.points), 0)
  const attemptResponse = await fetch(`${URL}/rest/v1/quiz_attempts`, { method:'POST', headers:{...headers, Prefer:'return=representation'}, body:JSON.stringify({quiz_id:quizId,student_id:user.id,score,max_score:maxScore,status:'SUBMITTED'}) })
  if (!attemptResponse.ok) return NextResponse.json({ error: await attemptResponse.text() }, { status: 500 })
  const attempt = (await attemptResponse.json())[0]
  if (attempt && answers.length) {
    const rows = answers.map((answer: {questionId:string;selectedOption:string}) => {
      const question = questions.find((item: {id:string}) => item.id === answer.questionId)
      const correct = question && answer.selectedOption === question.correct_option
      return { attempt_id: attempt.id, question_id: answer.questionId, selected_option: answer.selectedOption, is_correct: Boolean(correct), points_awarded: correct ? Number(question.points) : 0 }
    })
    await fetch(`${URL}/rest/v1/quiz_answers`, { method:'POST', headers, body:JSON.stringify(rows) })
  }
  return NextResponse.json({ score, maxScore })
}
