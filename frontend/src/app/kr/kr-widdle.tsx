"use client";

import Header from "@/components/header";
import GameClient from "@/components/game-client";
import { useState } from "react";

export default function KrPage() {
  const [isGameOver, setIsGameOver] = useState(false);
  const [word, setWord] = useState<string>("");
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        gap: 8,
        padding: 10,
      }}
    >
      <div
        style={{
          flexDirection: "row",
          justifyContent: "center",
        }}
      >
        <Header
          text={"위들 - 한국어"}
          lang="kr"
          isGameOver={isGameOver}
          word={word}
        />
        <GameClient
          key="kr"
          lang="kr"
          rows={6}
          cols={6}
          word={word}
          setWord={setWord}
          isGameOver={isGameOver}
          setIsGameOver={setIsGameOver}
        />
      </div>
    </div>
  );
}
