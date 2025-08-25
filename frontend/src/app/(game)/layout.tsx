import ToasterByPath from "@/components/toaster-by-path";
import { ReactNode } from "react";

export default function GameLayout({ children }: { children: ReactNode }) {
  return (
    <div>
      {children}
      <ToasterByPath />
    </div>
  );
}
