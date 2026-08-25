import { useParams, Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { getUpcomingEvents, getEventsAfterDate, getEventsBeforeDate, deleteEvent } from "../../api/eventApi";
import { useAuth } from "../../context/AuthContext";
import EventCard from "./EventCard";
import styles from "./EventListPage.module.css";

const FILTER_OPTIONS = [
    { value: "upcoming", label: "Kommande" },
    { value: "after", label: "Efter datum" },
    { value: "before", label: "Före datum" },
];

function EventListPage() {
    const { groupId } = useParams();
    const { isAdmin } = useAuth();
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [filterMode, setFilterMode] = useState("upcoming");
    const [selectedDate, setSelectedDate] = useState("");

    useEffect(() => {
        if (filterMode !== "upcoming" && !selectedDate) return;

        setLoading(true);
        setError(null);

        let request;
        if (filterMode === "upcoming") {
            request = getUpcomingEvents(groupId);
        } else if (filterMode === "after") {
            request = getEventsAfterDate(groupId, selectedDate);
        } else {
            request = getEventsBeforeDate(groupId, selectedDate);
        }

        request
            .then(setEvents)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [groupId, filterMode, selectedDate]);

    function handleFilterModeChange(e) {
        setFilterMode(e.target.value);
        if (e.target.value === "upcoming") setSelectedDate("");
    }

    async function handleDeleteEvent(eventId) {
        if (!window.confirm("Vill du verkligen ta bort det här evenemanget?")) return;
        try {
            await deleteEvent(groupId, eventId);
            setEvents((prev) => prev.filter((e) => e.id !== eventId));
        } catch (err) {
            alert("Kunde inte ta bort eventet: " + err.message);
        }
    }

    return (
        <div className={styles.page}>
            <header className={styles.header}>
                <p className={styles.eyebrow}>Kalender</p>
                <h1 className={styles.heading}>Vad händer i stan?</h1>
                <p className={styles.subheading}>
                    Loppisar, föreningsmöten och fester — allt på ett ställe.
                </p>
            </header>

            {isAdmin && (
                <Link to={`/groups/${groupId}/events/new`} className={styles.addButton}>
                    + Lägg till nytt evenemang
                </Link>
            )}

            <div className={styles.controls}>
                <select className={styles.filterSelect} value={filterMode} onChange={handleFilterModeChange}>
                    {FILTER_OPTIONS.map((option) => (
                        <option key={option.value} value={option.value}>{option.label}</option>
                    ))}
                </select>

                {filterMode !== "upcoming" && (
                    <input
                        type="date"
                        className={styles.dateInput}
                        value={selectedDate}
                        onChange={(e) => setSelectedDate(e.target.value)}
                    />
                )}
            </div>

            {loading && <p>Laddar events...</p>}
            {error && <p>Något gick fel: {error}</p>}

            {!loading && !error && filterMode !== "upcoming" && !selectedDate && (
                <p className={styles.hint}>Välj ett datum för att se events.</p>
            )}

            {!loading && !error && (filterMode === "upcoming" || selectedDate) && (
                <div className={styles.list}>
                    {events.length === 0 ? (
                        <p className={styles.hint}>Inga events hittades.</p>
                    ) : (
                        events.map((event) => (
                            <EventCard
                                key={event.id}
                                event={event}
                                groupId={groupId}
                                isAdmin={isAdmin}
                                onDelete={handleDeleteEvent}
                            />
                        ))
                    )}
                </div>
            )}
        </div>
    );
}

export default EventListPage;