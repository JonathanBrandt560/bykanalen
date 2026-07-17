import { useState } from "react";
import { getDayAndMonth } from "../../utils/dateHelpers";
import styles from "./GeneralPostCard.module.css";

function GeneralPostCard({ generalPost }) {
    const { day, month } = getDayAndMonth(generalPost.publishDate);

    const [hasLiked, setHasLiked] = useState(false);
    const [likeCount, setLikeCount] = useState(generalPost.likeCount);

    function handleToggleLike() {
        setHasLiked((prev) => !prev);
        setLikeCount((prev) => (hasLiked ? prev - 1 : prev + 1));
    }

    return (
        <article className={styles.card}>
            <h3 className={styles.title}>{generalPost.title}</h3>           
            <div className={styles.likes}>
                <button onClick={handleToggleLike} aria-pressed={hasLiked} className={styles.likeButton}>
                    <svg width="20" height="20" viewBox="0 0 24 24" fill={hasLiked ? "currentColor" : "none"} stroke="currentColor" strokeWidth="2">
                        <path d="M7 10v12M15 5.88 14 10h5.83a2 2 0 0 1 1.92 2.56l-2.33 8A2 2 0 0 1 17.5 22H4a2 2 0 0 1-2-2v-8a2 2 0 0 1 2-2h2.76a2 2 0 0 0 1.79-1.11L12 2a3.13 3.13 0 0 1 3 3.88Z" />
                    </svg>
                    <span>{likeCount}</span>
                </button>
            </div>

            <div className={styles.dateBlock}>
                <span className={styles.day}>{day}</span>
                <span className={styles.month}>{month}</span>
            </div>
        </article>
    );
}

export default GeneralPostCard;