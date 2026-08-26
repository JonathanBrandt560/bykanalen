import { useParams, Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { getServicesForGroup } from "../../api/serviceApi";
import ServiceCard from "./ServiceCard";
import styles from "./ServiceListPage.module.css";

function ServiceListPage() {
    const { groupId } = useParams();
    const [services, setServices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        getServicesForGroup(groupId)
            .then(setServices)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [groupId]);

    return (
        <div className={styles.page}>
            <header className={styles.header}>
                <p className={styles.eyebrow}>Tjänster</p>
                <h1 className={styles.heading}>Hjälp varandra i byn</h1>
                <p className={styles.subheading}>
                    Allt från gräsklippning till barnpassning — erbjud eller hitta en tjänst.
                </p>
            </header>

            <Link to={`/groups/${groupId}/services/new`} className={styles.addButton}>
                + Ny tjänst
            </Link>

            {loading && <p>Laddar tjänster...</p>}
            {error && <p>Något gick fel: {error}</p>}

            {!loading && !error && (
                <div className={styles.list}>
                    {services.length === 0 ? (
                        <p className={styles.hint}>Inga tjänster hittades.</p>
                    ) : (
                        services.map((service) => (
                            <ServiceCard key={service.id} service={service} groupId={groupId} />
                        ))
                    )}
                </div>
            )}
        </div>
    );
}

export default ServiceListPage;