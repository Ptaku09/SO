import type { NextPage } from 'next';
import Head from 'next/head';
import ExerciseBox from '../components/ExerciseBox';
import Title from '../components/Title';
import { useEffect } from 'react';

const Home: NextPage = () => {
  useEffect(() => {
    document.body.style.overflow = 'unset';
  }, []);

  return (
    <>
      <Head>
        <title>Operating Systems</title>
      </Head>

      <div className="flex items-center justify-center flex-col">
        <Title title="Operating Systems" />
        <div className="w-full h-full mt-10 flex items-center justify-center gap-16 flex-wrap">
          {[
            {
              desc: 'Scheduling access to processor ⏰',
              link: '/exercise1',
            },
            {
              desc: 'Hard drive 💾',
              link: '/exercise2',
            },
            {
              desc: 'Virtual memory 💻',
              link: '/exercise3',
            },
            {
              desc: 'Frames allocation 🖼',
              link: '/exercise4',
            },
            {
              desc: 'CPU load ⚙️',
              link: '/exercise5',
            },
          ].map(({ link, desc }: { link: string; desc: string }, i: number) => {
            return <ExerciseBox key={i} number={i + 1} desc={desc} link={link} />;
          })}
        </div>
      </div>
    </>
  );
};

export default Home;
