import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import { getPostDate } from "../../utils/dateHelpers";
import { toggleLike } from "../../api/generalPostApi";
import styles from "./GeneralPostCard.module.css";

function GeneralPostCard({ generalPost, groupId, onDelete }) {
    const navigate = useNavigate();
    const { username } = useAuth();
    const date = getPostDate(generalPost.publishDate);
    const isOwnPost = generalPost.username === username;
    console.log("DEBUG:", { postUsername: generalPost.username, contextUsername: username, isOwnPost });

    const [hasLiked, setHasLiked] = useState(generalPost.likedByCurrentUser);
    const [likeCount, setLikeCount] = useState(generalPost.likeCount);
    const [isLoading, setIsLoading] = useState(false);

    async function handleToggleLike(e) {
        e.stopPropagation();
        if (isLoading) return;
        setIsLoading(true);

        const wasLiked = hasLiked;
        setHasLiked(!wasLiked);
        setLikeCount((prev) => (wasLiked ? prev - 1 : prev + 1));

        try {
            const result = await toggleLike(groupId, generalPost.id);
            setHasLiked(result.liked);
            setLikeCount(result.likeCount);
        } catch (err) {
            setHasLiked(wasLiked);
            setLikeCount((prev) => (wasLiked ? prev + 1 : prev - 1));
            console.error("Kunde inte uppdatera gillning:", err);
        } finally {
            setIsLoading(false);
        }
    }

    function handleDeleteClick(e) {
        e.stopPropagation();
        onDelete?.(generalPost.id);
    }

    function handleCardClick() {
        navigate(`/groups/${groupId}/generalposts/${generalPost.id}`);
    }

    function handleKeyDown(e) {
        if (e.key === "Enter") handleCardClick();
    }

    return (
        <article
            className={styles.card}
            onClick={handleCardClick}
            role="link"
            tabIndex={0}
            onKeyDown={handleKeyDown}
        >
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

            <div className={styles.metaRow}>
                <div className={styles.dateLine}>
                    <p className={styles.name}>
                        <span>Av </span>
                        <span>{generalPost.username}</span>
                    </p>
                    <p className={styles.date}>{date}</p>
                </div>

                {isOwnPost && (
                    <button onClick={handleDeleteClick} className={styles.deleteButton}>
                        Ta bort
                    </button>
                )}
            </div>
        </article>
    );
}

export default GeneralPostCard;