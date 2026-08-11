import { useState, useMemo, useEffect } from "react";
import { fetchListings } from "../../api/listingsApi";
import ListingCard from "./ListingCard";
import styles from "./ListingPage.module.css";

const PAGE_SIZE = 20;

const SORT_OPTIONS = [
    { value: "newest", label: "Nyast först" },
    { value: "oldest", label: "Äldst först" },
    { value: "priceLow", label: "Pris: lägst först" },
    { value: "priceHigh", label: "Pris: högst först" },
    { value: "alphabetical", label: "Titel (A-Ö)" },
];

function sortListings(listings, sortBy) {
    const sorted = [...listings];

    switch (sortBy) {
        case "newest":
            return sorted.sort(
                (a, b) => new Date(b.publishDate) - new Date(a.publishDate),
            );
        case "oldest":
            return sorted.sort(
                (a, b) => new Date(a.publishDate) - new Date(b.publishDate),
            );
        case "priceLow":
            return sorted.sort((a, b) => a.price - b.price);
        case "priceHigh":
            return sorted.sort((a, b) => b.price - a.price);
        case "alphabetical":
            return sorted.sort((a, b) => a.title.localeCompare(b.title, "sv"));
        default:
            return sorted;
    }
}

function ListingPage() {
    const [listings, setListings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [visibleCount, setVisibleCount] = useState(PAGE_SIZE);
    const [sortBy, setSortBy] = useState("newest");
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        fetchListings()
            .then((data) => {
                setListings(data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

    const filteredListings = useMemo(() => {
        const term = searchTerm.trim().toLowerCase();
        if (!term) return listings;
        return listings.filter((listing) =>
            listing.title.toLowerCase().includes(term),
        );
    }, [listings, searchTerm]);

    const sortedListings = useMemo(
        () => sortListings(filteredListings, sortBy),
        [filteredListings, sortBy],
    );

    const visibleListings = sortedListings.slice(0, visibleCount);
    const hasMore = visibleCount < sortedListings.length;

    function handleShowMore() {
        setVisibleCount((prev) => prev + PAGE_SIZE);
    }

    function handleSortChange(event) {
        setSortBy(event.target.value);
        setVisibleCount(PAGE_SIZE);
    }

    function handleSearchChange(event) {
        setSearchTerm(event.target.value);
        setVisibleCount(PAGE_SIZE);
    }

    if (loading) {
        return <p className={styles.noResults}>Laddar annonser...</p>;
    }

    if (error) {
        return <p className={styles.noResults}>Något gick fel: {error}</p>;
    }

    return (
        <div className={styles.page}>
            <header className={styles.header}>
                <p className={styles.eyebrow}>Kalender</p>
                <h1 className={styles.heading}>Vad händer i stan?</h1>
                <p className={styles.subheading}>
                    Loppisar, föreningsmöten och fester — allt på ett ställe.
                </p>
            </header>

            <div className={styles.controls}>
                <input
                    type="text"
                    className={styles.searchInput}
                    placeholder="Sök annonser..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />

                <div className={styles.sortWrap}>
                    <label className={styles.sortLabel} htmlFor="sort">
                        Sortera efter
                    </label>
                    <select
                        id="sort"
                        className={styles.sortSelect}
                        value={sortBy}
                        onChange={handleSortChange}>
                        {SORT_OPTIONS.map((option) => (
                            <option key={option.value} value={option.value}>
                                {option.label}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            {sortedListings.length === 0 ? (
                <p className={styles.noResults}>
                    Inga annonser matchade "{searchTerm}".
                </p>
            ) : (
                <div className={styles.list}>
                    {visibleListings.map((listing) => (
                        <ListingCard key={listing.id} listing={listing} />
                    ))}
                </div>
            )}

            {hasMore && (
                <div className={styles.showMoreWrap}>
                    <button
                        type="button"
                        className={styles.showMoreButton}
                        onClick={handleShowMore}>
                        Visa fler annonser
                    </button>
                </div>
            )}
        </div>
    );
}

export default ListingPage;
