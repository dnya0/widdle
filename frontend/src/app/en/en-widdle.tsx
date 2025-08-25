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
        <Header text="Widdle" />
        <GameClient
          key="en"
          lang="en"
          rows={5}
          cols={5}
        />
      </div>
    </div>
  );
}
