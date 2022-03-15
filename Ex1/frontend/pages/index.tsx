import type { NextPage } from 'next';
import Head from 'next/head';
import ExerciseBox from '../components/ExerciseBox';

const Home: NextPage = () => {
  return (
    <>
      <Head>
        <title>Operating Systems</title>
      </Head>

      <div className="bg-white p-5 shadow-md mb-5 rounded-xl">
        <h1 className="relative 2xl:absolute 2xl:top-5 mx-auto text-5xl text-center text-sky-700 border-b-2 border-b-sky-700 pb-2 lg:px-7 font-['Lobster']">
          Operating Systems
        </h1>
      </div>
      <div className="w-full h-full mt-10 flex items-center justify-center gap-16 flex-wrap">
        {[
          {
            desc: '⏰ Scheduling access to processor ⏰',
            link: '/exercise1',
          },
          {
            desc: 'unknown',
            link: '#',
          },
          {
            desc: 'unknown',
            link: '#',
          },
          {
            desc: 'unknown',
            link: '#',
          },
          {
            desc: 'unknown',
            link: '#',
          },
        ].map(({ link, desc }: { link: string; desc: string }, i: number) => {
          return <ExerciseBox key={i} number={i + 1} desc={desc} link={link} />;
        })}
      </div>
    </>
  );
};

export default Home;
