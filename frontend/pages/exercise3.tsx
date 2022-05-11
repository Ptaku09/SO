import useForm from '../hooks/useForm';
import React, { useState } from 'react';
import Head from 'next/head';
import Menu from '../components/Menu';
import Title from '../components/Title';
import FormField from '../components/FormField';

const initialState = {
  physicalMemorySize: 8,
  virtualMemorySize: 40,
  sequenceSize: 10000,
};

type ResultProps = {
  algorithmName: string;
  errors: number;
};

export default function Exercise3() {
  const { formValues, handleInputChange } = useForm(initialState);
  const [isPending, setIsPending] = useState(false);
  const [results, setResults] = useState([] as ResultProps[]);

  const handleRunSimulation = async (event: React.SyntheticEvent) => {
    event.preventDefault();
    setIsPending(true);

    fetch(
      `http://localhost:8080/exercise3?physicalSize=${formValues.physicalMemorySize}&virtualSize=${formValues.virtualMemorySize}&sequenceSize=${formValues.sequenceSize}`
    )
      .then((response) => response.json())
      .then((data) => {
        setResults(data);
        setIsPending(false);
      });
  };

  return (
    <>
      <Head>
        <title>💻Exercise #3</title>
      </Head>

      <div className="flex h-full w-full items-center justify-start flex-col font-['Lato']">
        <Menu />
        <Title title="Exercise #3" />
        <div className="w-auto h-auto md:w-4/5 md:h-4/5 md:grid md:grid-cols-2 gap-10">
          <div className="flex items-center justify-start flex-col bg-white h-auto w-auto md:w-full md:h-full shadow-lg rounded-xl p-5 mb-8 md:mb-0">
            <p className="text-2xl 2xl:text-4xl border-b-[1px] border-b-black px-4 mb-6 md:mb-0 2xl:pb-2">Submit data</p>
            <form className="h-auto md:h-full flex items-center justify-center flex-col" onSubmit={handleRunSimulation}>
              <FormField
                label="physical Memory Size"
                id="physicalMemorySize"
                type="number"
                value={formValues.physicalMemorySize}
                maxLength={6}
                onChange={handleInputChange}
                min={1}
                max={formValues.physicalMemorySize}
              />
              <FormField
                label="virtual Memory Size"
                id="virtualMemorySize"
                type="number"
                value={formValues.virtualMemorySize}
                maxLength={6}
                onChange={handleInputChange}
                min={1}
                max={10000}
              />
              <FormField
                label="test sequence Size"
                id="sequenceSize"
                type="number"
                value={formValues.sequenceSize}
                maxLength={7}
                onChange={handleInputChange}
                min={0}
                max={1000000}
              />
              <button className="flex items-center justify-center shadow-xl rounded-lg px-5 py-2 bg-gradient-to-r from-white to-sky-400 bg-200% bg-left hover:bg-right transition-all duration-[400ms] ease-linear">
                {!isPending ? (
                  'Send request'
                ) : (
                  <>
                    <svg className="animate-spin rounded-full border-4 border-white border-t-sky-700 h-5 w-5 mr-3" viewBox="0 0 24 24" />
                    PROCESSING...
                  </>
                )}
              </button>
            </form>
          </div>
          <div className="flex items-center justify-start flex-col bg-white w-auto h-auto md:w-full md:h-full shadow-lg rounded-xl p-5 mb-8 md:mb-0 overflow-hidden">
            <p className="text-2xl 2xl:text-4xl border-b-[1px] border-b-black px-4 mb-4 2xl:pb-2">Results</p>
            <div className="flex w-full h-full flex-col items-center justify-start overflow-y-auto scroll-smooth">
              {results.length > 0 ? (
                results.map((result: ResultProps) => {
                  console.log(result);
                  return (
                    <div key={result.algorithmName} className="mb-3 mt-4">
                      <p className="text-lg text-sky-700 text-center">& {result.algorithmName} &</p>
                      <p className="border-b-[1px] border-b-black w-48 pb-2 text-center">Errors: {result.errors}</p>
                    </div>
                  );
                })
              ) : (
                <div>
                  {!isPending ? (
                    <p className="text-xl text-center">To see results you have to run simulation first!</p>
                  ) : (
                    <svg className="animate-spin rounded-full border-4 border-white border-t-sky-700 h-5 w-5 mr-3" viewBox="0 0 24 24" />
                  )}
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
