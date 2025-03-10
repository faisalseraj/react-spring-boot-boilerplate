import React, { useState } from "react";

import Footer from "../components/Footer";
import { Link } from 'react-router-dom';
import { NavigationHeader } from "../components/NavigationHeader";
import { apiService } from "../services/apiService";

// Initial job data
const initialJobs = [
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
];

function CreateJob() {
    const [jobs, setJobs] = useState(initialJobs);
    const [filters, setFilters] = useState({
        office: "",
        installer: "",
        status: "",
    });
    const [form, setForm] = useState({
        jobNumber: "",
        jobName: "",
        numCabinets: "",
        numUppers: "",
        numLowers: "",
        cabinetMaker: "",
        installer: "",
        dueDate: "",
        jobColor: "",
        office: "",
        status: "",
    });
    const [message, setMessage] = useState("");

    const handleFormChange = (e) => {
        const { id, value } = e.target;
        setForm((prevForm) => ({
            ...prevForm,
            [id]: value,
        }));
    };

    const addJob = async () => {
        if (
            Object.values(form).some((value) => value === "") ||
            form.jobNumber === ""
        ) {
            setMessage("All fields are required.");
            return;
        }
        try {
            await apiService().createJob(form);
            setMessage("Job Added Successfully!");
            setJobs([...jobs, form]);
            setForm({
                jobNumber: "",
                jobName: "",
                numCabinets: "",
                numUppers: "",
                numLowers: "",
                cabinetMaker: "",
                installer: "",
                dueDate: "",
                jobColor: "",
                office: "",
                status: "",
            });
        } catch (error) {
            setMessage(error.message);
        }
    };

    const applyFilters = () => {
        return jobs.filter((job) => {
            return (
                (filters.office === "" || job.office === filters.office) &&
                (filters.installer === "" ||
                    job.installer.toLowerCase().includes(filters.installer.toLowerCase())) &&
                (filters.status === "" || job.status === filters.status)
            );
        });
    };

    const deleteJob = (jobNumber) => {
        if (window.confirm("Are you sure you want to delete this job?")) {
            setJobs(jobs.filter((job) => job.jobNumber !== jobNumber));
        }
    };

    return (
        <div>
            <NavigationHeader showLogout />


            <div className="wrapper">
                <header>
                    <h1>Create Jobs - Organize Your Projects Seamlessly</h1>
                    <h2>Input job details, track progress, and manage tasks all in one place</h2>
                </header>
                <div className='wrapper'>
                    <div className="container login-container">
                        {/* Job Form */}
                        <div className='p-2'>
                            <form id="jobForm">


                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-4 col-form-label col-form-label-sm">Job #</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="number"
                                            id="jobNumber"
                                            value={form.jobNumber}
                                            onChange={handleFormChange}
                                            className="form-control form-control-sm mb-1"

                                        />
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-4 col-form-label col-form-label-sm">Job Name</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="text"
                                            id="jobName"
                                            value={form.jobName}
                                            onChange={handleFormChange}
                                            className="form-control form-control-sm mb-1"

                                        />
                                    </div>
                                </div>


                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-4 col-form-label col-form-label-sm"># of Cabinets</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="number"
                                            id="numCabinets"
                                            value={form.numCabinets}
                                            onChange={handleFormChange}
                                            className="form-control form-control-sm mb-1"

                                        />
                                    </div>
                                </div>


                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-4 col-form-label col-form-label-sm"># of Uppers</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="number"
                                            id="numUppers"
                                            value={form.numUppers}
                                            onChange={handleFormChange}
                                            className="form-control form-control-sm mb-1"

                                        />
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-4 col-form-label col-form-label-sm"># of Lowers</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="number"
                                            id="numLowers"
                                            className="form-control form-control-sm mb-1"

                                            value={form.numLowers}
                                            onChange={handleFormChange}
                                        />
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-4 col-form-label col-form-label-sm">Cabinet Maker</label>
                                    <div className="col-sm-8">
                                        <input
                                            type="text"
                                            id="cabinetMaker"
                                            className="form-control form-control-sm mb-1"
                                            value={form.cabinetMaker}
                                            onChange={handleFormChange}
                                        />                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-3 col-form-label col-form-label-sm">Installer</label>
                                    <div className="col-sm-9">
                                        <input
                                            type="text"
                                            id="installer"
                                            className="form-control form-control-sm mb-1"
                                            value={form.installer}
                                            onChange={handleFormChange}
                                        />
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-3 col-form-label col-form-label-sm">Due Date</label>
                                    <div className="col-sm-9">
                                        <input
                                            type="date"
                                            id="dueDate"
                                            className="form-control form-control-sm mb-1"
                                            value={form.dueDate}
                                            onChange={handleFormChange}
                                        />
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-3 col-form-label col-form-label-sm">Job Color</label>
                                    <div className="col-sm-9">
                                        <input
                                            type="text"
                                            id="jobColor"
                                            className="form-control form-control-sm mb-1"
                                            value={form.jobColor}
                                            onChange={handleFormChange}
                                        />    </div>
                                </div>


                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-2 col-form-label col-form-label-sm">Office</label>
                                    <div className="col-sm-10">
                                        <select
                                            id="office"
                                            className="form-control form-control-sm mb-1"
                                            value={form.office}
                                            onChange={handleFormChange}
                                        >
                                            <option value="">Select Office</option>
                                            <option value="400">400</option>
                                            <option value="402">402</option>
                                            <option value="403">403</option>
                                        </select>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label for="colFormLabelLg" className="col-sm-2 col-form-label col-form-label-sm">Status</label>
                                    <div className="col-sm-10">
                                        <select
                                            id="status"
                                            className="form-control form-control-sm mb-1"
                                            value={form.status}
                                            onChange={handleFormChange}
                                        >
                                            <option value="">Select Status</option>
                                            <option value="To-Do">To-Do</option>
                                            <option value="In Progress">In Progress</option>
                                            <option value="Completed">Completed</option>
                                        </select>                                    </div>
                                </div>



                                <button type="button" onClick={addJob}>
                                    Add Job
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                {/* Feedback Message */}
                <p>{message}</p>

                {/* Job List */}
                
            </div>

            {/* Footer */}
           <Footer />
        </div>
    );
}

export default CreateJob;
