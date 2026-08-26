import { useNavigate } from "react-router-dom";
import styles from "./ServiceCard.module.css";

function ServiceCard({ service, groupId }) {
    const navigate = useNavigate();

    function handleClick() {
        navigate(`/groups/${groupId}/services/${service.id}`);
    }

    function handleKeyDown(e) {
        if (e.key === "Enter") handleClick();
    }

    return (
        <article
            className={styles.card}
            onClick={handleClick}
            role="link"
            tabIndex={0}
            onKeyDown={handleKeyDown}
        >
            <div className={styles.image}>
                {service.image ? (
                    <img src={`data:image/jpeg;base64,${service.image}`} alt="" />
                ) : (
                    <div className={styles.imagePlaceholder} aria-hidden="true" />
                )}
            </div>

            <div className={styles.info}>
                <h3 className={styles.title}>{service.title}</h3>
                <p className={styles.date}>
                    Av {service.username} · {new Date(service.publishDate).toLocaleDateString("sv-SE")}
                </p> 
            </div>
        </article>
    );
}

export default ServiceCard;