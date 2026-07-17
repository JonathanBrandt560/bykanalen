import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { register } from "../../api/authApi";

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
            // RegisterDTO förväntar sig age och groupId som nummer, inte strängar
            await register({
                ...formData,
                age: Number(formData.age),
                groupId: Number(formData.groupId),
            });
            navigate("/login");
        } catch (err) {
            setError(err.message || "Kunde inte skapa användare");
        } finally {
            setIsSubmitting(false);
        }
    }

    return (
        <div>
            <h2>Skapa konto</h2>
            {error && <p style={{ color: "red" }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Användarnamn</label>
                    <input name="username" value={formData.username} onChange={handleChange} />
                </div>
                <div>
                    <label>Lösenord</label>
                    <input type="password" name="password" value={formData.password} onChange={handleChange} />
                </div>
                <div>
                    <label>Email</label>
                    <input type="email" name="email" value={formData.email} onChange={handleChange} />
                </div>
                <div>
                    <label>Ålder</label>
                    <input type="number" name="age" value={formData.age} onChange={handleChange} />
                </div>
                <div>
                    <label>Förnamn</label>
                    <input name="firstName" value={formData.firstName} onChange={handleChange} />
                </div>
                <div>
                    <label>Efternamn</label>
                    <input name="lastName" value={formData.lastName} onChange={handleChange} />
                </div>
                <div>
                    <label>Grupp-id</label>
                    <input type="number" name="groupId" value={formData.groupId} onChange={handleChange} />
                </div>
                <button type="submit" disabled={isSubmitting}>
                    {isSubmitting ? "Skapar konto…" : "Skapa konto"}
                </button>
            </form>
        </div>
    );
}

export default RegisterPage;
