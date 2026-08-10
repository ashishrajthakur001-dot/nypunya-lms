'use client'

import { FormEvent, useState } from 'react'
import { useRouter } from 'next/navigation'
import { supabase } from '@/lib/supabase'

export default function LoginPage() {
  const router = useRouter()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [busy, setBusy] = useState(false)

  async function submit(event: FormEvent) {
    event.preventDefault()
    setError('')
    setBusy(true)
    if (!supabase) {
      setError('Supabase is not configured. Add NEXT_PUBLIC_SUPABASE_URL and NEXT_PUBLIC_SUPABASE_ANON_KEY.')
      setBusy(false)
      return
    }
    const { error: authError } = await supabase.auth.signInWithPassword({ email, password })
    setBusy(false)
    if (authError) {
      setError(authError.message)
      return
    }
    router.push('/')
    router.refresh()
  }

  return (
    <main className="min-h-screen grid place-items-center bg-slate-950 px-6 text-white">
      <form onSubmit={submit} className="w-full max-w-md rounded-3xl border border-white/10 bg-white/5 p-8 shadow-2xl">
        <p className="text-sm font-semibold text-cyan-300">NypunyaLMS</p>
        <h1 className="mt-2 text-3xl font-bold">Welcome back</h1>
        <p className="mt-2 text-sm text-slate-400">Sign in to continue your learning journey.</p>
        <div className="mt-8 space-y-4">
          <input aria-label="Email" type="email" required value={email} onChange={e => setEmail(e.target.value)} placeholder="Email" className="w-full rounded-xl bg-white/10 px-4 py-3 outline-none ring-cyan-400 focus:ring-2" />
          <input aria-label="Password" type="password" required value={password} onChange={e => setPassword(e.target.value)} placeholder="Password" className="w-full rounded-xl bg-white/10 px-4 py-3 outline-none ring-cyan-400 focus:ring-2" />
          {error && <p className="rounded-xl bg-red-400/10 p-3 text-sm text-red-300">{error}</p>}
          <button disabled={busy} className="w-full rounded-xl bg-cyan-400 px-4 py-3 font-bold text-slate-950 disabled:opacity-50">{busy ? 'Signing in…' : 'Sign in'}</button>
        </div>
      </form>
    </main>
  )
}
