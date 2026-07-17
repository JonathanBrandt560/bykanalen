import { getDayAndMonth, formatTimeRange } from "../../utils/dateHelpers";
import styles from "./EventCard.module.css";

function EventCard({ event }) {
    const { day, month } = getDayAndMonth(event.startDate);

    return (
        <article className={styles.card}>
            <div className={styles.dateBlock}>
                <span className={styles.day}>{day}</span>
                <span className={styles.month}>{month}</span>
            </div>

            <div className={styles.image}>
                {event.image ? (
                    <img
                        src={`data:image/jpeg;base64,${event.image}`}
                        alt=""
                    />
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
        </article>
    );
}

export default EventCard;
