import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {ReadApiViewer} from "./components/ReadApiViewer";
import {CreateApiViewer} from "./components/CreateApiViewer";
import {SimpleApiViewer} from "./components/SimpleApiViewer";
import {ComplexApiViewer} from "./components/ComplexApiViewer";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
const router = createBrowserRouter([
    {
        path: "/",
        children: [
            {
                path: "/",
                element: <ReadApiViewer/>
            },
            {
                path: "/read",
                element: <ReadApiViewer/>
            },
            {
                path: "/create",
                element: <CreateApiViewer/>
            },
            {
                path: "/simple",
                element: <SimpleApiViewer/>
            },
            {
                path: "/complex",
                element: <ComplexApiViewer/>
            },

        ],
        element: <App/>
    }
]);
root.render(
  <React.StrictMode>
      <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
