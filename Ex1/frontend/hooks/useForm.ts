import React, { useState } from 'react';

export type InitialStateTypes = {
  amountOfObjects?: number;
  rrExecTime?: number;
  alpha?: number;
  beta?: number;
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
