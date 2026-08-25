import { useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { createGeneralPost } from "../../api/generalPostApi";
import styles from "./CreateGeneralPostPage.module.css";

function CreateGeneralPostPage() {
    const { groupId } = useParams();
    const navigate = useNavigate();

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [imageBase64, setImageBase64] = useState(null);
    const [fieldErrors, setFieldErrors] = useState({});
    const [generalError, setGeneralError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    function clearFieldError(name) {
        setFieldErrors((prev) => {
            if (!prev[name]) return prev;
            const next = { ...prev };
            delete next[name];
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
        reader.onload = () => setImageBase64(reader.result.split(",")[1]);
        reader.readAsDataURL(file);
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setGeneralError("");
        setFieldErrors({});
        setIsSubmitting(true);

        try {
            await createGeneralPost(groupId, {
                title,
                description: description || null,
                image: imageBase64,
            });
            navigate(`/groups/${groupId}/generalposts`);
        } catch (err) {
            if (err.fieldErrors) {
                setFieldErrors(err.fieldErrors);
            } else {
                setGeneralError(err.message || "Kunde inte skapa inlägget");
            }
        } finally {
            setIsSubmitting(false);
        }
    }

    return (
        <div className={styles.page}>
            <Link to={`/groups/${groupId}/generalposts`} className={styles.back}>
                ← Tillbaka
            </Link>
            <h1 className={styles.heading}>Nytt inlägg</h1>

            {generalError && <p className={styles.generalError}>{generalError}</p>}

            <form onSubmit={handleSubmit} className={styles.form} noValidate>
                <label className={styles.label}>
                    Titel
                    <input
                        value={title}
                        onChange={(e) => {
                            setTitle(e.target.value);
                            clearFieldError("title");
                        }}
                        className={fieldErrors.title ? `${styles.input} ${styles.inputError}` : styles.input}
                        maxLength={100}
                    />
                    {fieldErrors.title && <span className={styles.fieldError}>{fieldErrors.title}</span>}
                </label>

                <label className={styles.label}>
                    Beskrivning
                    <textarea
                        value={description}
                        onChange={(e) => {
                            setDescription(e.target.value);
                            clearFieldError("description");
                        }}
                        className={
                            fieldErrors.description ? `${styles.textarea} ${styles.inputError}` : styles.textarea
                        }
                        rows={5}
                        maxLength={500}
                    />
                    <span className={styles.charCount}>{description.length}/500</span>
                    {fieldErrors.description && (
                        <span className={styles.fieldError}>{fieldErrors.description}</span>
                    )}
                </label>

                <label className={styles.label}>
                    Bild (valfritt)
                    <input type="file" accept="image/*" onChange={handleImageChange} />
                </label>

                <button type="submit" disabled={isSubmitting} className={styles.submitButton}>
                    {isSubmitting ? "Publicerar…" : "Publicera inlägg"}
                </button>
            </form>
        </div>
    );
}

export default CreateGeneralPostPage;