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

export default function Exercise1() {
  const { formValues, handleInputChange } = useForm(initialState);
  const [isPending, setIsPending] = useState(false);

  const handleRunSimulation = async (event: React.SyntheticEvent) => {
    event.preventDefault();
    setIsPending(true);

    fetch('http://localhost:8080/exercise1', {
      method: 'POST',
      body: JSON.stringify({
        objects: formValues.amountOfObjects,
        rrExecTime: formValues.rrExecTime,
        alpha: formValues.alpha,
        beta: formValues.beta,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setIsPending(false);
      });
  };

  return (
    <>
      <Head>
        <title>⏰Exercise #1</title>
      </Head>

      <Menu />
      <div className="flex h-full w-full items-center justify-start flex-col font-['Lato']">
        <Title title="Exercise #1" />
        <div className="w-4/5 h-4/5 grid grid-cols-2 gap-10">
          <div className="flex items-center justify-start flex-col bg-white w-full h-full shadow-lg rounded-xl p-5">
            <p className="text-2xl 2xl:text-4xl border-b-[1px] border-b-black px-4 2xl:pb-2">Submit data</p>
            <form className="h-full flex items-center justify-center flex-col" onSubmit={handleRunSimulation}>
              <FormField
                label="amount of processes"
                id="amountOfObjects"
                type="number"
                value={formValues.amountOfObjects}
                maxLength={6}
                onChange={handleInputChange}
              />
              <FormField
                label="rr - single execution time"
                id="rrExecTime"
                type="number"
                value={formValues.rrExecTime}
                maxLength={6}
                onChange={handleInputChange}
              />
              <FormField label="alpha" id="alpha" type="number" value={formValues.alpha} maxLength={6} onChange={handleInputChange} />
              <FormField label="beta" id="beta" type="number" value={formValues.beta} maxLength={6} onChange={handleInputChange} />
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
          <div className="flex items-center justify-start flex-col bg-white w-full h-full shadow-lg rounded-xl p-5">
            <p className="text-2xl 2xl:text-4xl border-b-[1px] border-b-black px-4 2xl:pb-2">Results</p>
          </div>
        </div>
      </div>
    </>
  );
}
