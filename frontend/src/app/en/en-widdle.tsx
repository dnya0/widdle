"use client";

import Header from "@/components/header";
import GameClient from "@/components/game-client";
import { useState } from "react";

export default function EnPage() {
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
        <Header text="Widdle" lang="en" isGameOver={isGameOver} word={word} />
        <GameClient
          key="en"
          lang="en"
          rows={6}
          cols={5}
          word={word}
          setWord={setWord}
          isGameOver={isGameOver}
          setIsGameOver={setIsGameOver}
        />
      </div>
    </div>
  );
}
