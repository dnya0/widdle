type BadgeVariant = "solid" | "outline";

type BadgeProps = {
  label: string;
  variant?: BadgeVariant;
  color?: string;
  className?: string;
};

export function Badge({
  label,
  variant = "solid",
  color = "#1E293B",
  className = "",
}: BadgeProps) {
  const baseStyles =
    "inline-flex items-center rounded-full text-xs font-medium px-3 py-1 transition-colors";
  const styles =
    variant === "solid"
      ? { backgroundColor: color, color: "#0F172A", opacity: 0.95 }
      : { border: `1px solid ${color}`, color };

  return (
    <span className={`${baseStyles} ${className}`} style={styles}>
      {label}
    </span>
  );
}
