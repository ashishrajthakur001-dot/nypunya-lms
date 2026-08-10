'use client'

import { useRouter } from 'next/navigation'
export default function LogoutButton(){const router=useRouter();return <button onClick={async()=>{await fetch('/api/auth/signout',{method:'POST'});router.push('/login');router.refresh()}} style={{padding:'8px 12px'}}>Sign out</button>}
