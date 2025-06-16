import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider, useAuth } from "./context/AuthContext";
import LoginForm from "./components/LoginForm";
import RegisterForm from "./components/RegisterForm";
import Profile from "./components/Profile";
import Annonce from "./components/Annonce";
// import AnnonceDetaile from "./components/AnnonceDetail";

// Composant qui gère le loading et le routing
function AppRoutes() {
  const { loading } = useAuth();

  if (loading) {
    return <div>Chargement...</div>; // ou un spinner
  }

  return (
    <Routes>
      <Route path="/" element={<Navigate to="/register" />} />
      <Route path="/login" element={<LoginForm />} />
      <Route path="/register" element={<RegisterForm />} />
      <Route path="/profile" element={<Profile />} />
      <Route path="/annonce" element={<Annonce />} />
  {/* <Route path="/annonce/:id" element={<AnnonceDetail />} /> */}
   {/* Create this later */}
    </Routes>
  );
}

function App() {
  return (
    <AuthProvider>
      <Router>
        <AppRoutes />
      </Router>
    </AuthProvider>
  );
}

export default App;
