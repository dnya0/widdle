import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import Script from "next/script";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Widdle",
  description: "Word Quiz Game! Guess a new word that gets updated every day!",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased`}
        style={{
          display: "flex",
          flexDirection: "column",
          // justifyContent:"center",
          minHeight: "100vh",
          padding: "20px",
        }}
      >
        {children}

        <Script
          src="https://www.googletagmanager.com/gtag/js?id=G-NLPHXYT3Z1"
          strategy="beforeInteractive"
        />
        <Script id="google-analytics-config" strategy="beforeInteractive">
          {`
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', 'G-NLPHXYT3Z1');
  `}
        </Script>
      </body>
    </html>
  );
}
