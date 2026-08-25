import { useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { createEvent } from "../../api/eventApi";
import styles from "./CreateEventPage.module.css";

function CreateEventPage() {
    const { groupId } = useParams();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        title: "",
        description: "",
        startDate: "",
        endDate: "",
        closeRegistrationDate: "",
    });
    const [error, setError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    function handleChange(e) {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setError("");
        setIsSubmitting(true);

        try {
            const dto = {
                title: formData.title,
                description: formData.description || null,
                startDate: formData.startDate || null,
                endDate: formData.endDate || null,
                closeRegistrationDate: formData.closeRegistrationDate || null,
            };
            await createEvent(groupId, dto);
            navigate(`/groups/${groupId}/events`);
        } catch (err) {
            setError(err.message || "Kunde inte skapa eventet");
        } finally {
            setIsSubmitting(false);
        }
    }

    return (
        <div className={styles.page}>
            <Link to={`/groups/${groupId}/events`} className={styles.back}>
                ← Tillbaka
            </Link>
            <h1 className={styles.heading}>Nytt evenemang</h1>

            {error && <p className={styles.error}>{error}</p>}

            <form onSubmit={handleSubmit} className={styles.form}>
                <label className={styles.label}>
                    Titel
                    <input
                        name="title"
                        value={formData.title}
                        onChange={handleChange}
                        className={styles.input}
                        required
                    />
                </label>
                <label className={styles.label}>
                    Beskrivning
                    <textarea
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        className={styles.textarea}
                        rows={4}
                    />
                </label>
                <label className={styles.label}>
                    Startdatum och tid
                    <input
                        type="datetime-local"
                        name="startDate"
                        value={formData.startDate}
                        onChange={handleChange}
                        className={styles.input}
                        required
                    />
                </label>
                <label className={styles.label}>
                    Slutdatum och tid
                    <input
                        type="datetime-local"
                        name="endDate"
                        value={formData.endDate}
                        onChange={handleChange}
                        className={styles.input}
                    />
                </label>
                <label className={styles.label}>
                    Sista anmälningsdag
                    <input
                        type="datetime-local"
                        name="closeRegistrationDate"
                        value={formData.closeRegistrationDate}
                        onChange={handleChange}
                        className={styles.input}
                    />
                </label>

                <button type="submit" disabled={isSubmitting} className={styles.submitButton}>
                    {isSubmitting ? "Skapar…" : "Skapa evenemang"}
                </button>
            </form>
        </div>
    );
}

export default CreateEventPage;