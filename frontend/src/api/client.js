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
        let message = "Något gick fel";
        let fieldErrors = null;
        try {
            const errorData = await response.json();
            message = errorData.message || message;
            fieldErrors = errorData.fieldErrors || null;
        } catch {
            // svaret var inte JSON, t.ex. vid 401 från JwtAuthEntryPoint
        }
        const error = new Error(message);
        error.fieldErrors = fieldErrors;
        throw error;
    }

    return response;
}