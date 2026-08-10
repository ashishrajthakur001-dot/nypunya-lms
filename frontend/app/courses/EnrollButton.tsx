'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'

export default function EnrollButton({ courseId }: { courseId: string }) {
  const router = useRouter(); const [busy,setBusy] = useState(false); const [message,setMessage] = useState('')
  async function enroll() {
    setBusy(true); setMessage('')
    const response = await fetch('/api/enrollments', { method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify({courseId}) })
    const data = await response.json()
    if (!response.ok) setMessage(data.error ?? 'Unable to enroll.')
    else { setMessage('Enrolled successfully.'); router.refresh() }
    setBusy(false)
  }
  return <div><button onClick={enroll} disabled={busy} style={{padding:'10px 16px'}}>{busy ? 'Enrolling…' : 'Enroll in course'}</button>{message && <p style={{color:message.includes('successfully')?'green':'crimson'}}>{message}</p>}</div>
}
