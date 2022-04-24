import Link from 'next/link';
import React, { useEffect, useState } from 'react';

export default function Menu() {
  const [isOpen, setOpen] = useState(false);
  const [selected, setSelected] = useState('exercise1');

  useEffect(() => {
    setSelected(sessionStorage.getItem('current-exercise') || 'exercise1');
    document.body.style.overflow = 'unset';
  }, []);

  const handleMenuOpen = () => {
    setOpen((prevState) => !prevState);
    document.body.style.overflow === 'hidden' ? (document.body.style.overflow = 'unset') : (document.body.style.overflow = 'hidden');
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelected(event.target.id);
    sessionStorage.setItem('current-exercise', event.target.id);
  };

  return (
    <>
      <button
        className={`fixed left-5 flex flex-col h-6 justify-around transition duration-300 ${isOpen ? 'z-30 -translate-y-5' : 'translate-y-0'}`}
        onClick={handleMenuOpen}
      >
        <div className={`w-6 h-[0.18rem] transition origin-[1px] duration-300 ${isOpen ? 'bg-white rotate-45' : 'bg-black rotate-0'}`} />
        <div
          className={`w-6 h-[0.18rem] transition origin-[1px] duration-300 ${
            isOpen ? 'bg-white translate-x-5 opacity-0' : 'bg-black translate-x-0 opacity-100'
          }`}
        />
        <div className={`w-6 h-[0.18rem] transition origin-[1px] duration-300 ${isOpen ? 'bg-white rotate-[-45deg]' : 'bg-black rotate-0'}`} />
      </button>
      <div
        className={`fixed top-0 left-0 z-20 w-full md:w-1/3 h-full flex flex-col xs:flex-row items-center justify-around bg-white dark:bg-black dark:text-white text-black bg-opacity-100 transition duration-300 ${
          isOpen ? 'translate-x-0' : '-translate-x-full md:translate-x-[-34vw]'
        }`}
      >
        <div className="flex flex-col items-center font-['Lato']">
          <h1 className="text-5xl mb-14 font-['Lobster']">
            Browse,
            <br /> projects!
          </h1>
          <form className="text-xl">
            <div className="flex flex-col items-center justify-between gap-8">
              {['exercise1', 'exercise2', 'exercise3', 'exercise4', 'exercise5'].map((id: string) => {
                return (
                  <div key={id}>
                    <input
                      type="radio"
                      name="side-menu"
                      id={id}
                      checked={selected === id}
                      onChange={handleInputChange}
                      onClick={handleMenuOpen}
                      className="peer"
                      hidden
                    />
                    <label
                      htmlFor={id}
                      className="inline relative overflow-hidden after:absolute after:-z-[1] after:right-0 after:w-0 after:-bottom-[5px] after:bg-white after:h-[2px] after:transition-all after:duration-300 after:ease-out hover:after:left-0 hover:after:right-auto hover:after:w-full cursor-pointer peer-checked:after:w-full peer-checked:after:bg-sky-400 peer-checked:text-sky-400"
                    >
                      <Link href={`/${id !== 'exercise1' && id !== 'exercise2' ? '#' : id}`}>
                        <a onClick={() => sessionStorage.setItem('current-exercise', `exercise${id.slice(-1)}`)}>Exercise #{id.slice(-1)}</a>
                      </Link>
                    </label>
                  </div>
                );
              })}
              <div>
                <Link href="/">
                  <a
                    onClick={handleMenuOpen}
                    className="inline relative overflow-hidden border-[1px] p-2 after:absolute after:-z-[1] after:right-0 after:w-0 after:-bottom-[5px] after:bg-white after:h-[2px] after:transition-all after:duration-300 after:ease-out hover:after:left-0 hover:after:right-auto hover:after:w-full cursor-pointer peer-checked:after:w-full"
                  >
                    Main Menu
                  </a>
                </Link>
              </div>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
