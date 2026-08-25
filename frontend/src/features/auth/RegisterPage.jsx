import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { register } from "../../api/authApi";
import styles from "./RegisterPage.module.css";

const FIELDS = [
    { name: "username", label: "Användarnamn", type: "text" },
    { name: "password", label: "Lösenord", type: "password" },
    { name: "email", label: "Email", type: "email" },
    { name: "age", label: "Ålder", type: "number" },
    { name: "firstName", label: "Förnamn", type: "text" },
    { name: "lastName", label: "Efternamn", type: "text" },
    { name: "groupId", label: "Grupp-id", type: "number" },
];

function RegisterPage() {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        username: "",
        password: "",
        email: "",
        age: "",
        firstName: "",
        lastName: "",
        groupId: "",
    });
    const [fieldErrors, setFieldErrors] = useState({});
    const [generalError, setGeneralError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    function handleChange(e) {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));

        // Rensa fältets felmeddelande så snart användaren börjar rätta det
        setFieldErrors((prev) => {
            if (!prev[name]) return prev;
            const next = { ...prev };
            delete next[name];
            return next;
        });
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setGeneralError("");
        setFieldErrors({});
        setIsSubmitting(true);

        try {
            await register({
                ...formData,
                age: Number(formData.age),
                groupId: Number(formData.groupId),
            });
            navigate("/");
        } catch (err) {
            if (err.fieldErrors) {
                setFieldErrors(err.fieldErrors);
            } else {
                setGeneralError(err.message || "Kunde inte skapa användare");
            }
        } finally {
            setIsSubmitting(false);
        }
    }

    return (
        <div className={styles.page}>
            <div className={styles.card}>
                <h1 className={styles.heading}>Skapa konto</h1>

                {generalError && <p className={styles.generalError}>{generalError}</p>}

                <form onSubmit={handleSubmit} className={styles.form} noValidate>
                    {FIELDS.map((field) => (
                        <label key={field.name} className={styles.label}>
                            {field.label}
                            <input
                                type={field.type}
                                name={field.name}
                                value={formData[field.name]}
                                onChange={handleChange}
                                className={
                                    fieldErrors[field.name]
                                        ? `${styles.input} ${styles.inputError}`
                                        : styles.input
                                }
                            />
                            {fieldErrors[field.name] && (
                                <span className={styles.fieldError}>{fieldErrors[field.name]}</span>
                            )}
                        </label>
                    ))}

                    <button type="submit" disabled={isSubmitting} className={styles.submitButton}>
                        {isSubmitting ? "Skapar konto…" : "Skapa konto"}
                    </button>
                </form>
            </div>
        </div>
    );
}

export default RegisterPage;