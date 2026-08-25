import { apiFetch } from "./client";

export async function fetchListings() {
    const response = await apiFetch("/api/listings");
    return response.json();
}

export async function fetchListingById(id) {
    const response = await apiFetch(`/api/listings/${id}`);
    return response.json();
}

export async function fetchListingsByUser(userId) {
    const response = await apiFetch(`/api/listings/user/${userId}`);
    return response.json();
}

export async function fetchListingsByLocation(location) {
    const response = await apiFetch(`/api/listings/location/${location}`);
    return response.json();
}

export async function fetchListingsByPriceRange(min, max) {
    const response = await apiFetch(`/api/listings/price?min=${min}&max=${max}`);
    return response.json();
}

export async function createListing(dto) {
    const response = await apiFetch("/api/listings", {
        method: "POST",
        body: dto,
    });
    return response.json();
}

export async function updateListing(id, dto) {
    const response = await apiFetch(`/api/listings/${id}`, {
        method: "PUT",
        body: dto,
    });
    return response.json();
}

export async function deleteListing(id) {
    await apiFetch(`/api/listings/${id}`, { method: "DELETE" });
}