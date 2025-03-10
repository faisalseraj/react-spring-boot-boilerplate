import './App.css';

import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'; // Ensure BrowserRouter is properly imported

import AdminDashboard from './pages/AdminDashboard';
import CreateEmployee from './pages/CreateEmployee';
import CreateJob from './pages/CreateJob';
import JobDashboard from './pages/JobDashboard';
import LoginForm from './pages/LoginForm';
import Profile from './pages/Profile';
import ProtectedRoute from './HOC/ProtectedRoute';
import SignUpForm from './pages/SignUpForm';
import StrictNoAuthRoute from './HOC/StrictNoAuthRoute';
import UpdateProfile from './pages/UpdateProfile';

function App() {
  return (
    <Router> {/* Ensure Router is wrapping the entire app */}

      <div className="App">
        <Routes> {/* Routes wraps the Route definitions */}
          <Route element={<StrictNoAuthRoute />}>
            <Route path="/" element={<LoginForm />} />
            <Route path="/login" element={<LoginForm />} />
            <Route path="/signup" element={<SignUpForm />} />
          </Route>
          <Route element={<ProtectedRoute />}>
            <Route path="/jobs" element={<JobDashboard />} />
            <Route path="/update-profile" element={<UpdateProfile />} />
            <Route path="/profile" element={<Profile />} />
          </Route>
          <Route element={<ProtectedRoute role="ADMIN" />}>
            <Route path="/create-employee" element={<CreateEmployee />} />
            <Route path="/createjob" element={<CreateJob />} />
            <Route path="/admin" element={<AdminDashboard />} />
          </Route>
        </Routes>

      </div>

    </Router>
  );
}

export default App;
