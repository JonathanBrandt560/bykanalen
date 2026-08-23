import { apiFetch } from "./client";

export async function getLatestGeneralPosts(groupId) {
    const response = await apiFetch(`/api/groups/${groupId}/generalposts/orderlatest`);
    return response.json();
}

export async function toggleLike(groupId, postId) {
    const response = await apiFetch(`/api/groups/${groupId}/generalposts/${postId}/like`, {
        method: "POST",
    });
    return response.json();
}

export async function createGeneralPost(groupId, dto) {
    const response = await apiFetch(`/api/groups/${groupId}/generalposts`, {
        method: "POST",
        body: dto,
    });
    return response.json();
}

export async function getGeneralPostById(groupId, postId) {
    const response = await apiFetch(`/api/groups/${groupId}/generalposts/${postId}`);
    return response.json();
}

export async function deleteGeneralPost(groupId, postId) {
    await apiFetch(`/api/groups/${groupId}/generalposts/${postId}`, { method: "DELETE" });
}

export async function patchGeneralPost(groupId, postId, dto) {
    const response = await apiFetch(`/api/groups/${groupId}/generalposts/${postId}`, {
        method: "PATCH",
        body: dto,
    });
    return response.json();
}

export async function getGeneralPostsByLikes(groupId) {
    const response = await apiFetch(`/api/groups/${groupId}/generalposts/orderlikes`);
    return response.json();
}