import { apiFetch } from "./client";

export async function getMyGroups() {
    const response = await apiFetch("/api/groups/mine");
    return response.json();
}

export async function getGroupInfo(groupId) {
    const response = await apiFetch(`/api/groups/${groupId}`);
    return response.json();
}