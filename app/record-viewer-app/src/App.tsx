import React from 'react';
import {Link, Outlet} from "react-router-dom";

function App() {
  return (
    <div className="App">
      <div style={{display: "flex", gap: 5}}>
        <Link to={"/read"}>read api viewer</Link>
        <Link to={"/create"}>create api viewer</Link>
        <Link to={"/simple"}>simple api viewer</Link>
        <Link to={"/heavy"}>heavy api viewer</Link>
        <Link to={"/block"}>block api viewer</Link>
        <Link to={"/r2dbc-vs-jdbc"}>r2dbc vs jdbc api viewer</Link>
        <Link to={"/too-many-context-switches"}>too many context switches</Link>
      </div>
      <div style={{width: "100%", height: "80vh"}}>
        <Outlet/>
      </div>
    </div>
  );
}

export default App;
