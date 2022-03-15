import React from 'react';

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <div className="w-screen min-h-screen flex items-center justify-center flex-col py-10 px-16 2xl:px-0 overflow-x-hidden bg-white bg-hero-pattern">
      {children}
    </div>
  );
}
