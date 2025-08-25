import { Toaster } from "react-hot-toast";

export default function ToasterClient({ lang }: { lang: string }) {
  return (
    <Toaster
      key={lang}
      toastOptions={{
        className: "",
        style: {
          border: "1px solid #ff8787ff",
          padding: "16px",
          color: "#ffffffff",
          background: "#fca7a7ff",
          fontSize: "1rem",
          fontFamily: "Pretendard-SemiBold",
        },
        icon: null,
      }}
    />
  );
}
