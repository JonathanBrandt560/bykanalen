import { useParams, useNavigate, Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { getEventById, deleteEvent } from "../../api/eventApi";
import { useAuth } from "../../context/AuthContext";
import { formatTimeRange } from "../../utils/dateHelpers";
import EventRegistrationButton from "./EventRegistrationButton";
import styles from "./EventDetailPage.module.css";

function EventDetailPage() {
    const { groupId, id } = useParams();
    const navigate = useNavigate();
    const { isAdmin } = useAuth();
    const [event, setEvent] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        getEventById(groupId, id)
            .then(setEvent)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [groupId, id]);

    function handleCountChange(delta) {
        setEvent((prev) => prev && { ...prev, registrationCount: prev.registrationCount + delta });
    }

    async function handleDelete() {
        if (!window.confirm("Vill du verkligen ta bort det här evenemanget?")) return;
        try {
            await deleteEvent(groupId, id);
            navigate(`/groups/${groupId}/events`);
        } catch (err) {
            alert("Kunde inte ta bort eventet: " + err.message);
        }
    }

    if (loading) return <div className={styles.page}>Laddar...</div>;

    if (error || !event) {
        return (
            <div className={styles.page}>
                <p>Eventet hittades inte.</p>
                <Link to={`/groups/${groupId}/events`}>Tillbaka till kalendern</Link>
            </div>
        );
    }

    return (
        <div className={styles.page}>
            <Link to={`/groups/${groupId}/events`} className={styles.back}>
                ← Tillbaka
            </Link>

            <article className={styles.card}>
                <div className={styles.image}>
                    {event.image ? (
                        <img src={`data:image/jpeg;base64,${event.image}`} alt="" />
                    ) : (
                        <div className={styles.imagePlaceholder} aria-hidden="true" />
                    )}
                </div>

                <div className={styles.info}>
                    <h1 className={styles.title}>{event.title}</h1>
                    <p className={styles.time}>
                        {formatTimeRange(event.startDate, event.endDate)}
                    </p>

                    <p className={styles.description}>{event.description}</p>

                    <p className={styles.registrationCount}>
                        {event.registrationCount} anmälda
                    </p>

                    <div className={styles.actionsRow}>
                        <EventRegistrationButton
                            groupId={groupId}
                            eventId={id}
                            onCountChange={handleCountChange}
                        />
                        {isAdmin && (
                            <button onClick={handleDelete} className={styles.deleteButton}>
                                Ta bort evenemang
                            </button>
                        )}
                    </div>
                </div>
            </article>
        </div>
    );
}

export default EventDetailPage;