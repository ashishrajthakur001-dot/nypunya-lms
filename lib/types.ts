export type Role = 'STUDENT' | 'TRAINER' | 'ADMIN'

export type Course = {
  id: string
  title: string
  description: string | null
  status: 'DRAFT' | 'PUBLISHED' | 'ARCHIVED'
  trainer_id: string | null
}

export type Enrollment = {
  id: string
  course_id: string
  student_id: string
  status: 'ACTIVE' | 'COMPLETED' | 'CANCELLED'
  progress: number
}
