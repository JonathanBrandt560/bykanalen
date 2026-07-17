import { apiFetch } from "./client";

export async function login(username, password) {
    // auth: false — vi har ju inget token än när vi loggar in
    const response = await apiFetch("/auth/login", {
        method: "POST",
        body: { username, password },
        auth: false,
    });

    // AuthController.login returnerar token som ren text, inte JSON
    return response.text();
}

export async function register(dto) {
    const response = await apiFetch("/auth/register", {
        method: "POST",
        body: dto,
        auth: false,
    });

    return response.text();
}
