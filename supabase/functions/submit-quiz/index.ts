import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { createClient } from "jsr:@supabase/supabase-js@2";

const supabaseUrl = Deno.env.get("SUPABASE_URL")!;
const secretKeys = JSON.parse(Deno.env.get("SUPABASE_SECRET_KEYS") ?? "{}");
const secretKey = secretKeys.default ?? Deno.env.get("SUPABASE_SERVICE_ROLE_KEY");

Deno.serve(async (req) => {
  if (req.method !== "POST") return Response.json({ error: "Method not allowed" }, { status: 405 });
  const auth = req.headers.get("Authorization");
  if (!auth) return Response.json({ error: "Authentication required" }, { status: 401 });
  const userClient = createClient(supabaseUrl, Deno.env.get("SUPABASE_ANON_KEY")!, { global: { headers: { Authorization: auth } } });
  const { data: { user }, error: userError } = await userClient.auth.getUser();
  if (userError || !user) return Response.json({ error: "Invalid session" }, { status: 401 });
  const admin = createClient(supabaseUrl, secretKey);
  const { quizId, answers } = await req.json();
  if (!quizId || !Array.isArray(answers)) return Response.json({ error: "quizId and answers are required" }, { status: 400 });
  const { data: quiz, error: quizError } = await admin.from("quizzes").select("id,max_score,status").eq("id", quizId).eq("status", "PUBLISHED").maybeSingle();
  if (quizError || !quiz) return Response.json({ error: "Quiz not found or not published" }, { status: 404 });
  const { data: questions, error: questionError } = await admin.from("quiz_questions").select("id,correct_option,points").eq("quiz_id", quizId);
  if (questionError) return Response.json({ error: "Unable to load quiz" }, { status: 500 });
  const answerMap = new Map(answers.map((a: { questionId: string; selectedOption: string }) => [a.questionId, a.selectedOption]));
  const rawMax = (questions ?? []).reduce((sum, q) => sum + Number(q.points), 0);
  const rawScore = (questions ?? []).reduce((sum, q) => sum + (answerMap.get(q.id) === q.correct_option ? Number(q.points) : 0), 0);
  const score = rawMax ? Math.round((rawScore / rawMax) * Number(quiz.max_score)) : 0;
  const { data: attempt, error: attemptError } = await admin.from("quiz_attempts").insert({ quiz_id: quizId, student_id: user.id, score, max_score: quiz.max_score, status: "SUBMITTED" }).select("id").single();
  if (attemptError) return Response.json({ error: "Unable to record attempt" }, { status: 500 });
  const questionMap = new Map((questions ?? []).map(q => [q.id, q]));
  const rows = answers.flatMap((a: { questionId: string; selectedOption: string }) => { const q = questionMap.get(a.questionId); if (!q) return []; const correct = a.selectedOption === q.correct_option; return [{ attempt_id: attempt.id, question_id: q.id, selected_option: a.selectedOption, is_correct: correct, points_awarded: correct ? Number(q.points) : 0 }]; });
  if (rows.length) await admin.from("quiz_answers").insert(rows);
  return Response.json({ attemptId: attempt.id, score, maxScore: quiz.max_score });
});
