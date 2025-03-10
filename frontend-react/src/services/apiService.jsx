import axios from "axios";

export const apiService = () => {
  // Authentication
  const API_BASE_URL = `${process.env.REACT_APP_API_BASE_URL}/api`;

  // Create axios instances
  const publicAxios = axios.create({
    baseURL: API_BASE_URL,
    headers: { "Content-Type": "application/json" },
  });

  const authAxios = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("authToken")}`,
    },
  });

  // Response interceptor to handle errors
  authAxios.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response?.status === 401) {
        // localStorage.clear();
        // window.location = "/login";
      }
      return Promise.reject(error);
    }
  );

  return {
    login: async (payload) => {
      try {
        const response = await publicAxios.post("/user/login", payload);
        if (!response?.data?.token) {
          throw new Error({ error: { response } });
        }
        localStorage.setItem("authToken", response.data.token);
        localStorage.setItem("user", JSON.stringify(response.data.user));
        return response.data;
      } catch (error) {
        throw new Error(error.response?.data?.message || "Login failed");
      }
    },

    deleteUser: async (userId) => {
      try {
        await authAxios.delete(`/user/delete/${userId}`);
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to delete user"
        );
      }
    },

    createEmployee: async (employeeData) => {
      try {
        const response = await authAxios.post("/user/createEmployee", employeeData);
        if (!response?.data) {
          throw new Error({ error: { response } });
        }
        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to create employee"
        );
      }
    },


    updateEmployee: async (employeeData) => {
      try {
        debugger
        const response = await authAxios.patch("/user/updateEmployee", employeeData);
        if (!response?.data) {
          throw new Error({ error: { response } });
        }
        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to create employee"
        );
      }
    },



    getAllEmployees: async () => {
      try {
        const response = await authAxios.get("/user/getAllEmployees");
        if (!response?.data) {
          throw new Error({ error: { response } });
        }
        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to fetch all employees"
        );
      }
    },

    register: async (userData) => {
      try {
        const response = await publicAxios.post("/user/register", userData);
        console.log(response, "response")
        if (!response?.data?.token) {
          throw new Error({ error: { response } });
        }
        localStorage.setItem("authToken", response.data.token);
        localStorage.setItem("user", JSON.stringify(response.data.user));
        return response.data;
      } catch (error) {
        console.log(error, "eeee", error.data)
       debugger
        throw new Error(error.response?.data?.message || "Registration failed");
      }
    },

    // Jobs
    createJob: async (jobData) => {
      try {
        const response = await authAxios.post("/jobs", jobData);
        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to create job"
        );
      }
    },

    deleteJob: async (jobId) => {
      try {
        const response = await authAxios.delete("/jobs", jobId);
        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to delete job"
        );
      }
    },

    getJobs: async () => {
      try {
        const response = await authAxios.get("/jobs");
        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to fetch jobs"
        );
      }
    },

    updateJob: async (jobId, jobData) => {
      try {
        const response = await authAxios.put(`/jobs/${jobId}`, jobData);
        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to update job"
        );
      }
    },
  };
};
