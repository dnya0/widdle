"use client";
import { usePathname } from "next/navigation";
import ToasterClient from "@/components/toast";

export default function ToasterByPath() {
  const seg1 = usePathname().split("/")[1];
  const lang = seg1 === "en" ? "en" : "kr";
  return <ToasterClient lang={lang} />;
}
