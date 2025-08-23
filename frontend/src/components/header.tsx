import QuestionMark from "./question";

export default function Header({ text }: { text: string }) {
  return (
    <div
      style={{
        display: "flex",
        fontSize: "1.3rem",
        padding: 10,
        // width: "20rem",
        alignItems: "center",
        flexDirection: "row",
        justifyContent: "space-between",
      }}
    >
      <div style={{ margin: 10 }}>{text}</div>

      <div style={{ alignItems: "right" }}>
        <QuestionMark></QuestionMark>
      </div>
    </div>
  );
}
