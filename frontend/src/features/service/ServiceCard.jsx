import { Link } from "react-router-dom";
import styles from "./ServiceCard.module.css";

function ServiceCard({ listing }) {
    return (
        <Link to={`/Services/${Service.id}`} className={styles.card}>
            <div className={styles.image}>
                {Service.imageUrl ? (
                    <img src={Service.imageUrl} alt="" />
                ) : (
                    <div
                        className={styles.imagePlaceholder}
                        aria-hidden="true"
                    />
                )}
            </div>

            <div className={styles.info}>
                <h3 className={styles.title}>{Service.title}</h3>
                <div className={styles.metaRow}>
                    <span className={styles.price}>{Service.price}</span>
                    <span className={styles.time}>
                        {new Date(Service.publishDate).toLocaleDateString(
                            "sv-SE",
                        )}
                    </span>
                </div>
            </div>
        </Link>
    );
}

export default ServiceCard;
