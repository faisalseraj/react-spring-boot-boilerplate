import React, { useState } from "react";

import Filter from "../components/Filter";
import Footer from "../components/Footer";
import JobsTable from "../components/JobsTable";
import { Link } from "react-router-dom";
import { NavigationHeader } from "../components/NavigationHeader";

const JobDashboard = () => {
  const [jobs, setJobs] = useState([
    {
      jobNumber: 101,
      jobName: "Kitchen Remodel A",
      numCabinets: 10,
      numUppers: 6,
      numLowers: 4,
      cabinetMaker: "John Doe",
      installer: "Mike Smith",
      dueDate: "2025-02-15",
      jobColor: "White",
      office: "400",
      status: "To-Do",
      materialsOrdered: "Yes",
      materialsArrived: "No",
      photos: "",
    },
    {
      jobNumber: 102,
      jobName: "Kitchen Remodel B",
      numCabinets: 15,
      numUppers: 8,
      numLowers: 7,
      cabinetMaker: "Jane Doe",
      installer: "Sarah Lee",
      dueDate: "2025-03-10",
      jobColor: "Blue",
      office: "402",
      status: "In Progress",
      materialsOrdered: "No",
      materialsArrived: "Yes",
      photos: "",
    },
  ]);

  const [filters, setFilters] = useState({
    office: "",
    installer: "",
    status: "",
  });

  const handleFilterChange = (event) => {
    const { id, value } = event.target;
    setFilters((prev) => ({ ...prev, [id]: value }));
  };

  const clearFilters = () => {
    setFilters({ office: "", installer: "", status: "" });
  };

  const filteredJobs = jobs.filter((job) => {
    return (
      (filters.office === "" || job.office === filters.office) &&
      (filters.installer === "" ||
        job.installer
          .toLowerCase()
          .includes(filters.installer.toLowerCase())) &&
      (filters.status === "" || job.status === filters.status)
    );
  });

  const updateJob = (index, field, value) => {
    const updatedJobs = [...jobs];
    updatedJobs[index][field] = value;
    setJobs(updatedJobs);
  };

  const renderStatusClass = (status) => {
    switch (status) {
      case "To-Do":
        return "status-to-do";
      case "In Progress":
        return "status-in-progress";
      case "Completed":
        return "status-completed";
      default:
        return "";
    }
  };

  return (
    <div>
      {/* Navigation */}
      <NavigationHeader showLogout={true} />

      <header>
        <h1 className="dashboard-headline">Manage Your Jobs Efficiently</h1>
        <h2 className="dashboard-subheadline">
          Explore, filter, and update job statuses seamlessly from one
          centralized dashboard.
        </h2>
      </header>
      <h1 className="dashboard-headline">Kitchen Saver Cabinets</h1>
      <div className="wrapper jobs-container">
        <Filter
          filters={filters}
          handleFilterChange={(e) =>
            setFilters({ ...filters, [e.target.id]: e.target.value })
          }
          clearFilters={clearFilters}
        />

        <JobsTable />
      </div>

      <Footer />
    </div>
  );
};

export default JobDashboard;
