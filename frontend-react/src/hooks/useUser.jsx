import { useEffect, useState } from "react";

export const useUser = () => {
  const [user, setUser] = useState();
  useEffect(() => {
    const user = localStorage.getItem("user");
    if (user ) {
      setUser(JSON.parse(user));
    }
  }, []);
  return {
    user,
  };
};
