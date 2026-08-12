import { isToday, isYesterday, formatDistanceToNow } from "date-fns";
import { sv } from "date-fns/locale";

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

// Formaterar en ISO-datumsträng till "idag/igår + klockslag" eller annars till "YYYY-MM-DD"
export function getPostDate(isoDateString) {
    const date = new Date(isoDateString);
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    
    if (isToday(date)) {
        return `idag ${hours}:${minutes}`;
    } else if (isYesterday(date)) {
        return `igår ${hours}:${minutes}`;
    } else {
        return isoDateString.split("T")[0];
    }
}

