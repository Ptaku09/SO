import Head from 'next/head';
import Title from '../components/Title';
import Menu from '../components/Menu';
import useForm from '../hooks/useForm';
import FormField from '../components/FormField';
import React, { useState } from 'react';

const initialState = {
  amountOfObjects: 10,
  rrExecTime: 200,
  alpha: 1,
  beta: 4,
};

type ResultProps = {
  algorithmName: string;
  averageRunningTime: number;
  averageTimeToFirstExecution: number;
  averageWaitingTime: number;
  numberOfSwitchingOperations: number;
};

export default function Exercise1() {
  const { formValues, handleInputChange } = useForm(initialState);
  const [isPending, setIsPending] = useState(false);
  const [results, setResults] = useState([] as ResultProps[]);

  const handleRunSimulation = async (event: React.SyntheticEvent) => {
    event.preventDefault();
    setIsPending(true);

    fetch(
      `http://localhost:8080/exercise1?objects=${formValues.amountOfObjects}&rrExecTime=${formValues.rrExecTime}&alpha=${formValues.alpha}&beta=${formValues.beta}`,
      {
        method: 'POST',
      }
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
        <title>⏰Exercise #1</title>
      </Head>

      <div className="flex h-full w-full items-center justify-start flex-col font-['Lato']">
        <Menu />
        <Title title="Exercise #1" />
        <div className="w-auto h-auto md:w-4/5 md:h-4/5 md:grid md:grid-cols-2 gap-10">
          <div className="flex items-center justify-start flex-col bg-white h-auto w-auto md:w-full md:h-full shadow-lg rounded-xl p-5 mb-8 md:mb-0">
            <p className="text-2xl 2xl:text-4xl border-b-[1px] border-b-black px-4 mb-6 md:mb-0 2xl:pb-2">Submit data</p>
            <form className="h-auto md:h-full flex items-center justify-center flex-col" onSubmit={handleRunSimulation}>
              <FormField
                label="amount of processes"
                id="amountOfObjects"
                type="number"
                value={formValues.amountOfObjects}
                maxLength={6}
                onChange={handleInputChange}
                min={1}
                max={1000000}
              />
              <FormField
                label="rr - single execution time"
                id="rrExecTime"
                type="number"
                value={formValues.rrExecTime}
                maxLength={6}
                onChange={handleInputChange}
                min={0}
                max={10000}
              />
              <FormField
                label="alpha"
                id="alpha"
                type="number"
                value={formValues.alpha}
                maxLength={6}
                onChange={handleInputChange}
                min={1}
                max={1000}
              />
              <FormField label="beta" id="beta" type="number" value={formValues.beta} maxLength={6} onChange={handleInputChange} min={1} max={1000} />
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
          <div className="flex items-center justify-start flex-col bg-white w-auto h-auto md:w-full md:h-full shadow-lg rounded-xl p-5 mb-8 md:mb-0">
            <p className="text-2xl 2xl:text-4xl border-b-[1px] border-b-black px-4 mb-6 md:mb-0 2xl:pb-2">Results</p>
            <div className="flex w-full h-full flex-col items-center justify-center">
              {results.length > 0 ? (
                results.map((result: ResultProps) => {
                  return (
                    <div key={result.algorithmName} className="mb-3 mt-4 md:mt-0">
                      <p className="text-lg text-sky-700 text-center">& {result.algorithmName} &</p>
                      <p>Average time from start to end: {Math.round(result.averageRunningTime * 100) / 100}</p>
                      <p>Average time to start: {Math.round(result.averageTimeToFirstExecution * 100) / 100}</p>
                      <p>Average total waiting time: {Math.round(result.averageWaitingTime * 100) / 100}</p>
                      <p className="border-b-[1px] border-b-black pb-2">Number of switching operations: {result.numberOfSwitchingOperations}</p>
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
