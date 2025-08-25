import toast from "react-hot-toast";

const colors = {
  success: { border: "#49ff43ff", bg: "#b7edb5ff" },
};

export function showSuccess(message: string) {
  toast(message, {
    style: {
      border: `1px solid ${colors.success.border}`,
      background: colors.success.bg,
      color: "#fff",
      padding: "16px",
      fontSize: "1rem",
      fontFamily: "Pretendard-SemiBold",
    },
    icon: null,
  });
}