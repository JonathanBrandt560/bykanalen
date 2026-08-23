import { useParams, Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { fetchListingById } from "../../api/listingsApi";
import styles from "./ListingDetailPage.module.css";

function ListingDetailPage() {
    const { id } = useParams();
    const [listing, setListing] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchListingById(id)
            .then((data) => {
                setListing(data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, [id]);

    if (loading) return <div className={styles.page}>Laddar...</div>;

    if (error || !listing) {
        return (
            <div className={styles.page}>
                <p>Annonsen hittades inte.</p>
                <Link to="/listings">Tillbaka till listan</Link>
            </div>
        );
    }

    return (
        <div className={styles.page}>
            <Link to="/listings" className={styles.back}>
                ← Tillbaka
            </Link>

            <article className={styles.card}>
                <div className={styles.image}>
                    {listing.image ? (
                        <img
                            src={`data:image/jpeg;base64,${listing.image}`}
                            alt={listing.title}
                        />
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
                        <span className={styles.price}>{listing.price} kr</span>
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
                            {listing.location}
                        </span>
                    </div>
                </div>
            </article>
        </div>
    );
}

export default ListingDetailPage;
