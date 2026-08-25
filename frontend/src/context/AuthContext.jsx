import { createContext, useContext, useState, useEffect } from "react";
import { login as loginRequest, getCurrentUser } from "../api/authApi";
import { getMyGroups } from "../api/groupApi";
 
const AuthContext = createContext(null);
 
export function AuthProvider({ children }) {
    const [token, setToken] = useState(() => localStorage.getItem("token"));
    const [groups, setGroups] = useState([]);
    const [activeGroupId, setActiveGroupId] = useState(() => localStorage.getItem("activeGroupId"));
    const [isAdmin, setIsAdmin] = useState(false);
    const [username, setUsername] = useState(null);
 
    useEffect(() => {
        if (!token) {
            setGroups([]);
            setIsAdmin(false);
            setUsername(null);
            return;
        }
 
        getMyGroups()
            .then((fetchedGroups) => {
                setGroups(fetchedGroups);
                setActiveGroupId((prev) => {
                    if (prev) return prev;
                    const firstId = fetchedGroups[0]?.id ?? null;
                    if (firstId) localStorage.setItem("activeGroupId", firstId);
                    return firstId;
                });
            })
            .catch((err) => console.error("Kunde inte hämta grupper:", err));
 
        getCurrentUser()
            .then((userDetail) => {
                setIsAdmin(userDetail.type === "admin");
                setUsername(userDetail.username);
            })
            .catch((err) => console.error("Kunde inte hämta användarinfo:", err));
    }, [token]);
 
    async function login(username, password) {
        const receivedToken = await loginRequest(username, password);
        localStorage.setItem("token", receivedToken);
        setToken(receivedToken);
    }
 
    function logout() {
        localStorage.removeItem("token");
        localStorage.removeItem("activeGroupId");
        setToken(null);
        setGroups([]);
        setActiveGroupId(null);
        setIsAdmin(false);
        setUsername(null);
    }
 
    function switchGroup(groupId) {
        localStorage.setItem("activeGroupId", groupId);
        setActiveGroupId(groupId);
    }
 
    const value = {
        token,
        isLoggedIn: !!token,
        groups,
        activeGroupId,
        switchGroup,
        isAdmin,
        username,
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