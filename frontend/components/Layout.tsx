import React from 'react';

export default function Layout({ children }: { children: React.ReactNode }) {
  return <div className="w-screen h-screen overflow-y-auto py-10 px-16 2xl:px-0 bg-white bg-hero-pattern">{children}</div>;
}
