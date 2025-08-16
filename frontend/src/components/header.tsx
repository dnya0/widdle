import QuestionMark from "./question";

export default function Header() {
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
      <div style={{ margin: 10 }}>위들 - 한국어</div>

      <div style={{ alignItems: "right" }}>
        <QuestionMark></QuestionMark>
      </div>
    </div>
  );
}
