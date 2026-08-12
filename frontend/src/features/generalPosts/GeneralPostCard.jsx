import { useState, useEffect } from "react";
import { getPostDate } from "../../utils/dateHelpers";
import styles from "./GeneralPostCard.module.css";

function GeneralPostCard({ generalPost, userId }) {
    const date = getPostDate(generalPost.publishDate);

    const [hasLiked, setHasLiked] = useState(false);
    const [likeCount, setLikeCount] = useState(generalPost.likeCount);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        async function fetchLikeStatus() {
            try {
                const res = await fetch(
                    `/api/general-posts/${generalPost.id}/like-status?userId=${userId}`
                );
                if (res.ok) {
                    setHasLiked(await res.json());
                }
            } catch (err) {
                console.error("Kunde inte hämta gilla-status:", err);
            }
        }
        fetchLikeStatus();
    }, [generalPost.id, userId]);

    async function handleToggleLike() {
        if (isLoading) return;
        setIsLoading(true);

        // Optimistisk uppdatering för snabb respons i UI:t
        const wasLiked = hasLiked;
        setHasLiked(!wasLiked);
        setLikeCount((prev) => (wasLiked ? prev - 1 : prev + 1));

        try {
            const res = await fetch(
                `/api/general-posts/${generalPost.id}/like?userId=${userId}`,
                { method: "POST" }
            );
            if (!res.ok) throw new Error("Serverfel vid gillning");

            const result = await res.json();
            setHasLiked(result.liked);
            setLikeCount(result.likeCount);
        } catch (err) {
            // Rulla tillbaka om anropet misslyckas
            setHasLiked(wasLiked);
            setLikeCount((prev) => (wasLiked ? prev + 1 : prev - 1));
            console.error("Kunde inte uppdatera gillning:", err);
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <article className={styles.card}>
            <div className={styles.likes}>
                <button
                    onClick={handleToggleLike}
                    disabled={isLoading}
                    aria-pressed={hasLiked}
                    className={styles.likeButton}
                >
                    <svg width="20" height="20" viewBox="0 0 24 24" fill={hasLiked ? "currentColor" : "none"} stroke="currentColor" strokeWidth="2">
                        <path d="M7 10v12M15 5.88 14 10h5.83a2 2 0 0 1 1.92 2.56l-2.33 8A2 2 0 0 1 17.5 22H4a2 2 0 0 1-2-2v-8a2 2 0 0 1 2-2h2.76a2 2 0 0 0 1.79-1.11L12 2a3.13 3.13 0 0 1 3 3.88Z" />
                    </svg>
                    <span>{likeCount}</span>
                </button>
            </div>

            <h3 className={styles.title}>{generalPost.title}</h3>

            <div className={styles.dateLine}>
                <p className={styles.name}>
                    <span>Av </span>
                    <span>{generalPost.name} </span>
                </p>
                <p className={styles.date}>{date}</p>
            </div>
        </article>
    );
}

export default GeneralPostCard;