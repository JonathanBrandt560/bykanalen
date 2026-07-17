const BASE_URL = "/auth"; // OBS: se kommentar om vite.config.js nedan

// Enkel wrapper runt fetch som automatiskt lägger på Authorization-header
// när ett token finns sparat, samt hanterar JSON-body/parsing.
export async function apiFetch(path, { method = "GET", body, auth = true } = {}) {
    const headers = { "Content-Type": "application/json" };

    if (auth) {
        const token = localStorage.getItem("token");
        if (token) {
            headers["Authorization"] = `Bearer ${token}`;
        }
    }

    const response = await fetch(path, {
        method,
        headers,
        body: body ? JSON.stringify(body) : undefined,
    });

    if (!response.ok) {
        // Backend (GlobalExceptionHandler) skickar ErrorResponse som JSON vid fel
        let message = "Något gick fel";
        try {
            const errorData = await response.json();
            message = errorData.message || message;
        } catch {
            // svaret var inte JSON, t.ex. vid 401 från JwtAuthEntryPoint
        }
        throw new Error(message);
    }

    return response;
}
