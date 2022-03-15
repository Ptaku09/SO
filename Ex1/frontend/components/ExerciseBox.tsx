import WaveBackground from './WaveBackground';
import Link from 'next/link';

type Props = {
  number: number;
  desc: string;
  link: string;
};

export default function ExerciseBox({ number, desc, link }: Props) {
  return (
    <div className="relative w-80 h-72 bg-sky-400 rounded-xl flex items-center justify-start flex-col p-5 shadow-xl overflow-hidden">
      <p className="relative z-[2] text-4xl text-white font-['Lato']">{`Exercise #${number}`}</p>
      <p className="relative z-[2] mt-3 font-['Lato']">{desc}</p>
      <WaveBackground />
      <Link href={link}>
        <a className="absolute bottom-5 border-[1px] border-black px-5 py-2 bg-gradient-to-r from-white to-sky-400 bg-200% bg-left hover:bg-right transition-all duration-[400ms] ease-linear">
          Check it out!
        </a>
      </Link>
    </div>
  );
}
