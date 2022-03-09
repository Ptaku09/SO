import type { NextPage } from 'next';
import Head from 'next/head';

const Home: NextPage = () => {
  return (
    <>
      <Head>
        <title>Next-app boilerplate</title>
      </Head>

      <div className="w-screen h-screen flex items-center justify-center flex-col">
        <h1 className="absolute top-5 mx-auto text-5xl text-sky-700 border-b-2 border-b-sky-700 pb-5 px-7">Create-next-app boilerplate</h1>
        <p className="text-4xl text-left mb-3">Contains:</p>
        <ul className="list-disc text-lg">
          {[
            {
              name: 'Typescript',
              link: 'https://www.typescriptlang.org/',
            },
            {
              name: 'Tailwind',
              link: 'https://tailwindcss.com/',
            },
            {
              name: 'Prettier',
              link: 'https://prettier.io/',
            },
            {
              name: 'ESLint',
              link: 'https://eslint.org/',
            },
            {
              name: 'Husky',
              link: 'https://typicode.github.io/husky/#/',
            },
          ].map(({ name, link }: { name: string; link: string }) => {
            return (
              <li key={name}>
                <a className="hover:border-b-2 hover:border-b-blue-500 hover:text-blue-500" href={link} target="_blank" rel="noreferrer">
                  {name}
                </a>
              </li>
            );
          })}
        </ul>
        <p className="text-lg mt-3">...and more!</p>
      </div>
    </>
  );
};

export default Home;
