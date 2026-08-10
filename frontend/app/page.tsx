export default function Home() {
  const modules = [
    ["Courses", "Browse and continue enrolled courses"],
    ["Assignments", "Track submissions, deadlines and grades"],
    ["Assessments", "Take quizzes and view results"],
    ["Progress", "See completion across your learning"],
  ];
  return <main style={{maxWidth:1100,margin:'0 auto',padding:40,fontFamily:'Arial, sans-serif'}}>
    <header style={{display:'flex',justifyContent:'space-between',alignItems:'center',marginBottom:48}}>
      <div><h1 style={{margin:0}}>NypunyaLMS</h1><p style={{color:'#666'}}>Learning management prototype</p></div>
      <button style={{padding:'10px 16px'}}>Sign in</button>
    </header>
    <section style={{padding:'32px 0'}}><h2>Continue learning</h2><p>Courses, assignments, assessments and learner progress in one place.</p></section>
    <section style={{display:'grid',gridTemplateColumns:'repeat(auto-fit,minmax(220px,1fr))',gap:16}}>
      {modules.map(([title,description]) => <article key={title} style={{border:'1px solid #ddd',borderRadius:12,padding:20}}><h3>{title}</h3><p style={{color:'#666'}}>{description}</p><button style={{padding:'8px 12px'}}>Open</button></article>)}
    </section>
  </main>
}
