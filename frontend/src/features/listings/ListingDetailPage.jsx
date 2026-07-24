import { useParams, Link } from "react-router-dom";
import { mockListingPage } from "./mockListingPage";
import styles from "./ListingDetailPage.module.css";

function ListingDetailPage() {
    const { id } = useParams();
    const listing = mockListingPage.find((l) => l.id === Number(id));

    if (!listing) {
        return (
            <div className={styles.page}>
                <p>Annonsen hittades inte.</p>
                <Link to="/preview-listingpage">Tillbaka till listan</Link>
            </div>
        );
    }

    return (
        <div className={styles.page}>
            <Link to="/preview-listingpage" className={styles.back}>
                ← Tillbaka
            </Link>

            <article className={styles.card}>
                <div className={styles.image}>
                    {listing.imageUrl ? (
                        <img src={listing.imageUrl} alt={listing.title} />
                    ) : (
                        <div
                            className={styles.imagePlaceholder}
                            aria-hidden="true"
                        />
                    )}
                </div>

                <div className={styles.info}>
                    <h1 className={styles.title}>{listing.title}</h1>
                    <div className={styles.metaRow}>
                        <span className={styles.price}>{listing.price}</span>
                        <span className={styles.time}>
                            {new Date(listing.publishDate).toLocaleDateString(
                                "sv-SE",
                            )}
                        </span>
                    </div>

                    <p className={styles.description}>{listing.description}</p>

                    <div className={styles.pickupRow}>
                        <span className={styles.pickupLabel}>Hämtas i</span>
                        <span className={styles.pickupLocation}>
                            {listing.pickupLocation}
                        </span>
                    </div>
                </div>
            </article>
        </div>
    );
}

export default ListingDetailPage;
