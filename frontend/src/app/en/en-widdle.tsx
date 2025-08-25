import Header from "@/components/header";
import EnClient from "./components/en-client";
import ToasterClient from "@/components/toast";

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
        <ToasterClient />
        <EnClient></EnClient>
      </div>
    </div>
  );
}
