import { apiFetch } from "./client";

export async function getServicesForGroup(groupId) {
    const response = await apiFetch(`/api/groups/${groupId}/services`);
    return response.json();
}

export async function createService(groupId, dto) {
    const response = await apiFetch(`/api/groups/${groupId}/services`, {
        method: "POST",
        body: dto,
    });
    return response.json();
}

export async function getServiceById(groupId, serviceId) {
    const response = await apiFetch(`/api/groups/${groupId}/services/${serviceId}`);
    return response.json();
}