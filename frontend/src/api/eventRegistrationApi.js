import { apiFetch } from "./client";

export async function getRegistrationStatus(groupId, eventId) {
    const response = await apiFetch(`/api/groups/${groupId}/events/${eventId}/registration`);
    return response.json();
}

export async function registerForEvent(groupId, eventId) {
    await apiFetch(`/api/groups/${groupId}/events/${eventId}/registration`, { method: "POST" });
}

export async function unregisterFromEvent(groupId, eventId) {
    await apiFetch(`/api/groups/${groupId}/events/${eventId}/registration`, { method: "DELETE" });
}