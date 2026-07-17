import { mockEvents } from "./mockEvents";
import EventCard from "./EventCard";
import styles from "./EventListPage.module.css";

function EventListPage() {
    return (
        <div className={styles.page}>
            <header className={styles.header}>
                <p className={styles.eyebrow}>Kalender</p>
                <h1 className={styles.heading}>Vad händer i stan?</h1>
                <p className={styles.subheading}>
                    Loppisar, föreningsmöten och fester — allt på ett ställe.
                </p>
            </header>

            <div className={styles.list}>
                {mockEvents.map((event) => (
                    <EventCard key={event.id} event={event} />
                ))}
            </div>
        </div>
    );
}

export default EventListPage;
