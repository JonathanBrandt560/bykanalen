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
    const [imageBase64, setImageBase64] = useState(null);
    const [fieldErrors, setFieldErrors] = useState({});
    const [generalError, setGeneralError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    function handleChange(e) {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));

        // Rensa relevanta fel för fältet så snart användaren börjar rätta det
        setFieldErrors((prev) => {
            const next = { ...prev };
            delete next[name];
            // Rensa även korsvalideringsfel som är kopplade till det här fältet
            if (name === "endDate") delete next.endDateValid;
            if (name === "closeRegistrationDate") delete next.closeRegistrationDateValid;
            if (name === "startDate") {
                delete next.endDateValid;
                delete next.closeRegistrationDateValid;
            }
            return next;
        });
    }

    function handleImageChange(e) {
        const file = e.target.files[0];
        if (!file) {
            setImageBase64(null);
            return;
        }
        const reader = new FileReader();
        reader.onload = () => {
            setImageBase64(reader.result.split(",")[1]);
        };
        reader.readAsDataURL(file);
    }

    // Slår ihop ett fälts eget valideringsfel med eventuellt korsvalideringsfel
    // (t.ex. "endDate" + "endDateValid") till ett enda meddelande att visa under fältet
    function getFieldError(fieldName, crossValidationKey) {
        return fieldErrors[fieldName] ?? (crossValidationKey ? fieldErrors[crossValidationKey] : undefined);
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setGeneralError("");
        setFieldErrors({});
        setIsSubmitting(true);

        try {
            const dto = {
                title: formData.title,
                image: imageBase64,
                description: formData.description || null,
                startDate: formData.startDate || null,
                endDate: formData.endDate || null,
                closeRegistrationDate: formData.closeRegistrationDate || null,
            };
            await createEvent(groupId, dto);
            navigate(`/groups/${groupId}/events`);
        } catch (err) {
            if (err.fieldErrors) {
                setFieldErrors(err.fieldErrors);
            } else {
                setGeneralError(err.message || "Kunde inte skapa eventet");
            }
        } finally {
            setIsSubmitting(false);
        }
    }

    const titleError = getFieldError("title");
    const startDateError = getFieldError("startDate");
    const endDateError = getFieldError("endDate", "endDateValid");
    const closeRegistrationDateError = getFieldError("closeRegistrationDate", "closeRegistrationDateValid");

    return (
        <div className={styles.page}>
            <Link to={`/groups/${groupId}/events`} className={styles.back}>
                ← Tillbaka
            </Link>
            <h1 className={styles.heading}>Nytt evenemang</h1>

            {generalError && <p className={styles.generalError}>{generalError}</p>}

            <form onSubmit={handleSubmit} className={styles.form} noValidate>
                <label className={styles.label}>
                    Titel
                    <input
                        name="title"
                        value={formData.title}
                        onChange={handleChange}
                        className={titleError ? `${styles.input} ${styles.inputError}` : styles.input}
                    />
                    {titleError && <span className={styles.fieldError}>{titleError}</span>}
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
                    Bild (valfritt)
                    <input type="file" accept="image/*" onChange={handleImageChange} />
                </label>

                <label className={styles.label}>
                    Startdatum och tid
                    <input
                        type="datetime-local"
                        name="startDate"
                        value={formData.startDate}
                        onChange={handleChange}
                        className={startDateError ? `${styles.input} ${styles.inputError}` : styles.input}
                    />
                    {startDateError && <span className={styles.fieldError}>{startDateError}</span>}
                </label>

                <label className={styles.label}>
                    Slutdatum och tid
                    <input
                        type="datetime-local"
                        name="endDate"
                        value={formData.endDate}
                        onChange={handleChange}
                        className={endDateError ? `${styles.input} ${styles.inputError}` : styles.input}
                    />
                    {endDateError && <span className={styles.fieldError}>{endDateError}</span>}
                </label>

                <label className={styles.label}>
                    Sista anmälningsdag
                    <input
                        type="datetime-local"
                        name="closeRegistrationDate"
                        value={formData.closeRegistrationDate}
                        onChange={handleChange}
                        className={
                            closeRegistrationDateError ? `${styles.input} ${styles.inputError}` : styles.input
                        }
                    />
                    {closeRegistrationDateError && (
                        <span className={styles.fieldError}>{closeRegistrationDateError}</span>
                    )}
                </label>

                <button type="submit" disabled={isSubmitting} className={styles.submitButton}>
                    {isSubmitting ? "Skapar…" : "Skapa evenemang"}
                </button>
            </form>
        </div>
    );
}

export default CreateEventPage;