import { useParams, Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { getGeneralPostById } from "../../api/generalPostApi";
import { useAuth } from "../../context/AuthContext";
import { getPostDate } from "../../utils/dateHelpers";
import styles from "./GeneralPostDetailPage.module.css";

function GeneralPostDetailPage() {
    const { groupId, id } = useParams();
    const { username } = useAuth();
    const [post, setPost] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        getGeneralPostById(groupId, id)
            .then(setPost)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [groupId, id]);

    if (loading) return <div className={styles.page}>Laddar...</div>;

    if (error || !post) {
        return (
            <div className={styles.page}>
                <p>Inlägget hittades inte.</p>
                <Link to={`/groups/${groupId}/generalposts`}>Tillbaka till anslagstavlan</Link>
            </div>
        );
    }

    const isOwnPost = post.username === username;

    return (
        <div className={styles.page}>
            <Link to={`/groups/${groupId}/generalposts`} className={styles.back}>
                ← Tillbaka
            </Link>

            <article className={styles.card}>
                {post.image && (
                    <div className={styles.image}>
                        <img src={`data:image/jpeg;base64,${post.image}`} alt="" />
                    </div>
                )}

                <h1 className={styles.title}>{post.title}</h1>

                <div className={styles.meta}>
                    <span>Av {post.username}</span>
                    <span>{getPostDate(post.publishDate)}</span>
                </div>

                <p className={styles.description}>{post.description}</p>

                <p className={styles.likeCount}>{post.likeCount} gillningar</p>

                {isOwnPost && (
                    <Link to={`/groups/${groupId}/generalposts/${id}/edit`} className={styles.editLink}>
                        Redigera inlägg
                    </Link>
                )}
            </article>
        </div>
    );
}

export default GeneralPostDetailPage;