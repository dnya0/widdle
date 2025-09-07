"use client";

import { useState } from "react";
import QuestionMark from "./question";
import HelpModal from "./how-to-play";

export default function Header({ text }: { text: string }) {
  const [showHelpModal, setShowHelpModal] = useState(false);

  return (
    <div
      style={{
        display: "flex",
        fontSize: "1.3rem",
        padding: 10,
        // width: "20rem",
        alignItems: "center",
        flexDirection: "row",
        justifyContent: "space-between",
      }}
    >
      <div style={{ margin: 10 }}>{text}</div>

      <div style={{ alignItems: "right" }}>
        <QuestionMark onClick={() => setShowHelpModal(true)} />
      </div>

      <HelpModal open={showHelpModal} onClose={() => setShowHelpModal(false)} />
    </div>
  );
}
