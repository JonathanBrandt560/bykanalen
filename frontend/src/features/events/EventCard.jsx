import { useNavigate } from "react-router-dom";
import { getDayAndMonth, formatTimeRange } from "../../utils/dateHelpers";
import EventRegistrationButton from "./EventRegistrationButton";
import styles from "./EventCard.module.css";

function EventCard({ event, groupId, isAdmin, onDelete }) {
    const navigate = useNavigate();
    const { day, month } = getDayAndMonth(event.startDate);

    function handleCardClick() {
        navigate(`/groups/${groupId}/events/${event.id}`);
    }

    function handleKeyDown(e) {
        if (e.key === "Enter") handleCardClick();
    }

    function handleDeleteClick(e) {
        e.stopPropagation();
        onDelete?.(event.id);
    }

    return (
        <article
            className={styles.card}
            onClick={handleCardClick}
            role="link"
            tabIndex={0}
            onKeyDown={handleKeyDown}
        >
            <div className={styles.dateBlock}>
                <span className={styles.day}>{day}</span>
                <span className={styles.month}>{month}</span>
            </div>

            <div className={styles.image}>
                {event.image ? (
                    <img src={`data:image/jpeg;base64,${event.image}`} alt="" />
                ) : (
                    <div className={styles.imagePlaceholder} aria-hidden="true" />
                )}
            </div>

            <div className={styles.info}>
                <h3 className={styles.title}>{event.title}</h3>
                <p className={styles.time}>
                    {formatTimeRange(event.startDate, event.endDate)}
                </p>
            </div>

            <div className={styles.actions}>
                <EventRegistrationButton groupId={groupId} eventId={event.id} />
                {isAdmin && (
                    <button onClick={handleDeleteClick} className={styles.deleteButton}>
                        Ta bort
                    </button>
                )}
            </div>
        </article>
    );
}

export default EventCard;