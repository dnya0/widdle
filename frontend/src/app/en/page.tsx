import Header from "@/components/header";
import EnClient from "./components/en-client";

export default function Home() {
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
        <EnClient></EnClient>
      </div>
    </div>
  );
}
