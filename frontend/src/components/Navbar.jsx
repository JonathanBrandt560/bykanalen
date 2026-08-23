import { NavLink, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import styles from "./Navbar.module.css";

function Navbar() {
    const { isLoggedIn, groups, activeGroupId, switchGroup, logout } = useAuth();

    if (!isLoggedIn) return null;

    const activeGroup = groups.find((g) => String(g.id) === String(activeGroupId));

    return (
        <nav className={styles.navbar}>
            <div className={styles.left}>
                <Link to="/" className={styles.brand}>Bykanalen</Link>

                {groups.length > 1 ? (
                    <select
                        className={styles.groupSelect}
                        value={activeGroupId ?? ""}
                        onChange={(e) => switchGroup(e.target.value)}
                    >
                        {groups.map((g) => (
                            <option key={g.id} value={g.id}>{g.groupName}</option>
                        ))}
                    </select>
                ) : (
                    activeGroup && <span className={styles.groupName}>{activeGroup.groupName}</span>
                )}
            </div>

            {activeGroupId && (
                <div className={styles.links}>
                    <NavLink
                        to={`/groups/${activeGroupId}/generalposts`}
                        className={({ isActive }) => (isActive ? styles.linkActive : styles.link)}
                    >
                        Anslagstavla
                    </NavLink>
                    <NavLink
                        to={`/groups/${activeGroupId}/events`}
                        className={({ isActive }) => (isActive ? styles.linkActive : styles.link)}
                    >
                        Kalender
                    </NavLink>
                    <NavLink
                        to="/listings"
                        className={({ isActive }) => (isActive ? styles.linkActive : styles.link)}
                    >
                        Marknad
                    </NavLink>
                    <NavLink
                        to={`/groups/${activeGroupId}/services`}
                        className={({ isActive }) => (isActive ? styles.linkActive : styles.link)}
                    >
                        Tjänster
                    </NavLink>
                </div>
            )}

            <button className={styles.logoutButton} onClick={logout}>
                Logga ut
            </button>
        </nav>
    );
}

export default Navbar;