import { useParams, Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { getLatestGeneralPosts, getGeneralPostsByLikes, deleteGeneralPost } from "../../api/generalPostApi";
import GeneralPostCard from "./GeneralPostCard";
import styles from "./GeneralPostListPage.module.css";

const SORT_OPTIONS = [
    { value: "latest", label: "Senaste" },
    { value: "likes", label: "Flest gillningar" },
];

function GeneralPostListPage() {
    const { groupId } = useParams();
    const [posts, setPosts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [sortBy, setSortBy] = useState("latest");

    useEffect(() => {
        setLoading(true);
        setError(null);

        const request = sortBy === "likes"
            ? getGeneralPostsByLikes(groupId)
            : getLatestGeneralPosts(groupId);

        request
            .then(setPosts)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [groupId, sortBy]);

    async function handleDeletePost(postId) {
        if (!window.confirm("Vill du verkligen ta bort det här inlägget?")) return;
        try {
            await deleteGeneralPost(groupId, postId);
            setPosts((prev) => prev.filter((p) => p.id !== postId));
        } catch (err) {
            alert("Kunde inte ta bort inlägget: " + err.message);
        }
    }

    return (
        <div className={styles.page}>
            <header className={styles.header}>
                <p className={styles.eyebrow}>Inlägg</p>
                <h1 className={styles.heading}>Medlemsinlägg om allt mellan himmel och jord</h1>
            </header>

            <div className={styles.controls}>
                <Link to={`/groups/${groupId}/generalposts/new`} className={styles.addButton}>
                    + Nytt inlägg
                </Link>

                <div className={styles.sortWrap}>
                    <label className={styles.sortLabel} htmlFor="sort">
                        Sortera efter
                    </label>
                    <select
                        id="sort"
                        className={styles.sortSelect}
                        value={sortBy}
                        onChange={(e) => setSortBy(e.target.value)}
                    >
                        {SORT_OPTIONS.map((option) => (
                            <option key={option.value} value={option.value}>
                                {option.label}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            {loading && <p>Laddar inlägg...</p>}
            {error && <p>Något gick fel: {error}</p>}

            {!loading && !error && (
                <div className={styles.list}>
                    {posts.map((post) => (
                        <GeneralPostCard
                            key={post.id}
                            groupId={groupId}
                            generalPost={post}
                            onDelete={handleDeletePost}
                        />
                    ))}
                </div>
            )}
        </div>
    );
}

export default GeneralPostListPage;