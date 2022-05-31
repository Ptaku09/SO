import useForm from '../hooks/useForm';
import React, { useState } from 'react';
import Head from 'next/head';
import Menu from '../components/Menu';
import Title from '../components/Title';
import FormField from '../components/FormField';

const initialState = {
  processes: 10,
  processLength: 1000,
  frames: 60,
  scuffleTime: 50,
  scufflePercent: 50,
};

type ResultProps = {
  algorithmName: string;
  totalErrors: number;
  errorsPerProcess: number[];
  scuffleErrors: number;
  stoppedProcesses: number;
};

export default function Exercise4() {
  const { formValues, handleInputChange } = useForm(initialState);
  const [isPending, setIsPending] = useState(false);
  const [results, setResults] = useState([] as ResultProps[]);

  const handleRunSimulation = async (event: React.SyntheticEvent) => {
    event.preventDefault();
    setIsPending(true);

    const url = new URL('http://localhost:8080/exercise4');
    url.searchParams.set('processes', formValues.processes!.toString());
    url.searchParams.set('processLength', formValues.processLength!.toString());
    url.searchParams.set('frames', formValues.frames!.toString());
    url.searchParams.set('scuffleTime', formValues.scuffleTime!.toString());
    url.searchParams.set('scufflePercent', formValues.scufflePercent!.toString());

    fetch(`${url}`)
      .then((response) => response.json())
      .then((data) => {
        setResults(data);
        setIsPending(false);
      });
  };

  return (
    <>
      <Head>
        <title>🖼Exercise #4</title>
      </Head>

      <div className="flex h-full w-full items-center justify-start flex-col font-['Lato']">
        <Menu />
        <Title title="Exercise #4" />
        <div className="w-auto h-auto md:w-4/5 md:h-4/5 md:grid md:grid-cols-2 gap-10">
          <div className="flex items-center justify-start flex-col bg-white h-auto w-auto md:w-full md:h-full shadow-lg rounded-xl p-5 mb-8 md:mb-0">
            <p className="text-2xl 2xl:text-4xl border-b-[1px] border-b-black px-4 mb-6 md:mb-0 2xl:pb-2">Submit data</p>
            <form className="h-auto md:h-full flex items-center justify-center flex-col overflow-y-auto" onSubmit={handleRunSimulation}>
              <FormField
                label="amount of processes"
                id="amountOfProcesses"
                type="number"
                value={formValues.processes}
                maxLength={6}
                onChange={handleInputChange}
                min={1}
                max={50}
              />
              <FormField
                label="length of process"
                id="lengthOfProcess"
                type="number"
                value={formValues.processLength}
                maxLength={6}
                onChange={handleInputChange}
                min={1}
                max={10000}
              />
              <FormField
                label="amount of frames"
                id="amountOfFrames"
                type="number"
                value={formValues.frames}
                maxLength={7}
                onChange={handleInputChange}
                min={1}
                max={500}
              />
              <FormField
                label="scuffle time window"
                id="scuffleTimeWindow"
                type="number"
                value={formValues.scuffleTime}
                maxLength={7}
                onChange={handleInputChange}
                min={1}
                max={1000}
              />
              <FormField
                label="scuffle detection percentage [%]"
                id="scuffleDetectionPercentage"
                type="number"
                value={formValues.scufflePercent}
                maxLength={7}
                onChange={handleInputChange}
                min={1}
                max={100}
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
                    <div key={result.algorithmName} className="mb-3 mt-4 w-60 flex items-center flex-col">
                      <p className="text-lg text-sky-700 text-center">& {result.algorithmName} &</p>
                      <p className="w-48 text-center">Total errors: {result.totalErrors}</p>
                      <p className="w-48 text-center">
                        Errors per process: <br />[
                        {result.errorsPerProcess.map((err: number, i: number) => (i !== result.errorsPerProcess.length - 1 ? `${err}, ` : err))}]
                      </p>
                      <p className="w-48 text-center">Scuffle errors: {result.scuffleErrors}</p>
                      <p className="border-b-[1px] border-b-black w-48 pb-2 text-center">Stopped processes: {result.stoppedProcesses}</p>
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
