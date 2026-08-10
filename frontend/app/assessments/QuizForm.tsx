'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'

type Question = { id:string; question_text:string; option_a:string; option_b:string; option_c:string; option_d:string }
export default function QuizForm({ quizId, questions }: { quizId:string; questions:Question[] }) {
  const [answers,setAnswers]=useState<Record<string,string>>({}); const [result,setResult]=useState<string>(''); const [busy,setBusy]=useState(false); const router=useRouter()
  async function submit(){
    setBusy(true); const response=await fetch('/api/quiz/submit',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({quizId,answers:Object.entries(answers).map(([questionId,selectedOption])=>({questionId,selectedOption}))})}); const data=await response.json(); setBusy(false); if(!response.ok){setResult(data.error??'Unable to submit quiz.');return} setResult(`Score: ${data.score}/${data.maxScore}`); router.refresh()
  }
  return <div style={{display:'grid',gap:18,marginTop:24}}>{questions.map((q,i)=><fieldset key={q.id} style={{border:'1px solid #ddd',borderRadius:10,padding:18}}><legend><strong>{i+1}. {q.question_text}</strong></legend>{[['A',q.option_a],['B',q.option_b],['C',q.option_c],['D',q.option_d]].map(([value,label])=><label key={value} style={{display:'block',padding:6}}><input type="radio" name={q.id} checked={answers[q.id]===value} onChange={()=>setAnswers({...answers,[q.id]:value})}/> {value}. {label}</label>)}</fieldset>)}<button disabled={busy||!questions.length} onClick={submit} style={{padding:12}}>{busy?'Submitting…':'Submit quiz'}</button>{result&&<p><strong>{result}</strong></p>}</div>
}
