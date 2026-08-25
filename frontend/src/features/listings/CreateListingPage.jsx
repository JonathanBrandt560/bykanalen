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
            await createListing({
                title,
                description: description || null,
                image: imageBase64,
                price: price === "" ? null : Number(price),
                location: location || null,
            });
            navigate("/listings");
        } catch (err) {
            if (err.fieldErrors) {
                setFieldErrors(err.fieldErrors);
            } else {
                setGeneralError(err.message || "Kunde inte skapa annonsen");
            }
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
                    Pris (kr)
                    <input
                        type="number"
                        min="0"
                        value={price}
                        onChange={(e) => {
                            setPrice(e.target.value);
                            clearFieldError("price");
                        }}
                        className={fieldErrors.price ? `${styles.input} ${styles.inputError}` : styles.input}
                    />
                    {fieldErrors.price && <span className={styles.fieldError}>{fieldErrors.price}</span>}
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
