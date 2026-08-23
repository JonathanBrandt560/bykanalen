import { useState, useEffect } from "react";
import { getRegistrationStatus, registerForEvent, unregisterFromEvent } from "../../api/eventRegistrationApi";
import styles from "./EventRegistrationButton.module.css";

function EventRegistrationButton({ groupId, eventId, onCountChange }) {
    const [isRegistered, setIsRegistered] = useState(false);
    const [loading, setLoading] = useState(true);
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        let cancelled = false;
        getRegistrationStatus(groupId, eventId)
            .then((status) => {
                if (!cancelled) setIsRegistered(status);
            })
            .catch((err) => console.error("Kunde inte hämta anmälningsstatus:", err))
            .finally(() => {
                if (!cancelled) setLoading(false);
            });
        return () => { cancelled = true; };
    }, [groupId, eventId]);

    async function handleClick(e) {
        e.stopPropagation(); // förhindrar att kortets klick (navigering) triggas samtidigt
        if (isSubmitting) return;
        setIsSubmitting(true);

        const wasRegistered = isRegistered;
        setIsRegistered(!wasRegistered);
        onCountChange?.(wasRegistered ? -1 : 1);

        try {
            if (wasRegistered) {
                await unregisterFromEvent(groupId, eventId);
            } else {
                await registerForEvent(groupId, eventId);
            }
        } catch (err) {
            setIsRegistered(wasRegistered);
            onCountChange?.(wasRegistered ? 1 : -1);
            console.error("Kunde inte uppdatera anmälan:", err);
        } finally {
            setIsSubmitting(false);
        }
    }

    if (loading) return null;

    return (
        <button
            onClick={handleClick}
            disabled={isSubmitting}
            className={isRegistered ? styles.registered : styles.button}
        >
            {isRegistered ? "Avanmäl dig" : "Anmäl dig"}
        </button>
    );
}

export default EventRegistrationButton;