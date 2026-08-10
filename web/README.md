# NypunyaLMS Web Prototype

Next.js prototype connected to the supplied Supabase project. The course catalog reads published courses from Supabase.

## Run

```bash
npm install
cp .env.example .env.local
npm run dev
```

Set `NEXT_PUBLIC_SUPABASE_URL` and `NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY` in `.env.local`.