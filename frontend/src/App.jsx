import { Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import ProtectedRoute from "./components/ProtectedRoute";
import Navbar from "./components/Navbar";
import HomePage from "./features/home/HomePage";
import RegisterPage from "./features/auth/RegisterPage";
import EventListPage from "./features/events/EventListPage";
import EventDetailPage from "./features/events/EventDetailPage";
import CreateEventPage from "./features/events/CreateEventPage";
import GeneralPostListPage from "./features/generalPosts/GeneralPostListPage";
import GeneralPostDetailPage from "./features/generalPosts/GeneralPostDetailPage";
import CreateGeneralPostPage from "./features/generalPosts/CreateGeneralPostPage";
import EditGeneralPostPage from "./features/generalPosts/EditGeneralPostPage";
import ListingPage from "./features/listings/ListingPage";
import ListingDetailPage from "./features/listings/ListingDetailPage";
import CreateListingPage from "./features/listings/CreateListingPage";
import ServiceListPage from "./features/services/ServiceListPage";
import CreateServicePage from "./features/services/CreateServicePage";

function App() {
    return (
        <AuthProvider>
            <Navbar />
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/register" element={<RegisterPage />} />

                <Route
                    path="/groups/:groupId/events"
                    element={
                        <ProtectedRoute>
                            <EventListPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/groups/:groupId/events/:id"
                    element={
                        <ProtectedRoute>
                            <EventDetailPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/groups/:groupId/events/new"
                    element={
                        <ProtectedRoute>
                            <CreateEventPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/groups/:groupId/generalposts"
                    element={
                        <ProtectedRoute>
                            <GeneralPostListPage />
                        </ProtectedRoute>
                    }
                />          
                <Route
                    path="/groups/:groupId/generalposts/:id"
                    element={
                        <ProtectedRoute>
                            <GeneralPostDetailPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/groups/:groupId/generalposts/new"
                    element={
                        <ProtectedRoute>
                            <CreateGeneralPostPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/groups/:groupId/generalposts/:id/edit"
                    element={
                        <ProtectedRoute>
                            <EditGeneralPostPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/listings"
                    element={
                        <ProtectedRoute>
                            <ListingPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/listings/:id"
                    element={
                        <ProtectedRoute>
                            <ListingDetailPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/listings/new"
                    element={
                        <ProtectedRoute>
                            <CreateListingPage />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/groups/:groupId/services"
                    element={
                        <ProtectedRoute>
                            <ServiceListPage />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/groups/:groupId/services/new"
                    element={
                        <ProtectedRoute>
                            <CreateServicePage />
                        </ProtectedRoute>
                    }
                />
                {/* TILLFÄLLIGA routes för att testa statisk layout — ta bort innan produktion */}
                <Route path="/preview-events" element={<EventListPage />} />
                <Route path="/preview-generalposts" element={<GeneralPostListPage />} />
            </Routes>
        </AuthProvider>
    );
}

export default App;