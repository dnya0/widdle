import EnPage from "@/app/en/en-widdle";
import KrPage from "@/app/kr/kr-widdle";
import SaveWordPage from "../../add/add-form";
import { notFound } from "next/navigation";

export default async function GamePage({
  params,
}: {
  params: Promise<{ lang: string }>;
}) {
  const { lang } = await params;

  if (lang === "add") return <SaveWordPage />;
  else if (lang === "kr") return <KrPage />;
  else if (lang === "en") return <EnPage />;

  notFound();
}
