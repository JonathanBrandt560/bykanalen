import { Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import ProtectedRoute from "./components/ProtectedRoute";
import LoginPage from "./features/auth/LoginPage";
import RegisterPage from "./features/auth/RegisterPage";
import EventListPage from "./features/events/EventListPage";
import GeneralPostListPage from "./features/generalPosts/GeneralPostListPage";
import ListingPage from "./features/listings/ListingPage";
import ListingDetailPage from "./features/listings/ListingDetailPage";

function HomePage() {
    return (
        <div>
            <h1>Välkommen!</h1>
        </div>
    );
}

function App() {
    return (
        <AuthProvider>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route
                    path="/"
                    element={
                        <ProtectedRoute>
                            <HomePage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/events"
                    element={
                        <ProtectedRoute>
                            <EventListPage />
                        </ProtectedRoute>
                    }
                />
                {/* TILLFÄLLIGa routes för att testa statisk layout utan inloggning — ta bort innan ni kopplar på riktig auth/API */}
                <Route path="/preview-events" element={<EventListPage />} />
                <Route
                    path="/preview-generalposts"
                    element={<GeneralPostListPage />}
                />
                <Route path="/preview-listingpage" element={<ListingPage />} />
                <Route path="/listings/:id" element={<ListingDetailPage />} />
            </Routes>
        </AuthProvider>
    );
}

export default App;
