import { useState, useMemo } from "react";
import { mockListingPage } from "./mockServicePage";
import ListingCard from "./ServiceCard";
import styles from "./ServicePage.module.css";

const PAGE_SIZE = 20;

const SORT_OPTIONS = [
    { value: "newest", label: "Nyast först" },
    { value: "oldest", label: "Äldst först" },
    { value: "priceLow", label: "Pris: lägst först" },
    { value: "priceHigh", label: "Pris: högst först" },
    { value: "alphabetical", label: "Titel (A-Ö)" },
];

function getPriceNumber(priceString) {
    return parseInt(priceString.replace(/\D/g, ""), 10) || 0;
}

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
            return sorted.sort(
                (a, b) => getPriceNumber(a.price) - getPriceNumber(b.price),
            );
        case "priceHigh":
            return sorted.sort(
                (a, b) => getPriceNumber(b.price) - getPriceNumber(a.price),
            );
        case "alphabetical":
            return sorted.sort((a, b) => a.title.localeCompare(b.title, "sv"));
        default:
            return sorted;
    }
}

function ServicePage() {
    const [visibleCount, setVisibleCount] = useState(PAGE_SIZE);
    const [sortBy, setSortBy] = useState("newest");
    const [searchTerm, setSearchTerm] = useState("");

    const filteredServices = useMemo(() => {
        const term = searchTerm.trim().toLowerCase();
        if (!term) return mockServicePage;
        return mockServicePage.filter((Service) =>
            Service.title.toLowerCase().includes(term),
        );
    }, [searchTerm]);

    const sortedServices = useMemo(
        () => sortListings(filteredServices, sortBy),
        [filteredServices, sortBy],
    );

    const visibleServices = sortedServices.slice(0, visibleCount);
    const hasMore = visibleCount < sortedServices.length;

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

            {sortedServices.length === 0 ? (
                <p className={styles.noResults}>
                    Inga annonser matchade "{searchTerm}".
                </p>
            ) : (
                <div className={styles.list}>
                    {visibleServices.map((Service) => (
                        <ServiceCard key={Service.id} Service={Service} />
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

export default ServicePage;
