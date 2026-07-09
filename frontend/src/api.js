const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8081';
const SESSION_KEY = 'catalog_ai_session';

export function getSession() {
  const rawSession = localStorage.getItem(SESSION_KEY);
  return rawSession ? JSON.parse(rawSession) : null;
}

export function saveSession(session) {
  localStorage.setItem(SESSION_KEY, JSON.stringify(session));
}

export function clearSession() {
  localStorage.removeItem(SESSION_KEY);
}

export async function apiFetch(path, options = {}) {
  const session = getSession();
  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {}),
  };

  if (session?.token) {
    headers.Authorization = `Bearer ${session.token}`;
  }

  let response;
  try {
    response = await fetch(`${API_BASE_URL}${path}`, {
      ...options,
      headers,
    });
  } catch (error) {
    throw new Error(`Cannot reach backend at ${API_BASE_URL}. Start the Spring Boot server and check CORS settings.`);
  }

  const contentType = response.headers.get('content-type');
  const payload = contentType?.includes('application/json') ? await response.json() : null;

  if (!response.ok) {
    const message = payload?.message || 'Request failed';
    throw new Error(message);
  }

  return payload;
}
