import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import styles from "./GuestHome.module.css";

function GuestHome() {
    const { login } = useAuth();
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    async function handleSubmit(e) {
        e.preventDefault();
        setError("");
        setIsSubmitting(true);

        try {
            await login(username, password);
            navigate("/");
        } catch (err) {
            setError(err.message || "Fel användarnamn eller lösenord");
        } finally {
            setIsSubmitting(false);
        }
    }

    return (
        <div className={styles.page}>
            <section className={styles.hero}>
                <p className={styles.eyebrow}>Digital anslagstavla</p>
                <h1 className={styles.heading}>Bykanalen</h1>
                <p className={styles.lead}>
                    Bykanalen är en plattform för mindre samhällen och byar som är
                    intresserade av att enas digitalt. Här samlas kalendern, det
                    lokala snacket, marknadsplatsen och tjänsterna grannar erbjuder
                    varandra — allt på ett och samma ställe.
                </p>

                <ul className={styles.features}>
                    <li>
                        <span className={styles.featureLabel}>Kalender</span>
                        Loppisar, föreningsmöten och fester i din by
                    </li>
                    <li>
                        <span className={styles.featureLabel}>Anslagstavla</span>
                        Allt mellan himmel och jord, från grannar till grannar
                    </li>
                    <li>
                        <span className={styles.featureLabel}>Marknad</span>
                        Köp, sälj och skänk bort inom byn
                    </li>
                    <li>
                        <span className={styles.featureLabel}>Tjänster</span>
                        Hjälp varandra med allt från gräsklippning till barnpassning
                    </li>
                </ul>
            </section>

            <section className={styles.noticeboard}>
                <div className={styles.pinnedNote}>
                    <span className={styles.pin} aria-hidden="true" />

                    <p className={styles.noticeEyebrow}>Endast för medlemmar</p>
                    <p className={styles.noticeText}>
                        Bykanalen är exklusivt tillgänglig för sina medlemmar. Logga in
                        för att se vad som är på gång i din by.
                    </p>

                    {error && <p className={styles.error}>{error}</p>}

                    <form onSubmit={handleSubmit} className={styles.form}>
                        <label className={styles.label}>
                            Användarnamn
                            <input
                                type="text"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                className={styles.input}
                            />
                        </label>
                        <label className={styles.label}>
                            Lösenord
                            <input
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                className={styles.input}
                            />
                        </label>
                        <button type="submit" disabled={isSubmitting} className={styles.submitButton}>
                            {isSubmitting ? "Loggar in…" : "Logga in"}
                        </button>
                    </form>

                    <p className={styles.registerLine}>
                        Ny i byn? <Link to="/register">Bli medlem</Link>
                    </p>
                </div>
            </section>
        </div>
    );
}

export default GuestHome;