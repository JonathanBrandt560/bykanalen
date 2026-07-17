const MONTHS_SHORT = [
    "JAN", "FEB", "MAR", "APR", "MAJ", "JUN",
    "JUL", "AUG", "SEP", "OKT", "NOV", "DEC",
];

// Plockar ut dagnummer + kort månadsnamn för "kalenderbladet" på ett event-kort.
// Tar emot en ISO-datumsträng, t.ex. "2026-08-15T18:00:00" (så som LocalDateTime serialiseras).
export function getDayAndMonth(isoDateString) {
    const date = new Date(isoDateString);
    return {
        day: date.getDate(),
        month: MONTHS_SHORT[date.getMonth()],
    };
}

// Formaterar start–slut som "18:00–21:00"
export function formatTimeRange(startIso, endIso) {
    const format = (iso) => {
        const date = new Date(iso);
        const hours = String(date.getHours()).padStart(2, "0");
        const minutes = String(date.getMinutes()).padStart(2, "0");
        return `${hours}:${minutes}`;
    };

    if (!endIso) return format(startIso);
    return `${format(startIso)}–${format(endIso)}`;
}
