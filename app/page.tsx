'use client';

import { useMemo, useState } from 'react';

type Role = 'STUDENT' | 'TRAINER' | 'ADMIN';

type Course = { title: string; description: string; progress: number; status: string; trainer: string };

const courses: Course[] = [
  { title: 'Java & Spring Boot Fundamentals', description: 'REST APIs, Spring Boot, JPA and application security.', progress: 72, status: 'In progress', trainer: 'Ananya Rao' },
  { title: 'React & Next.js Application Development', description: 'Modern React, Next.js, Redux and responsive UI engineering.', progress: 48, status: 'In progress', trainer: 'Rahul Mehta' },
  { title: 'Microservices Architecture', description: 'Service boundaries, API Gateway, discovery and distributed systems.', progress: 100, status: 'Completed', trainer: 'Vikram Singh' },
  { title: 'Cloud & DevOps Foundations', description: 'Docker, Kubernetes, CI/CD and production delivery patterns.', progress: 18, status: 'New', trainer: 'Priya Nair' },
];

const activities = [
  ['Assignment submitted', 'Java & Spring Boot Fundamentals', '2 hours ago'],
  ['Quiz completed', 'Microservices Architecture', 'Yesterday'],
  ['Certificate issued', 'Microservices Architecture', '2 days ago'],
  ['New course published', 'Cloud & DevOps Foundations', '3 days ago'],
];

const roleCopy: Record<Role, { title: string; subtitle: string; stats: string[] }> = {
  STUDENT: { title: 'Good morning, Ashish', subtitle: 'Continue your learning journey and stay on track.', stats: ['4', '2', '78%', '1'] },
  TRAINER: { title: 'Trainer workspace', subtitle: 'Manage courses, learners and assessments from one place.', stats: ['6', '124', '87%', '18'] },
  ADMIN: { title: 'Platform overview', subtitle: 'Monitor the LMS, users, courses and learning activity.', stats: ['32', '486', '76%', '14'] },
};

export default function Home() {
  const [role, setRole] = useState<Role>('STUDENT');
  const [query, setQuery] = useState('');
  const copy = roleCopy[role];
  const filtered = useMemo(() => courses.filter(c => `${c.title} ${c.description}`.toLowerCase().includes(query.toLowerCase())), [query]);

  return (
    <div className="shell">
      <header className="topbar">
        <div className="brand">Nypunya<span>LMS</span></div>
        <nav className="nav"><button className="active">Dashboard</button><button>Courses</button><button>Assignments</button><button>Assessments</button><button>Certificates</button></nav>
        <div className="profile"><div className="name">Ashish Raj</div><div className="avatar">AR</div></div>
      </header>
      <main className="main">
        <section className="hero">
          <div><div className="eyebrow">Learning management system</div><h1 className="title">{copy.title}</h1><div className="muted">{copy.subtitle}</div></div>
          <div className="actions"><div className="role-switch">{(['STUDENT','TRAINER','ADMIN'] as Role[]).map(r => <button key={r} className={role === r ? 'active' : ''} onClick={() => setRole(r)}>{r[0] + r.slice(1).toLowerCase()}</button>)}</div></div>
        </section>
        <section className="stats">
          <div className="stat"><div className="muted">{role === 'STUDENT' ? 'Active courses' : 'Courses'}</div><div className="value">{copy.stats[0]}</div></div>
          <div className="stat"><div className="muted">{role === 'STUDENT' ? 'Pending tasks' : 'Learners'}</div><div className="value">{copy.stats[1]}</div></div>
          <div className="stat"><div className="muted">Completion rate</div><div className="value">{copy.stats[2]}</div></div>
          <div className="stat"><div className="muted">{role === 'STUDENT' ? 'Certificates' : 'Open items'}</div><div className="value">{copy.stats[3]}</div></div>
        </section>
        <section className="grid">
          <div>
            <div className="row" style={{marginBottom:16}}><h2 className="section-title">Course catalog</h2><input value={query} onChange={e => setQuery(e.target.value)} placeholder="Search courses..." style={{padding:'10px 12px',border:'1px solid #dfe3eb',borderRadius:10,outline:'none'}} /></div>
            <div className="courses">{filtered.map(c => <article className="course" key={c.title}><span className="pill">{c.status}</span><h3>{c.title}</h3><p className="muted">{c.description}</p><div className="row"><small>{c.trainer}</small><small>{c.progress}%</small></div><div className="progress"><div style={{width:`${c.progress}%`}} /></div><button className="btn secondary">{c.progress === 100 ? 'Review course' : 'Continue learning'}</button></article>)}</div>
          </div>
          <aside className="card"><h2 className="section-title">Recent activity</h2><div className="list">{activities.map(([a,b,c]) => <div className="activity" key={a+b}><strong>{a}</strong><div className="muted">{b}</div><small className="muted">{c}</small></div>)}</div><button className="btn secondary" style={{width:'100%',marginTop:12}}>View all activity</button></aside>
        </section>
      </main>
    </div>
  );
}
