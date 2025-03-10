import React, { useEffect, useState } from "react";

import { apiService } from "../services/apiService";

function JobsTable({filters, setFilters}) {
  const [jobs, setJobs] = useState([]);

  const [loading, setLoading] = useState(false); // To manage loading state
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

  const applyFilters = () => {
    return jobs.filter((job) => {
      return (
        (filters.office === "" || job.office === filters.office) &&
        (filters.installer === "" ||
          job.installer
            .toLowerCase()
            .includes(filters.installer.toLowerCase())) &&
        (filters.status === "" || job.status === filters.status)
      );
    });
  };

  const updateStatus = (index, newStatus) => {
    const updatedJobs = [...jobs];
    updatedJobs[index].status = newStatus;
    setJobs(updatedJobs);
  };

  const updateMaterialOrdered = (index, newStatus) => {
    const updatedJobs = [...jobs];
    updatedJobs[index].materialsOrdered = newStatus;
    setJobs(updatedJobs);
  };

  const updateMaterialArrived = (index, newStatus) => {
    const updatedJobs = [...jobs];
    updatedJobs[index].materialsArrived = newStatus;
    setJobs(updatedJobs);
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
    if (!loading && jobs?.length === 0) {
      fetchJobs();
    }
  }, [loading]);

  const renderTable = (data) => {
    return jobs.map((job, index) => {
      let statusClass = "";
      if (job.status === "To-Do") statusClass = "status-to-do";
      else if (job.status === "In Progress") statusClass = "status-in-progress";
      else if (job.status === "Completed") statusClass = "status-completed";

      const materialsOrderedClass =
        job.materialsOrdered === "Yes" ? "status-yes" : "status-no";
      const materialsArrivedClass =
        job.materialsArrived === "Yes" ? "status-yes" : "status-no";

      return (
        <tr key={job.jobNumber}>
          <td>{job.jobNumber}</td>
          <td>{job.jobName}</td>
          <td>{job.numCabinets}</td>
          <td>{job.numUppers}</td>
          <td>{job.numLowers}</td>
          <td>{job.cabinetMaker}</td>
          <td>{job.installer}</td>
          <td>{job.dueDate}</td>
          <td>{job.jobColor}</td>
          <td>{job.office}</td>
          <td>
            <select
              value={job.status}
              onChange={(e) => updateStatus(index, e.target.value)}
              className={statusClass}
            >
              <option value="To-Do">To-Do</option>
              <option value="In Progress">In Progress</option>
              <option value="Completed">Completed</option>
            </select>
          </td>
          <td>
            <select
              value={job.materialsOrdered}
              onChange={(e) => updateMaterialOrdered(index, e.target.value)}
              className={materialsOrderedClass}
            >
              <option value="Yes">Yes</option>
              <option value="No">No</option>
            </select>
          </td>
          <td>
            <select
              value={job.materialsArrived}
              onChange={(e) => updateMaterialArrived(index, e.target.value)}
              className={materialsArrivedClass}
            >
              <option value="Yes">Yes</option>
              <option value="No">No</option>
            </select>
          </td>
          <td>
            <input type="file" />
            {job.photos && (
              <a href={job.photos} target="_blank" rel="noopener noreferrer">
                View Photo
              </a>
            )}
          </td>
          <td>
            <button onClick={() => deleteJob(data.id)}>Delete</button>
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
    <>
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
        <tbody>{renderTable(jobs)}</tbody>
      </table>
      {error && <div className="error">{error}</div>}
    </>
  );
}

export default JobsTable;
