import { useAuth } from "../../context/AuthContext";
import GuestHome from "./GuestHome";
import MemberHome from "./MemberHome";

function HomePage() {
    const { isLoggedIn } = useAuth();
    return isLoggedIn ? <MemberHome /> : <GuestHome />;
}

export default HomePage;