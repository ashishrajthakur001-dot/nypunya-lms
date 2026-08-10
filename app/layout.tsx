import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: 'NypunyaLMS',
  description: 'Role-based Learning Management System prototype',
};

export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  return <html lang="en"><body>{children}</body></html>;
}