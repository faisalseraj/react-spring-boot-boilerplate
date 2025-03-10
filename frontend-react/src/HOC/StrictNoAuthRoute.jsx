import { Navigate, Outlet } from "react-router-dom";

import { useUser } from "../hooks/useUser";

const StrictNoAuthRoute = () => {
  const { user } = useUser();

  if (user?.role === "ADMIN") {
    return <Navigate to="/admin" />;
  } else if (user?.role === "CABINET_MAKER" || user?.role === "INSTALLER") {
    return <Navigate to="/jobs" />;
  }

  return <Outlet />;
};

export default StrictNoAuthRoute;
