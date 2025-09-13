"use client";

import { useState } from "react";
import QuestionMark from "./question";
import HelpModal from "./how-to-play";
import SettingsIcon from "./settings";
import SettingModal from "./setting-modal";
import ResultModal from "./result-modal";
import StatisticsIcon from "./statistics";

export default function Header({
  text,
  lang,
  isGameOver,
  word,
}: {
  text: string;
  lang: "kr" | "en";
  isGameOver: boolean;
  word: string;
}) {
  const [showHelpModal, setShowHelpModal] = useState(false);
  const [showSettingModal, setShowSettingModal] = useState(false);
  const [showResultModal, setShowResultModal] = useState(false);

  return (
    <div
      style={{
        display: "flex",
        fontSize: "1.3rem",
        padding: 10,
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
        <StatisticsIcon onClick={() => setShowResultModal(true)} />
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
      <ResultModal
        showModal={showResultModal}
        lang={lang}
        isGameOver={isGameOver}
        answer={word}
        onClose={() => setShowResultModal(false)}
      />
    </div>
  );
}
