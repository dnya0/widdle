import Header from "@/components/header";
import GameClient from "@/components/game-client";

export default function KrPage() {
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
        <Header text={"위들 - 한국어"}></Header>

        <GameClient
          key="kr"
          lang="kr"
          rows={6}
          cols={6}
        />
      </div>
    </div>
  );
}
