import { Link } from "react-router-dom";
import styles from "./ListingCard.module.css";

function ListingCard({ listing }) {
    return (
        <Link to={`/listings/${listing.id}`} className={styles.card}>
            <div className={styles.image}>
                {listing.imageUrl ? (
                    <img src={listing.imageUrl} alt="" />
                ) : (
                    <div
                        className={styles.imagePlaceholder}
                        aria-hidden="true"
                    />
                )}
            </div>

            <div className={styles.info}>
                <h3 className={styles.title}>{listing.title}</h3>
                <div className={styles.metaRow}>
                    <span className={styles.price}>{listing.price}</span>
                    <span className={styles.time}>
                        {new Date(listing.publishDate).toLocaleDateString(
                            "sv-SE",
                        )}
                    </span>
                </div>
            </div>
        </Link>
    );
}

export default ListingCard;
