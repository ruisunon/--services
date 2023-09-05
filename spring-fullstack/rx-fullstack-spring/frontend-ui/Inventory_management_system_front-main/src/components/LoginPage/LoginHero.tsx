import React, { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
interface ILogin {
  userName: string | undefined;
  password: string | undefined;
}

const LoginHero = () => {
  const navigate = useNavigate();
  const userName = useRef<HTMLInputElement>(null);
  const password = useRef<HTMLInputElement>(null);
  const [loading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState("");
  const [hasError, setHasError] = useState(false);
  const submitHandler = (event: any) => {
    event.preventDefault();
    setIsLoading(true);
    const submittedUsername = userName.current?.value;
    const submittedPassword = password.current?.value;
    console.log(submittedUsername, submittedPassword);
    const sendData: ILogin = {
      userName: submittedUsername,
      password: submittedPassword,
    };
    fetch("http://localhost:3000/auth/login", {
      method: "POST",
      body: JSON.stringify(sendData),
      headers: {
        "Content-Type": "Application/json",
      },
      credentials: "include",
    })
      .then((res: any) => {
        if (res.status === 400) {
          console.log("ERR", res.body);
          throw new Error("Username or password are incorrect.");
        }
        return res.json();
      })
      .then((data) => {
        localStorage.setItem("itm", data.user);
        setIsLoading(false);
        console.log(data);
        setHasError(false);
        navigate("/materials");
      })
      .catch((err) => {
        setIsLoading(false);
        setHasError(true);
        setError(err.message);
      });
  };
  useEffect(() => {
    // console.log("COOKIE LOGGED" + document.cookie.indexOf("logged"));
    if (+document.cookie.indexOf("logged") != -1) navigate("/materials");
  }, []);
  return (
    <div className="relative flex flex-col justify-center min-h-screen overflow-hidden">
      {!loading ? (
        <div className="w-full p-6 m-auto bg-white rounded-md shadow-md lg:max-w-xl">
          <h1 className="text-3xl font-semibold text-center text-purple-700 underline">
            Login
          </h1>
          <form className="mt-6" onSubmit={submitHandler}>
            <div className="mb-2">
              {hasError && <p className="text-center text-red-600">{error}</p>}
              <label
                htmlFor="name"
                className="block text-sm font-semibold text-gray-800"
              >
                Username
              </label>
              <input
                ref={userName}
                type="name"
                className="block w-full px-4 py-2 mt-2 text-purple-700 bg-white border rounded-md focus:border-purple-400 focus:ring-purple-300 focus:outline-none focus:ring focus:ring-opacity-40"
              />
            </div>
            <div className="mb-2">
              <label
                htmlFor="password"
                className="block text-sm font-semibold text-gray-800"
              >
                Password
              </label>
              <input
                ref={password}
                type="password"
                className="block w-full px-4 py-2 mt-2 text-purple-700 bg-white border rounded-md focus:border-purple-400 focus:ring-purple-300 focus:outline-none focus:ring focus:ring-opacity-40"
              />
            </div>
            <a href="#" className="text-xs text-purple-600 hover:underline">
              Forget Password?
            </a>
            <div className="mt-6">
              <button className="w-full px-4 py-2 tracking-wide text-white transition-colors duration-200 transform bg-purple-700 rounded-md hover:bg-purple-600 focus:outline-none focus:bg-purple-600">
                Login
              </button>
            </div>
          </form>

          <p className="mt-8 text-xs font-light text-center text-gray-700">
            {" "}
            Don't have an account?{" "}
            <a href="#" className="font-medium text-purple-600 hover:underline">
              Sign up
            </a>
          </p>
        </div>
      ) : (
        <div className="w-screen h-screen flex justify-center align-middle items-center">
          <div role="status mx-auto">
            <svg
              aria-hidden="true"
              className="w-8 h-8 mr-2 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600"
              viewBox="0 0 100 101"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                fill="currentColor"
              />
              <path
                d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                fill="currentFill"
              />
            </svg>
            <span className="sr-only">Loading...</span>
          </div>
        </div>
      )}
    </div>
  );
};

export default LoginHero;
