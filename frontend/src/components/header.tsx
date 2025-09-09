"use client";

import { useState } from "react";
import QuestionMark from "./question";
import HelpModal from "./how-to-play";
import SettingsIcon from "./settings";
import SettingModal from "./setting-modal";

export default function Header({
  text,
  lang,
}: {
  text: string;
  lang: "kr" | "en";
}) {
  const [showHelpModal, setShowHelpModal] = useState(false);
  const [showSettingModal, setShowSettingModal] = useState(false);

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

      <div
        style={{
          alignItems: "right",
          display: "flex",
          flexDirection: "row",
          gap: "10px",
        }}
      >
        <QuestionMark onClick={() => setShowHelpModal(true)} />
        <SettingsIcon onClick={() => setShowSettingModal(true)} />
      </div>

      <HelpModal
        open={showHelpModal}
        lang={lang}
        onClose={() => setShowHelpModal(false)}
      />
      <SettingModal
        open={showSettingModal}
        lang={lang}
        onClose={() => setShowSettingModal(false)}
      />
    </div>
  );
}
