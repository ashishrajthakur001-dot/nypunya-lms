'use client'

import { useState } from 'react'

export default function SubmissionForm({ assignmentId }:{assignmentId:string}){
  const [content,setContent]=useState(''); const [message,setMessage]=useState(''); const [busy,setBusy]=useState(false)
  async function submit(){setBusy(true);setMessage('');const response=await fetch('/api/assignments/submit',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({assignmentId,content})});const data=await response.json();setBusy(false);if(!response.ok){setMessage(data.error??'Unable to submit.');return}setContent('');setMessage('Submission recorded successfully.')}
  return <div style={{marginTop:12}}><textarea value={content} onChange={e=>setContent(e.target.value)} placeholder="Paste your solution or submission notes here…" rows={5} style={{width:'100%',padding:10}}/><button onClick={submit} disabled={busy||!content.trim()} style={{marginTop:8,padding:'8px 12px'}}>{busy?'Submitting…':'Submit work'}</button>{message&&<p style={{color:message.includes('successfully')?'green':'crimson'}}>{message}</p>}</div>
}
