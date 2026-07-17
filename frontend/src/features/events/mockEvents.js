// Tillfällig mockdata i samma form som backendens EventSummaryDTO
// (id, title, image, startDate, endDate). Byts ut mot ett riktigt
// API-anrop (eventApi.getUpcomingEvents) när kopplingen mot backend görs.
export const mockEvents = [
    {
        id: 1,
        title: "Loppis på Stortorget",
        image: null,
        startDate: "2026-07-18T10:00:00",
        endDate: "2026-07-18T14:00:00",
    },
    {
        id: 2,
        title: "Midsommarfirande vid Bygdegården",
        image: null,
        startDate: "2026-07-20T13:00:00",
        endDate: "2026-07-20T23:00:00",
    },
    {
        id: 3,
        title: "Konstutställning: Lokala konstnärer",
        image: null,
        startDate: "2026-07-25T11:00:00",
        endDate: "2026-08-02T17:00:00",
    },
    {
        id: 4,
        title: "Boule-kväll för alla åldrar",
        image: null,
        startDate: "2026-07-28T18:00:00",
        endDate: "2026-07-28T20:00:00",
    },
    {
        id: 5,
        title: "Hembygdsföreningens årsmöte",
        image: null,
        startDate: "2026-08-05T19:00:00",
        endDate: "2026-08-05T21:00:00",
    },
    {
        id: 6,
        title: "Julmarknad på Torget",
        image: null,
        startDate: "2026-12-06T11:00:00",
        endDate: "2026-12-06T16:00:00",
    },
];
