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
    const [error, setError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    function handleImageChange(e) {
        const file = e.target.files[0];
        if (!file) {
            setImageBase64(null);
            return;
        }
        const reader = new FileReader();
        reader.onload = () => {
            // reader.result har formen "data:image/jpeg;base64,XXXX" — vi vill bara ha delen efter kommatecknet
            setImageBase64(reader.result.split(",")[1]);
        };
        reader.readAsDataURL(file);
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setError("");
        setIsSubmitting(true);

        try {
            await createGeneralPost(groupId, {
                title,
                description: description || null,
                image: imageBase64,
            });
            navigate(`/groups/${groupId}/generalposts`);
        } catch (err) {
            setError(err.message || "Kunde inte skapa inlägget");
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

            {error && <p className={styles.error}>{error}</p>}

            <form onSubmit={handleSubmit} className={styles.form}>
                <label className={styles.label}>
                    Titel
                    <input
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        className={styles.input}
                        maxLength={100}
                        required
                    />
                </label>
                <label className={styles.label}>
                    Beskrivning
                    <textarea
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        className={styles.textarea}
                        rows={5}
                        maxLength={2000}
                    />
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