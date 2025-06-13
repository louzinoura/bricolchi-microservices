import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const LoginForm = () => {
  const [formData, setFormData] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleChange = e =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    setError("");
    setSuccess(false);
    
    try {
      const response = await axios.post(
        "http://localhost:8081/api/auth/login", 
        {
          username: formData.username.trim(),
          password: formData.password.trim()
        }
      );
      
      setSuccess(true);
      login(response.data);
      
      setTimeout(() => {
        navigate("/profile");
      }, 1000);
      
    } catch (err) {
      const serverMessage = err.response?.data?.message || err.response?.data?.error;
      setError(serverMessage || "Invalid username or password");
    }
  };

  return (
    <div className="max-w-md mx-auto bg-white p-8 rounded-lg shadow-md mt-10">
      <h2 className="text-2xl font-bold mb-6 text-center text-gray-700">Login</h2>
      
      {success && (
        <div className="mb-4 p-3 bg-green-100 text-green-700 rounded-lg">
          Login successful. Redirecting...
        </div>
      )}
      
      {error && <p className="text-red-500 text-sm mb-4">{error}</p>}
      
      {!success && (
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            name="username"
            placeholder="Username"
            value={formData.username}
            onChange={handleChange}
            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            required
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            required
          />
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition duration-200"
          >
            Login
          </button>
        </form>
      )}
    </div>
  );
};

export default LoginForm;