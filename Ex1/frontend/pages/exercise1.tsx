import Head from 'next/head';
import Title from '../components/Title';
import Menu from '../components/Menu';

export default function Exercise1() {
  return (
    <>
      <Head>
        <title>⏰Exercise #1</title>
      </Head>

      <Menu />
      <div className="flex h-full w-full items-center justify-start flex-col">
        <Title title="Exercise #1" />
        <div className="w-4/5 h-4/5 grid grid-cols-2 gap-10">
          <div className="flex items-center justify-start flex-col bg-white w-full h-full shadow-lg rounded-xl p-5">
            <p>Submit data</p>
          </div>
          <div className="flex items-center justify-start flex-col bg-white w-full h-full shadow-lg rounded-xl">#2`</div>
        </div>
      </div>
    </>
  );
}
