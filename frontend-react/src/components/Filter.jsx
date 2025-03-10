import React from "react";
const Filter = ({ filters, handleFilterChange, clearFilters }) => {
  return (
    <div className="filter-container">
      <form
        id="filterForm"
        className="form-inline bg-light p-3 rounded shadow-sm"
      >
        <div className="form-group mb-2">
          <label htmlFor="filterOffice" className="mr-2 font-weight-bold">
            Filter by Office:
          </label>
          <select
            id="filterOffice"
            className="form-control form-control-sm"
            value={filters.office}
            onChange={handleFilterChange}
          >
            <option value="">All Offices</option>
            <option value="400">400</option>
            <option value="402">402</option>
            <option value="403">403</option>
          </select>
        </div>

        <div className="form-group mx-3 mb-2">
          <label htmlFor="filterInstaller" className="mr-2 font-weight-bold">
            Filter by Installer:
          </label>
          <input
            type="text"
            id="filterInstaller"
            className="form-control form-control-sm"
            value={filters.installer}
            placeholder="Installer Name"
            onChange={handleFilterChange}
          />
        </div>

        <div className="form-group mb-2">
          <label htmlFor="filterStatus" className="mr-2 font-weight-bold">
            Filter by Status:
          </label>
          <select
            className="form-control form-control-sm"
            id="filterStatus"
            value={filters.status}
            onChange={handleFilterChange}
          >
            <option value="">All Statuses</option>
            <option value="To-Do">To-Do</option>
            <option value="In Progress">In Progress</option>
            <option value="Completed">Completed</option>
          </select>
        </div>

        <div className="form-group mb-2">
          <br />
          <button
            type="button"
            className="btn btn-warning btn-sm"
            onClick={clearFilters}
          >
            Clear Filters
          </button>
        </div>
      </form>
    </div>
  );
};

export const OfficeFilter = ({ filters, handleFilterChange, clearFilters }) => {
  return (
    <div className="filter-container">
      <form
        id="filterForm"
        className="form-inline bg-light p-3 rounded shadow-sm"
      >
        <div className="form-group mb-2">
          <label htmlFor="filterOffice" className="mr-2 font-weight-bold">
            Filter by Office:
          </label>
          <select
            id="filterOffice"
            className="form-control form-control-sm"
            value={filters.office}
            onChange={handleFilterChange}
          >
            <option value="">All Offices</option>
            <option value="400">400</option>
            <option value="402">402</option>
            <option value="403">403</option>
          </select>
        </div>

       

        <div className="form-group mb-2">
          <br />
          <button
            type="button"
            className="btn btn-warning btn-sm"
            onClick={clearFilters}
          >
            Clear Filters
          </button>
        </div>
      </form>
    </div>
  );
};

export default Filter