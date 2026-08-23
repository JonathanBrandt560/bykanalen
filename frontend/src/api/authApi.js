import { apiFetch } from "./client";

export async function login(username, password) {
    const response = await apiFetch("/api/auth/login", {
        method: "POST",
        body: { username, password },
        auth: false,
    });
    return response.text();
}

export async function register(dto) {
    const response = await apiFetch("/api/auth/register", {
        method: "POST",
        body: dto,
        auth: false,
    });
    return response.text();
}

export async function getCurrentUser() {
    const response = await apiFetch("/api/auth/me");
    return response.json();
}