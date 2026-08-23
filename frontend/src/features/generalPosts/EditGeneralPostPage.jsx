import { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { getGeneralPostById, patchGeneralPost } from "../../api/generalPostApi";
import styles from "./EditGeneralPostPage.module.css";

function EditGeneralPostPage() {
    const { groupId, id } = useParams();
    const navigate = useNavigate();

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        getGeneralPostById(groupId, id)
            .then((post) => {
                setTitle(post.title ?? "");
                setDescription(post.description ?? "");
            })
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [groupId, id]);

    async function handleSubmit(e) {
        e.preventDefault();
        setError("");
        setIsSubmitting(true);

        try {
            await patchGeneralPost(groupId, id, { title, description });
            navigate(`/groups/${groupId}/generalposts/${id}`);
        } catch (err) {
            setError(err.message || "Kunde inte spara ändringarna");
        } finally {
            setIsSubmitting(false);
        }
    }

    if (loading) return <div className={styles.page}>Laddar...</div>;

    return (
        <div className={styles.page}>
            <Link to={`/groups/${groupId}/generalposts/${id}`} className={styles.back}>
                ← Tillbaka
            </Link>
            <h1 className={styles.heading}>Redigera inlägg</h1>

            {error && <p className={styles.error}>{error}</p>}

            <form onSubmit={handleSubmit} className={styles.form}>
                <label className={styles.label}>
                    Titel
                    <input
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        className={styles.input}
                        maxLength={100}
                        minLength={1}
                        required
                    />
                </label>
                <label className={styles.label}>
                    Beskrivning
                    <textarea
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        className={styles.textarea}
                        rows={6}
                        maxLength={2000}
                    />
                </label>

                <button type="submit" disabled={isSubmitting} className={styles.submitButton}>
                    {isSubmitting ? "Sparar…" : "Spara ändringar"}
                </button>
            </form>
        </div>
    );
}

export default EditGeneralPostPage;