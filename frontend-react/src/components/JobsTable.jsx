import React, { useEffect, useState } from "react";

import { apiService } from "../services/apiService";

function JobsTable({ filters }) {
  const [jobs, setJobs] = useState([]);
  const [filteredJobs, setFilteredJobs] = useState([]); // To store filtered jobs

  const [loading, setLoading] = useState(true); // To manage loading state
  const [error, setError] = useState(""); // To handle error messages

  const deleteJob = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this job?"
    );
    if (confirmed) {
      await apiService().deleteJob(id);
      await fetchJobs();
    }
  };

  const applyFilters = (filters) => {
    const filteredJobs = jobs.filter((job) => {
      const officeMatch =
        filters.office === "" || job.office === filters.office;
      const installerMatch =
        filters.installerName === "" ||
        job.installerName
          .toLowerCase()
          .includes(filters.installerName.toLowerCase());
      const statusMatch =
        filters.status === "" || job.status === filters.status;

      return officeMatch && installerMatch && statusMatch;
    });
    return filteredJobs;
  };

  const updateStatus = async (index, newStatus) => {
    const updatedJobs = [...jobs];
    const oldJob = updatedJobs[index];
    const body = { ...oldJob, status: newStatus };

    try {
      await apiService().updateJob(oldJob.id, body);
      fetchJobs();
    } catch (error) {
      console.error(error);
      setError("Something went wrong");
    }
    setJobs(updatedJobs);
  };

  const updateMaterialOrdered = async (index, newStatus) => {
    const jobToUpdate = { ...jobs[index] };
    jobToUpdate.materialOrderStatus =
      newStatus === "ordered" ? "ordered" : null;
    jobToUpdate.materialArrivalStatus =
      newStatus === "ordered" ? jobToUpdate.materialArrivalStatus : null;
    try {
      await apiService().updateJob(jobToUpdate.id, jobToUpdate);
      fetchJobs();
    } catch (error) {
      console.error(error);
      setError("Something went wrong");
    }
  };

  const updateMaterialArrived = async (index, newStatus) => {
    const jobToUpdate = { ...jobs[index] };

    if (jobToUpdate.materialOrderStatus !== "ordered") {
      setError("Can't set material as arrived if not ordered");
      return;
    } else {
      jobToUpdate.materialArrivalStatus =
        newStatus === "arrived" ? "arrived" : null;

      try {
        await apiService().updateJob(jobToUpdate.id, jobToUpdate);
        fetchJobs();
      } catch (error) {
        console.error(error);
        setError("Something went wrong");
      }
    }
  };

  const handleFileUpload = async (index, event) => {
    const file = event.target.files[0];
    const allowedTypes = ["image/png", "image/jpeg", "image/svg+xml"];

    if (!allowedTypes.includes(file.type)) {
      setError("Only png, jpg and svg images are allowed");
      return;
    }

    try {
      const response = await apiService().uploadJobImage(jobs[index].id, file);
      const updatedJobs = [...jobs];
      updatedJobs[index].image = response.data;
      setJobs(updatedJobs);
    } catch (error) {
      console.error(error);
      setError("Something went wrong");
    }
  };

  const fetchJobs = async () => {
    setLoading(true);
    try {
      const response = await apiService().getJobs();
      setJobs(response);
    } catch (error) {
      console.error("Error during login:", error);
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchJobs();
  }, []);

  useEffect(() => {
    if (
      Object.keys(filters).filter((item) => filters[item] !== "").length === 0
    ) {
      setJobs(jobs);
    } else {
      setFilteredJobs(applyFilters(filters));
    }
  }, [filters]);

  useEffect(() => {
    const timer = setTimeout(() => setError(""), 3000);
    return () => clearTimeout(timer);
  }, [error]);

  const renderTable = (data) => {
    return data.map((job, index) => {
      let statusClass = "";
      if (job.status === "To-Do") statusClass = "status-to-do";
      else if (job.status === "In Progress") statusClass = "status-in-progress";
      else if (job.status === "Completed") statusClass = "status-completed";

      const materialsOrderedClass =
        job.materialOrderStatus === "ordered" ? "status-yes" : "status-no";
      const materialsArrivedClass =
        job.materialArrivalStatus === "arrived" ? "status-yes" : "status-no";

      return (
        <tr key={job.jobName}>
          <td>{job.jobNumber}</td>
          <td>{job.jobName}</td>
          <td>{job.numCabinets}</td>
          <td>{job.numUppers}</td>
          <td>{job.numLowers}</td>
          <td>{job.cabinetMakerName}</td>
          <td>{job.installerName}</td>
          <td>
            {new Date(job.dueDate).toLocaleDateString("en-US", {
              year: "numeric",
              month: "long",
              day: "numeric",
            })}
          </td>
          <td>{job.jobColor}</td>
          <td>{job.office}</td>
          <td>
            <select
              cl
              value={job.status}
              onChange={(e) => updateStatus(index, e.target.value)}
              className={`${statusClass} form-control form-control-sm`}
            >
              <option value="To-Do">To-Do</option>
              <option value="In Progress">In Progress</option>
              <option value="Completed">Completed</option>
            </select>
          </td>
          <td>
            <select
              value={job.materialOrderStatus}
              onChange={(e) => updateMaterialOrdered(index, e.target.value)}
              className={`${materialsOrderedClass} form-control form-control-sm`}
            >
              <option value="">No</option>
              <option value="ordered">Yes</option>
            </select>
          </td>
          <td>
            <select
              value={job.materialsArrivalStatus}
              onChange={(e) => updateMaterialArrived(index, e.target.value)}
              className={`${materialsArrivedClass} form-control form-control-sm`}
            >
              <option value="No">No</option>
              <option value="arrived">Yes</option>
            </select>
          </td>
          <td>
            <input
              type="file"
              className="form-control form-control-sm"
              onChange={(e) => handleFileUpload(index, e)}
            />
            {job.image && (
              <a
                href={`${process.env.REACT_APP_API_BASE_URL}/api/files/${job.image}`}
                target="_blank"
                rel="noopener noreferrer"
              >
                View Photo
              </a>
            )}
          </td>
          <td>
            <button onClick={() => deleteJob(job.id)}>Delete</button>
          </td>
        </tr>
      );
    });
  };

  return loading ? (
    <div className="skeleton-loader">
      {" "}
      <div className="skeleton-row">
        <div className="skeleton-cell"></div>
        <div className="skeleton-cell"></div>
        <div className="skeleton-cell"></div>
      </div>
      <div className="skeleton-row">
        <div className="skeleton-cell"></div>
        <div className="skeleton-cell"></div>
        <div className="skeleton-cell"></div>
      </div>
      <div className="skeleton-row">
        <div className="skeleton-cell"></div>
        <div className="skeleton-cell"></div>
        <div className="skeleton-cell"></div>
      </div>
    </div>
  ) : (
    <div>
      {<div className="error-message">{error}</div>}

      <table>
        <thead>
          <tr>
            <th>Job #</th>
            <th>Job Name</th>
            <th># of Cabinets</th>
            <th># of Uppers</th>
            <th># of Lowers</th>
            <th>Cabinet Maker</th>
            <th>Installer</th>
            <th>Due Date</th>
            <th>Job Color</th>
            <th>Office</th>
            <th>Status</th>
            <th>Materials Ordered</th>
            <th>Materials Arrived</th>
            <th>Photos</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {renderTable(
            Object.keys(filters).filter((item) => filters[item] !== "")
              .length === 0
              ? jobs
              : filteredJobs
          )}
        </tbody>
      </table>
    </div>
  );
}

export default JobsTable;
