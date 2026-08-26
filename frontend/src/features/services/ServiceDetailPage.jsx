import { useParams, useNavigate, Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { getServiceById } from "../../api/serviceApi";
import styles from "./ServiceDetailPage.module.css";

function ServiceDetailPage() {
    const { groupId, id } = useParams();
    const navigate = useNavigate();
    const [service, setService] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        getServiceById(groupId, id)
            .then(setService)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [groupId, id]);

    if (loading) return <div className={styles.page}>Laddar...</div>;

    if (error || !service) {
        return (
            <div className={styles.page}>
                <p>Tjänsten hittades inte.</p>
                <Link to={`/groups/${groupId}/services`}>Tillbaka till tjänster</Link>
            </div>
        );
    }

    return (
        <div className={styles.page}>
            <button onClick={() => navigate(-1)} className={styles.back}>
                ← Tillbaka
            </button>

            <article className={styles.card}>
                <div className={styles.image}>
                    {service.image ? (
                        <img src={`data:image/jpeg;base64,${service.image}`} alt="" />
                    ) : (
                        <div className={styles.imagePlaceholder} aria-hidden="true" />
                    )}
                </div>

                <div className={styles.info}>
                    <p className={styles.date}>
                        Av {service.username} · {new Date(service.publishDate).toLocaleDateString("sv-SE")}
                    </p>
                    <h1 className={styles.title}>{service.title}</h1>
                    <p className={styles.description}>{service.description}</p>
                </div>
            </article>
        </div>
    );
}

export default ServiceDetailPage;