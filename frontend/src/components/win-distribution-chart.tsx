import React from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  ResponsiveContainer,
  LabelList,
} from "recharts";

const MIN_BAR_LENGTH = 0.1;

export default function WinDistributionChart({
  winDistribution,
}: {
  winDistribution: number[];
}) {
  console.log(winDistribution);
  const data = winDistribution.map((v, i) => ({
    name: String(i + 1),
    value: v,
    display: v > 0 ? v : MIN_BAR_LENGTH,
  }));

  const maxScore = Math.max(0, ...winDistribution);

  return (
    <ResponsiveContainer width="100%" height={200}>
      <BarChart
        layout="vertical"
        data={data}
        style={{ pointerEvents: "none" }}
        margin={{ top: 20, right: 20, bottom: 20, left: 20 }}
      >
        <XAxis type="number" hide domain={maxScore === 0 ? [0, 1] : [0, 'auto']}/>
        <YAxis
          type="category"
          dataKey="name"
          width={10}
          axisLine={false}
          tickLine={false}
          tick={{ fontSize: 14, fontFamily: "Pretendard-Medium" }}
        />
        <Bar dataKey="display" fill="#2563eb" radius={[10, 0, 0, 10]}>
          <LabelList
            dataKey="value"
            position="center"
            fill="#fff"
            style={{ fontSize: 14, fontFamily: "Pretendard-Medium" }}
          />
        </Bar>
      </BarChart>
    </ResponsiveContainer>
  );
}
