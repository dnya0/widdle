import React from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  ResponsiveContainer,
  LabelList,
} from "recharts";

export default function WinDistributionChart({
  winDistribution,
}: {
  winDistribution: number[];
}) {
  const data = winDistribution.map((v, i) => ({
    name: String(i + 1),
    value: v,
    display: v + 3,
  }));

  return (
    <ResponsiveContainer width="100%" height={200}>
      <BarChart layout="vertical" data={data} style={{ pointerEvents: "none" }}>
        <XAxis type="number" hide />
        <YAxis
          type="category"
          dataKey="name"
          axisLine={false}
          tickLine={false}
          tick={{ fontSize: 14, fontFamily: "Pretendard-Medium" }}
        />
        <Bar dataKey="display" fill="#2563eb" radius={[10, 0, 0, 10]}>
          <LabelList
            dataKey="value"
            position="inside"
            fill="#fff"
            style={{ fontSize: 14, fontFamily: "Pretendard-Medium" }}
          />
        </Bar>
      </BarChart>
    </ResponsiveContainer>
  );
}
