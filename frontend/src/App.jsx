import { useState } from "react";
import LoginPage from "./LoginPage";

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    if (!isLoggedIn) {
        return <LoginPage onLogin={() => setIsLoggedIn(true)} />;
    }

    return (
        <div>
            <h1>Välkommen!</h1>
        </div>
    );
}

export default App;