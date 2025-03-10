import { Link, Navigate, useNavigate } from "react-router-dom";
import React, { use, useEffect, useState } from "react";

import { useUser } from "../hooks/useUser";

export const NavigationHeader = ({ showLogout }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const { user } = useUser();
  const navigate = useNavigate();
  const onLogout = () => {
    localStorage.clear();
    return navigate("/login");
  };

  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  return (
    <nav>
      <div className="nav-container">
        <h1 className="brand-name">Kitchen Saver</h1>

        {isLoggedIn && user?.role === "ADMIN" ? (
          <>
            <Link to="/admin" className="ml-2">
              <button> Home</button>
            </Link>
            <Link to="/createjob">
              {" "}
              <button> Create Job</button>
            </Link>

            <Link to="/create-employee">
              {" "}
              <button> Create Employee</button>
            </Link>
            <Link to="/update-profile">
              {" "}
              <button> Update Profile</button>
            </Link>
          </>
        ) : null}
      </div>
      {showLogout ? <button onClick={onLogout}>Logout</button> : null}
    </nav>
  );
};
