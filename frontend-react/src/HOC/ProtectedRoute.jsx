import { Navigate, Outlet } from "react-router-dom";
import { useEffect, useState } from "react";

import { useUser } from "../hooks/useUser";

const ProtectedRoute = ({ role }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const { user } = useUser();
  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (token) {
      setIsAuthenticated(true);
    }
    setTimeout(() => {
      setIsLoading(false);
    }, 2000);
  }, []);
  
  if (isLoading || !user?.role) {
    return <div>Loading...</div>;
  }

  if (role && user?.role !== role) {
    return <Navigate to="/" />;
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
