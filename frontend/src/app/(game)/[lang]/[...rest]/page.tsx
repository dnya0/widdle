import { redirect } from "next/navigation";

export default async function CatchAll({
  params,
}: {
  params: Promise<{ lang: string; rest: string[] }>;
}) {
  const { lang } = await params;
  redirect(`/${lang}`);
}
