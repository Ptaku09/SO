import React, { ChangeEventHandler } from 'react';

type Props = {
  label: string;
  id: string;
  type: string;
  value: number | undefined;
  maxLength: number;
  onChange: ChangeEventHandler<HTMLInputElement>;
};

export default function FormField({ label, id, type, value, maxLength, onChange }: Props) {
  return (
    <>
      <label className="text-[0.75rem] ml-5 w-full" htmlFor={id}>
        {label.toUpperCase()}
      </label>
      <input
        className="w-full py-2 px-3 border-[1px] border-sky-400 bg-white shadow text-md text-black focus:outline-sky-700 focus:animate-pulse rounded-lg mb-6"
        name={id}
        id={id}
        type={type}
        value={value}
        maxLength={maxLength}
        onChange={onChange}
        required
      />
    </>
  );
}
