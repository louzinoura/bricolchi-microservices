import { useState } from "react";
import { register } from "../api/auth";
import { useNavigate } from "react-router-dom";

export default function RegisterForm() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: "",
    role: "CLIENT",
    speciality: "",
    location: "",
    bio: "",
    hourlyRate: ""
  });

  const [message, setMessage] = useState({ text: "", isError: false });

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = { ...form };

    // Convert hourlyRate to number if role is PRESTATAIRE
    if (payload.role === "PRESTATAIRE") {
      payload.hourlyRate = parseFloat(payload.hourlyRate);
    }

    try {
      const msg = await register(payload);
      setMessage({ text: msg, isError: false });

      setTimeout(() => {
        navigate("/login");
      }, 1500);
    } catch (err) {
      setMessage({ text: err.message || "Registration failed", isError: true });
    }
  };

  return (
    <div className="max-w-md mx-auto bg-white p-8 rounded-lg shadow-md mt-10">
      <h2 className="text-2xl font-bold mb-6 text-center text-gray-700">Create an Account</h2>

      {message.text && (
        <div className={`mb-4 p-3 rounded-lg ${message.isError ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700'}`}>
          {message.text}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Username */}
        <div>
          <label className="block text-gray-700 text-sm font-bold mb-2">Username</label>
          <input
            value={form.username}
            onChange={(e) => setForm({ ...form, username: e.target.value })}
            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            placeholder="Enter your username"
            required
          />
        </div>

        {/* Email */}
        <div>
          <label className="block text-gray-700 text-sm font-bold mb-2">Email</label>
          <input
            type="email"
            value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })}
            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            placeholder="Enter your email"
            required
          />
        </div>

        {/* Password */}
        <div>
          <label className="block text-gray-700 text-sm font-bold mb-2">Password</label>
          <input
            type="password"
            value={form.password}
            onChange={(e) => setForm({ ...form, password: e.target.value })}
            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            placeholder="Create a password"
            required
          />
        </div>

        {/* Role */}
        <div>
          <label className="block text-gray-700 text-sm font-bold mb-2">Account Type</label>
          <select
            value={form.role}
            onChange={(e) => setForm({ ...form, role: e.target.value })}
            className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
          >
            <option value="CLIENT">Client</option>
            <option value="PRESTATAIRE">Prestataire</option>
          </select>
        </div>

        {/* Conditionally show additional fields for PRESTATAIRE */}
        {form.role === "PRESTATAIRE" && (
          <>
            <div>
              <label className="block text-gray-700 text-sm font-bold mb-2">Speciality</label>
              <input
                value={form.speciality}
                onChange={(e) => setForm({ ...form, speciality: e.target.value })}
                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                placeholder="Your speciality (e.g., Plumber, Electrician)"
                required
              />
            </div>

            <div>
              <label className="block text-gray-700 text-sm font-bold mb-2">Location</label>
              <input
                value={form.location}
                onChange={(e) => setForm({ ...form, location: e.target.value })}
                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                placeholder="City or region"
                required
              />
            </div>

            <div>
              <label className="block text-gray-700 text-sm font-bold mb-2">Bio</label>
              <textarea
                value={form.bio}
                onChange={(e) => setForm({ ...form, bio: e.target.value })}
                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                placeholder="Describe your experience"
                required
              />
            </div>

            <div>
              <label className="block text-gray-700 text-sm font-bold mb-2">Hourly Rate (MAD)</label>
              <input
                type="number"
                min="0"
                step="0.01"
                value={form.hourlyRate}
                onChange={(e) => setForm({ ...form, hourlyRate: e.target.value })}
                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                placeholder="e.g., 150"
                required
              />
            </div>
          </>
        )}

        {/* Submit */}
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition duration-200 font-medium"
        >
          Register
        </button>
      </form>

      <div className="mt-4 text-center">
        <p className="text-gray-600">
          Already have an account?{' '}
          <button 
            onClick={() => navigate("/login")} 
            className="text-blue-600 hover:text-blue-800 font-medium"
          >
            Sign in
          </button>
        </p>
      </div>
    </div>
  );
}
