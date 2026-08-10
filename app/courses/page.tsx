import { supabase } from '@/lib/supabase'

export default async function CoursesPage() {
  const { data: courses } = supabase ? await supabase.from('courses').select('id,title,description,status').eq('status', 'PUBLISHED').order('created_at', { ascending: false }) : { data: [] }

  return (
    <main className="min-h-screen bg-slate-50 px-6 py-10 text-slate-900">
      <div className="mx-auto max-w-6xl">
        <div className="mb-8">
          <p className="text-sm font-semibold text-cyan-700">Learning catalog</p>
          <h1 className="mt-1 text-4xl font-bold">Explore courses</h1>
        </div>
        <div className="grid gap-5 md:grid-cols-2 lg:grid-cols-3">
          {(courses ?? []).map(course => (
            <article key={course.id} className="rounded-2xl border bg-white p-6 shadow-sm">
              <span className="rounded-full bg-emerald-50 px-3 py-1 text-xs font-semibold text-emerald-700">Published</span>
              <h2 className="mt-5 text-xl font-bold">{course.title}</h2>
              <p className="mt-3 text-sm leading-6 text-slate-600">{course.description}</p>
              <button className="mt-6 w-full rounded-xl bg-slate-900 px-4 py-3 text-sm font-semibold text-white">View course</button>
            </article>
          ))}
          {!courses?.length && <p className="text-slate-500">Configure Supabase to load the course catalog.</p>}
        </div>
      </div>
    </main>
  )
}
