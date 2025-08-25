import Header from "@/components/header";
import KrClient from "./components/kr-client";
import ToasterClient from "@/components/toast";

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
        <ToasterClient />
        <KrClient></KrClient>
      </div>
    </div>
  );
}
