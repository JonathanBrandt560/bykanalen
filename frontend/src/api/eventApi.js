import { apiFetch } from "./client";

export async function getUpcomingEvents(groupId) {
    const response = await apiFetch(`/api/groups/${groupId}/events/upcoming`);
    return response.json();
}

export async function getEventById(groupId, eventId) {
    const response = await apiFetch(`/api/groups/${groupId}/events/${eventId}`);
    return response.json();
}

export async function createEvent(groupId, dto) {
    const response = await apiFetch(`/api/groups/${groupId}/events`, {
        method: "POST",
        body: dto,
    });
    return response.json();
}

export async function deleteEvent(groupId, eventId) {
    await apiFetch(`/api/groups/${groupId}/events/${eventId}`, { method: "DELETE" });
}

export async function getEventsAfterDate(groupId, date) {
    const response = await apiFetch(`/api/groups/${groupId}/events/after?date=${date}`);
    return response.json();
}

export async function getEventsBeforeDate(groupId, date) {
    const response = await apiFetch(`/api/groups/${groupId}/events/before?date=${date}`);
    return response.json();
}