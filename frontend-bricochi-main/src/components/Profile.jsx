import { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const Profile = () => {
  const { token, user, logout } = useAuth();
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  // Configure axios defaults
  axios.defaults.timeout = 10000; // 10 second timeout

  useEffect(() => {
    console.log("🔐 Auth token from context:", token);
    console.log("👤 User from context:", user);
    console.log("🗂 Token in localStorage:", localStorage.getItem("token"));
    console.log("🗂 User in localStorage:", localStorage.getItem("user"));

    if (!token) {
      console.warn("⚠️ No token found! Redirecting to login...");
      navigate("/login");
      return;
    }

    const fetchProfile = async () => {
      try {
        setLoading(true);
        setError("");
        
        console.log("📡 Sending request to /api/users/profile with token...");
        
        // First, test if the backend is reachable
        const baseURL = "http://localhost:8081";
        console.log("🔍 Testing backend connectivity...");
        
        const response = await axios.get(`${baseURL}/api/users/profile`, {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          timeout: 10000,
        });

        console.log("✅ Profile fetched successfully:", response.data);

        if (!response.data || typeof response.data !== "object") {
          console.error("❌ Unexpected response format!", response.data);
          setError("Unexpected response format from server.");
          return;
        }

        setProfile(response.data);
      } catch (err) {
        console.error("❌ Error during profile fetch:", err);
        
        // More detailed error handling
        if (err.code === 'ERR_NETWORK') {
          setError("Cannot connect to server. Please check if the backend is running on port 8081.");
        } else if (err.code === 'ECONNABORTED') {
          setError("Request timeout. Server is taking too long to respond.");
        } else if (err.response) {
          // Server responded with error status
          const status = err.response.status;
          const message = err.response.data?.message || `Server error: ${status}`;
          
          if (status === 401) {
            setError("Authentication failed. Please login again.");
            logout();
            navigate("/login");
          } else if (status === 403) {
            setError("Access forbidden. You don't have permission to view this profile.");
          } else {
            setError(message);
          }
        } else {
          setError("An unexpected error occurred. Please try again.");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, [token, logout, navigate]);

  // Loading state
  if (loading) {
    return (
      <div className="text-center mt-10">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500 mx-auto"></div>
        <p className="mt-4 text-gray-600">⏳ Loading profile...</p>
      </div>
    );
  }

  // Error state
  if (error) {
    return (
      <div className="max-w-2xl mx-auto bg-red-50 border border-red-200 rounded-lg p-8 mt-10">
        <div className="text-red-600 text-center">
          <h3 className="text-lg font-semibold mb-2">❌ Error Loading Profile</h3>
          <p className="mb-4">{error}</p>
          <button 
            onClick={() => window.location.reload()} 
            className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded transition-colors"
          >
            Try Again
          </button>
        </div>
      </div>
    );
  }

  // Success state
  return (
    <div className="max-w-2xl mx-auto bg-white shadow-md rounded-lg p-8 mt-10">
      <h2 className="text-2xl font-bold mb-4 text-center text-gray-800">User Profile</h2>
      <div className="space-y-4 text-gray-700">
        <p><strong>Username:</strong> {profile.username}</p>
        <p><strong>Email:</strong> {profile.email}</p>
        <p><strong>Role:</strong> {profile.role}</p>
        {profile.speciality && <p><strong>Speciality:</strong> {profile.speciality}</p>}
        {profile.location && <p><strong>Location:</strong> {profile.location}</p>}
        {profile.bio && <p><strong>Bio:</strong> {profile.bio}</p>}
        {profile.hourlyRate && <p><strong>Hourly Rate:</strong> {profile.hourlyRate} MAD/h</p>}
        <p><strong>Created At:</strong> {new Date(profile.createdAt).toLocaleString()}</p>
      </div>
    </div>
  );
};

export default Profile;