const API_BASE = "http://localhost:8081/api";

export async function register(data) {
  const res = await fetch(`${API_BASE}/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error(await res.text());
  return res.text(); // "User registered successfully"
}

export async function login(credentials) {
  const res = await fetch(`${API_BASE}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(credentials),
  });
  if (!res.ok) throw new Error("Invalid credentials");
  return res.json(); // contains token, username, role, userId
}

export async function getProfile(token) {
  const res = await fetch(`${API_BASE}/users/profile`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  if (!res.ok) throw new Error("Unauthorized");
  return res.json();
}
