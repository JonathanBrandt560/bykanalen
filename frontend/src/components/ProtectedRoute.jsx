import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

// Wrappa en route med denna för att kräva inloggning, t.ex.:
// <Route path="/" element={<ProtectedRoute><HomePage /></ProtectedRoute>} />
function ProtectedRoute({ children }) {
    const { isLoggedIn } = useAuth();

    if (!isLoggedIn) {
        return <Navigate to="/" replace />;
    }

    return children;
}

export default ProtectedRoute;
