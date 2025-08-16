import Header from "@/components/header";
import KrClient from "./components/kr-client";

export default function kr() {
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
        <Header></Header>
        <KrClient></KrClient>
      </div>
    </div>
  );
}
