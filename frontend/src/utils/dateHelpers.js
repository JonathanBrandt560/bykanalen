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

const MONTHS_FULL = [
    "januari", "februari", "mars", "april", "maj", "juni",
    "juli", "augusti", "september", "oktober", "november", "december",
];

// Formaterar till "18 juli 2026 · 18:00–21:00", för detaljvyer
export function formatFullDateTimeRange(startIso, endIso) {
    const start = new Date(startIso);
    const dateStr = `${start.getDate()} ${MONTHS_FULL[start.getMonth()]} ${start.getFullYear()}`;
    return `${dateStr} · ${formatTimeRange(startIso, endIso)}`;
}

// Returnerar t.ex. "Juli 2026", för sektionsrubriker
export function getMonthYearLabel(isoDateString) {
    const date = new Date(isoDateString);
    const label = `${MONTHS_FULL[date.getMonth()]} ${date.getFullYear()}`;
    return label.charAt(0).toUpperCase() + label.slice(1);
}

// Formaterar till "15 juli 2026", för t.ex. sista anmälningsdag
export function formatFullDate(isoDateString) {
    const date = new Date(isoDateString);
    return `${date.getDate()} ${MONTHS_FULL[date.getMonth()]} ${date.getFullYear()}`;
}

// Nyckel för att avgöra var en ny grupp börjar, t.ex. "2026-06"
function getMonthYearKey(isoDateString) {
    const date = new Date(isoDateString);
    return `${date.getFullYear()}-${String(date.getMonth()).padStart(2, "0")}`;
}

// Grupperar en redan sorterad lista av objekt (t.ex. events) efter månad/år.
// Antar att listan redan är sorterad kronologiskt (vilket EventController redan garanterar).
export function groupByMonth(items, dateField = "startDate") {
    const groups = [];
    let currentKey = null;

    for (const item of items) {
        const key = getMonthYearKey(item[dateField]);
        if (key !== currentKey) {
            groups.push({ key, label: getMonthYearLabel(item[dateField]), items: [] });
            currentKey = key;
        }
        groups[groups.length - 1].items.push(item);
    }

    return groups;
}

