import React, { useState } from 'react';

export type InitialStateTypes = {
  //exercise 1
  amountOfObjects?: number;
  rrExecTime?: number;
  alpha?: number;
  beta?: number;

  //exercise 2
  amountOfProcesses?: number;
  hardDriveCapacity?: number;
  initialHeadPosition?: number;
  realTimeChance?: number;

  //exercise3
  physicalMemorySize?: number;
  virtualMemorySize?: number;
  sequenceSize?: number;

  //exercise4
  processes?: number;
  processLength?: number;
  frames?: number;
  scuffleTime?: number;
  scufflePercent?: number;
};

const useForm = (initialState: InitialStateTypes) => {
  const [formValues, setFormValues] = useState(initialState);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const target = event.target as HTMLInputElement;

    setFormValues({
      ...formValues,
      [target.name]: target.value,
    });
  };

  return {
    formValues,
    setFormValues,
    handleInputChange,
  };
};

export default useForm;
