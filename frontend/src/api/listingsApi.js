const BASE_URL = "http://localhost:8080";

export async function fetchListings() {
    const response = await fetch(`${BASE_URL}/listings`);
    if (!response.ok) {
        throw new Error(`Kunde inte hämta annonser: ${response.status}`);
    }
    return response.json();
}

export async function fetchListingById(id) {
    const response = await fetch(`${BASE_URL}/listings/${id}`);
    if (!response.ok) {
        throw new Error(`Kunde inte hämta annons: ${response.status}`);
    }
    return response.json();
}
