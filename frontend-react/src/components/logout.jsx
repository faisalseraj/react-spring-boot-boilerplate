import React from "react";

export const LogoutButton = () => {
  const onLogout = () => {
    localStorage.clear();
  };
  return <button onClick={onLogout}>Logout</button>;
};
