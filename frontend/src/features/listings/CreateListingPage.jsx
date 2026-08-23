import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { createListing } from "../../api/listingsApi";
import styles from "./CreateListingPage.module.css";

function CreateListingPage() {
    const navigate = useNavigate();

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [price, setPrice] = useState("");
    const [location, setLocation] = useState("");
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
            setImageBase64(reader.result.split(",")[1]);
        };
        reader.readAsDataURL(file);
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setError("");
        setIsSubmitting(true);

        try {
            await createListing({
                title,
                description: description || null,
                image: imageBase64,
                price: Number(price),
                location: location || null,
            });
            navigate("/listings");
        } catch (err) {
            setError(err.message || "Kunde inte skapa annonsen");
        } finally {
            setIsSubmitting(false);
        }
    }

    return (
        <div className={styles.page}>
            <Link to="/listings" className={styles.back}>
                ← Tillbaka
            </Link>
            <h1 className={styles.heading}>Ny annons</h1>

            {error && <p className={styles.error}>{error}</p>}

            <form onSubmit={handleSubmit} className={styles.form}>
                <label className={styles.label}>
                    Titel
                    <input
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        className={styles.input}
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
                    />
                </label>
                <label className={styles.label}>
                    Pris (kr)
                    <input
                        type="number"
                        min="0"
                        value={price}
                        onChange={(e) => setPrice(e.target.value)}
                        className={styles.input}
                        required
                    />
                </label>
                <label className={styles.label}>
                    Plats
                    <input
                        value={location}
                        onChange={(e) => setLocation(e.target.value)}
                        className={styles.input}
                    />
                </label>
                <label className={styles.label}>
                    Bild (valfritt)
                    <input type="file" accept="image/*" onChange={handleImageChange} />
                </label>

                <button type="submit" disabled={isSubmitting} className={styles.submitButton}>
                    {isSubmitting ? "Publicerar…" : "Publicera annons"}
                </button>
            </form>
        </div>
    );
}

export default CreateListingPage;