import { mockGeneralPosts } from "./mockGeneralPosts";
import GeneralPostCard from "./GeneralPostCard";
import styles from "./GeneralPostListPage.module.css";

function GeneralPostListPage() {
    return (
        <div className={styles.page}>
            <header className={styles.header}>
                <p className={styles.eyebrow}>Inlägg</p>
                <h1 className={styles.heading}>Medlemsinlägg om allt mellan himmel och jord.</h1>
            </header>

            <div className={styles.list}>
                {mockGeneralPosts.map((generalPost) => (
                   <GeneralPostCard key={generalPost.id} generalPost={generalPost} /> 
                ))}
            </div>
        </div>
    );
}

export default GeneralPostListPage;