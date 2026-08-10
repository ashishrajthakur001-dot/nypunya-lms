import Link from 'next/link'
import QuizForm from '../QuizForm'
import { getQuizzes, getQuizQuestions, getCourseMap } from '../../../lib/supabase-rest'

export const dynamic = 'force-dynamic'

export default async function QuizPage({ params }: { params:{id:string} }) {
  const [quizzes,questions,courses]=await Promise.all([getQuizzes(),getQuizQuestions(params.id),getCourseMap()])
  const quiz=quizzes.find(item=>item.id===params.id)
  if(!quiz) return <main style={{padding:40,fontFamily:'Arial,sans-serif'}}><h1>Assessment not found</h1><Link href="/assessments">Back to assessments</Link></main>
  return <main style={{maxWidth:900,margin:'0 auto',padding:40,fontFamily:'Arial,sans-serif'}}><Link href="/assessments">← Assessments</Link><h1>{quiz.title}</h1><p>{courses.get(quiz.course_id)?.title ?? 'Course'} · {quiz.duration_minutes} minutes · {quiz.max_score} points</p>{questions.length?<QuizForm quizId={quiz.id} questions={questions}/>:<p>No questions have been published for this assessment yet.</p>}</main>
}
