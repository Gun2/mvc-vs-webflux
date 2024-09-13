import React from 'react';
import {Link, Outlet} from "react-router-dom";

function App() {
  return (
    <div className="App">
      <div style={{display: "flex", gap: 5}}>
        <Link to={"/read"}>read api viewer</Link>
        <Link to={"/create"}>create api viewer</Link>
        <Link to={"/simple"}>simple api viewer</Link>
        <Link to={"/complex"}>complex api viewer</Link>
      </div>
      <div>
        <Outlet/>
      </div>
    </div>
  );
}

export default App;
