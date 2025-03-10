import axios from "axios";

export const apiService = () => {
  const API_BASE_URL = `${process.env.REACT_APP_API_BASE_URL}/api`;

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
        const response = await authAxios.patch("/user/updateEmployee", employeeData);
        if (!response?.data) {
          throw new Error({ error: { response } });
        }
        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to update employee"
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
        if (!response?.data?.token) {
          throw new Error({ error: { response } });
        }
        localStorage.setItem("authToken", response.data.token);
        localStorage.setItem("user", JSON.stringify(response.data.user));
        return response.data;
      } catch (error) {
        throw new Error(error.response?.data?.message || "Registration failed");
      }
    },

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
        const response = await authAxios.delete(`/jobs/${jobId}`);
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

    uploadJobImage: async (jobId, imageFile) => {
      try {
        const formData = new FormData();
        formData.append('file', imageFile);

        const response = await authAxios.post(`/upload`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });

        if (response.data) {
          await apiService().updateJobImage(jobId, response.data);
        }

        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to upload job image"
        );
      }
    },

    updateJobImage: async (jobId, imageUrl) => {
      try {
        const response = await authAxios.post(`/jobs/${jobId}/uploadImage`, {imageUrl});

        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to upload job image"
        );
      }
    },

    getJobImage:  (imageUrl) => {
      try {
        return `${API_BASE_URL}/files/1741617713456_a_unique_logo_for_a_platform_called(1).jpeg`
      } catch (error) {
        return ""
      }
    },

    getSelf: async () => {
      try {
        const response = await authAxios.get("/user/getSelf");
        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to fetch user data"
        );
      }
    },

    updateProfile: async (userData) => {
      try {
        const response = await authAxios.patch("/user/updateProfile", userData);
        if (!response?.data) {
          throw new Error({ error: { response } });
        }
        localStorage.setItem("user", JSON.stringify(response.data));
        return response.data;
      } catch (error) {
        throw new Error(
          error.response?.data?.message || "Failed to update user profile"
        );
      }
    },
  };
};
