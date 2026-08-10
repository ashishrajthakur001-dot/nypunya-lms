'use client'

import { FormEvent, useState } from 'react'
import { useRouter } from 'next/navigation'

export default function LoginForm() {
  const router = useRouter()
  const [mode, setMode] = useState<'signin'|'signup'>('signin')
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [message, setMessage] = useState('')
  const [busy, setBusy] = useState(false)

  async function submit(event: FormEvent) {
    event.preventDefault(); setError(''); setMessage(''); setBusy(true)
    try {
      const endpoint = mode === 'signin' ? '/api/auth/signin' : '/api/auth/signup'
      const body = mode === 'signin' ? { email, password } : { email, password, fullName: name }
      const response = await fetch(endpoint, { method: 'POST', headers: {'Content-Type':'application/json'}, body: JSON.stringify(body) })
      const data = await response.json()
      if (!response.ok) throw new Error(data.error ?? 'Request failed')
      if (mode === 'signup' && data.needsConfirmation) setMessage('Account created. Check your email for confirmation, then sign in.')
      else router.push('/')
    } catch (err) { setError(err instanceof Error ? err.message : 'Request failed') } finally { setBusy(false) }
  }

  return <main style={{maxWidth:460,margin:'80px auto',padding:32,fontFamily:'Arial,sans-serif'}}>
    <h1>NypunyaLMS</h1><p style={{color:'#666'}}>{mode === 'signin' ? 'Sign in to your learner account.' : 'Create a learner account.'}</p>
    <form onSubmit={submit} style={{display:'grid',gap:14,marginTop:24}}>
      {mode === 'signup' && <label>Full name<input required value={name} onChange={e=>setName(e.target.value)} style={{display:'block',width:'100%',padding:10,marginTop:5}} /></label>}
      <label>Email<input required type="email" value={email} onChange={e=>setEmail(e.target.value)} style={{display:'block',width:'100%',padding:10,marginTop:5}} /></label>
      <label>Password<input required minLength={8} type="password" value={password} onChange={e=>setPassword(e.target.value)} style={{display:'block',width:'100%',padding:10,marginTop:5}} /></label>
      <button disabled={busy} style={{padding:12}}>{busy ? 'Please wait…' : mode === 'signin' ? 'Sign in' : 'Create account'}</button>
    </form>
    {error && <p style={{color:'crimson'}}>{error}</p>}{message && <p>{message}</p>}
    <button onClick={()=>{setMode(mode==='signin'?'signup':'signin');setError('');setMessage('')}} style={{marginTop:16,border:0,background:'transparent',textDecoration:'underline',cursor:'pointer'}}>{mode === 'signin' ? 'Create a learner account' : 'Already have an account? Sign in'}</button>
  </main>
}
