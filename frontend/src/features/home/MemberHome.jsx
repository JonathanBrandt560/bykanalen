import { useState, useEffect } from "react";
import { useAuth } from "../../context/AuthContext";
import { getGroupInfo } from "../../api/groupApi";
import styles from "./MemberHome.module.css";

function MemberHome() {
    const { groups, activeGroupId, switchGroup } = useAuth();
    const activeGroup = groups.find((g) => String(g.id) === String(activeGroupId));

    const [groupInfo, setGroupInfo] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!activeGroupId) return;

        setLoading(true);
        setError(null);

        getGroupInfo(activeGroupId)
            .then(setGroupInfo)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [activeGroupId]);

    if (groups.length === 0) {
        return (
            <div className={styles.page}>
                <p className={styles.loading}>Hämtar dina byar…</p>
            </div>
        );
    }

    return (
        <div className={styles.page}>
            <header className={styles.header}>
                <p className={styles.eyebrow}>Välkommen tillbaka</p>
                <h1 className={styles.heading}>
                    {activeGroup ? activeGroup.groupName : "Bykanalen"}
                </h1>

                {groups.length > 1 && (
                    <div className={styles.groupSwitch}>
                        <span>Byt by:</span>
                        <select
                            value={activeGroupId ?? ""}
                            onChange={(e) => switchGroup(e.target.value)}
                            className={styles.groupSelect}
                        >
                            {groups.map((g) => (
                                <option key={g.id} value={g.id}>{g.groupName}</option>
                            ))}
                        </select>
                    </div>
                )}
            </header>

            {loading && <p className={styles.loading}>Hämtar information om byn…</p>}
            {error && <p className={styles.error}>Något gick fel: {error}</p>}

            {groupInfo && (
                <article className={styles.notice}>
                    <span className={styles.pin} aria-hidden="true" />

                    {groupInfo.image1 && (
                        <img
                            className={styles.heroImage}
                            src={`data:image/jpeg;base64,${groupInfo.image1}`}
                            alt=""
                        />
                    )}

                    {groupInfo.text1 && <p className={styles.introText}>{groupInfo.text1}</p>}

                    <div className={styles.columns}>
                        {groupInfo.text2 && (
                            <div className={styles.column}>
                                {groupInfo.image2 && (
                                    <img
                                        className={styles.columnImage}
                                        src={`data:image/jpeg;base64,${groupInfo.image2}`}
                                        alt=""
                                    />
                                )}
                                <p>{groupInfo.text2}</p>
                            </div>
                        )}
                        {groupInfo.text3 && (
                            <div className={styles.column}>
                                {groupInfo.image3 && (
                                    <img
                                        className={styles.columnImage}
                                        src={`data:image/jpeg;base64,${groupInfo.image3}`}
                                        alt=""
                                    />
                                )}
                                <p>{groupInfo.text3}</p>
                            </div>
                        )}
                    </div>
                </article>
            )}
        </div>
    );
}

export default MemberHome;