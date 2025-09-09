import Header from "@/components/header";
import GameClient from "@/components/game-client";

export default function EnPage() {
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
        <Header text="Widdle" lang="en" />
        <GameClient key="en" lang="en" rows={6} cols={5} />
      </div>
    </div>
  );
}
