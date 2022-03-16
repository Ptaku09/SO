export default function Title({ title }: { title: string }) {
  return (
    <div className="bg-white p-5 shadow-md mb-5 rounded-xl">
      <h1 className="2xl:top-5 mx-auto text-5xl text-center text-sky-700 border-b-2 border-b-sky-700 pb-2 lg:px-7 font-['Lobster']">{title}</h1>
    </div>
  );
}
