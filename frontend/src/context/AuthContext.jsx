import { createContext, useContext, useState } from "react";
import { login as loginRequest } from "../api/authApi";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [token, setToken] = useState(() => localStorage.getItem("token"));

    async function login(username, password) {
        const receivedToken = await loginRequest(username, password);
        localStorage.setItem("token", receivedToken);
        setToken(receivedToken);
    }

    function logout() {
        localStorage.removeItem("token");
        setToken(null);
    }

    const value = {
        token,
        isLoggedIn: !!token,
        login,
        logout,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth måste användas inom en AuthProvider");
    }
    return context;
}
