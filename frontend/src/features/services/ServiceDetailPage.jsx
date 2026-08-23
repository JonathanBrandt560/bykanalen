import { useParams, Link } from "react-router-dom";
import { mockListingPage } from "./mockServicePage";
import styles from "./ServiceDetailPage.module.css";

function ServiceDetailPage() {
    const { id } = useParams();
    const Service = mockServicePage.find((l) => l.id === Number(id));

    if (!Service) {
        return (
            <div className={styles.page}>
                <p>Annonsen hittades inte.</p>
                <Link to="/preview-Servicepage">Tillbaka till listan</Link>
            </div>
        );
    }

    return (
        <div className={styles.page}>
            <Link to="/preview-Servicepage" className={styles.back}>
                ← Tillbaka
            </Link>

            <article className={styles.card}>
                <div className={styles.image}>
                    {Service.imageUrl ? (
                        <img src={Service.imageUrl} alt={Service.title} />
                    ) : (
                        <div
                            className={styles.imagePlaceholder}
                            aria-hidden="true"
                        />
                    )}
                </div>

                <div className={styles.info}>
                    <h1 className={styles.title}>{lService.title}</h1>
                    <div className={styles.metaRow}>
                        <span className={styles.price}>{Service.price}</span>
                        <span className={styles.time}>
                            {new Date(Service.publishDate).toLocaleDateString(
                                "sv-SE",
                            )}
                        </span>
                    </div>

                    <p className={styles.description}>{Service.description}</p>

                    <div className={styles.pickupRow}>
                        <span className={styles.pickupLabel}>Hämtas i</span>
                        <span className={styles.pickupLocation}>
                            {Service.pickupLocation}
                        </span>
                    </div>
                </div>
            </article>
        </div>
    );
}

export default ServiceDetailPage;
