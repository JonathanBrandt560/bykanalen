import styles from "./ServiceCard.module.css";

function ServiceCard({ service }) {
    return (
        <article className={styles.card}>
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
                    {new Date(service.publishDate).toLocaleDateString("sv-SE")}
                </p>
                <p className={styles.description}>{service.description}</p>
            </div>
        </article>
    );
}

export default ServiceCard;