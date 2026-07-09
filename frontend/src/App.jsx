import { useState } from 'react';
import './App.css';
import { apiFetch, clearSession, getSession, saveSession } from './api';

function AuthForm({ mode, onSwitchMode, onAuthenticated }) {
  const [formData, setFormData] = useState({ name: '', email: '', password: '' });
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const isLogin = mode === 'login';

  const handleChange = (event) => {
    setFormData({ ...formData, [event.target.name]: event.target.value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage('');

    try {
      if (isLogin) {
        const session = await apiFetch('/api/auth/login', {
          method: 'POST',
          body: JSON.stringify({ email: formData.email, password: formData.password }),
        });
        saveSession(session);
        onAuthenticated(session);
        return;
      }

      await apiFetch('/api/auth/register', {
        method: 'POST',
        body: JSON.stringify(formData),
      });
      setMessage('Registration successful. You can log in now.');
      onSwitchMode('login');
    } catch (error) {
      setMessage(error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="auth-shell">
      <section className="auth-panel">
        <div>
          <p className="eyebrow">AI Catalog Assistant</p>
          <h1>{isLogin ? 'Welcome back' : 'Create your account'}</h1>
        </div>

        <form onSubmit={handleSubmit} className="form-stack">
          {!isLogin && (
            <label>
              Name
              <input name="name" type="text" value={formData.name} onChange={handleChange} required />
            </label>
          )}
          <label>
            Email
            <input name="email" type="email" value={formData.email} onChange={handleChange} required />
          </label>
          <label>
            Password
            <input name="password" type="password" value={formData.password} onChange={handleChange} minLength={8} required />
          </label>
          {message && <p className="notice">{message}</p>}
          <button type="submit" disabled={loading}>
            {loading ? 'Please wait...' : isLogin ? 'Log in' : 'Register'}
          </button>
        </form>

        <button className="link-button" type="button" onClick={() => onSwitchMode(isLogin ? 'register' : 'login')}>
          {isLogin ? 'Need an account? Register' : 'Already registered? Log in'}
        </button>
      </section>
    </main>
  );
}

function ProductGenerator({ session, onLogout }) {
  const [formData, setFormData] = useState({ name: '', description: '' });
  const [savedProduct, setSavedProduct] = useState(null);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (event) => {
    setFormData({ ...formData, [event.target.name]: event.target.value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setMessage('');

    try {
      const product = await apiFetch('/api/products/generate-and-save', {
        method: 'POST',
        body: JSON.stringify(formData),
      });
      setSavedProduct(product);
      setMessage('Product metadata generated and stored in PostgreSQL.');
    } catch (error) {
      setMessage(error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app-shell">
      <header className="topbar">
        <div>
          <p className="eyebrow">Signed in as {session.user.name}</p>
          <h1> Auto-Category & Tag Generator</h1>
        </div>
        <button type="button" className="secondary-button" onClick={onLogout}>Log out</button>
      </header>

      <main className="workspace">
        <section className="card form-card">
          <form onSubmit={handleSubmit} className="form-stack">
            <label>
              Product Name
              <input
                name="name"
                type="text"
                placeholder="type the product name here...."
                value={formData.name}
                onChange={handleChange}
                required
              />
            </label>
            <label>
              Description
              <textarea
                name="description"
                placeholder="write the product description here...."
                value={formData.description}
                onChange={handleChange}
                required
              />
            </label>
            <button type="submit" disabled={loading}>
              {loading ? 'Analyzing...' : 'Analyze & Save'}
            </button>
          </form>
          {message && <p className="notice">{message}</p>}
        </section>

        {savedProduct && (
          <section className="card result-card">
            <span className="db-badge">Stored in Neon PostgreSQL</span>
            <h2>Structured JSON Output</h2>

            <div className="metadata-grid">
              <div className="info-item">
                <small>Primary Category</small>
                <p>{savedProduct.category || 'N/A'}</p>
              </div>
              <div className="info-item">
                <small>Sub-Category</small>
                <p>{savedProduct.subCategory || 'N/A'}</p>
              </div>
            </div>

            <TagList title="SEO Tags" items={savedProduct.seoTags} variant="tag-pill" />
            <TagList title="Sustainability Filters" items={savedProduct.filters} variant="filter-pill" />

            <pre>{JSON.stringify(savedProduct.aiMetadata, null, 2)}</pre>
          </section>
        )}
      </main>
    </div>
  );
}

function TagList({ title, items = [], variant }) {
  return (
    <div className="tag-section">
      <strong>{title}</strong>
      <div className="tag-cloud">
        {items.map((item) => (
          <span key={item} className={variant}>{item}</span>
        ))}
      </div>
    </div>
  );
}

function App() {
  const [session, setSession] = useState(getSession());
  const [authMode, setAuthMode] = useState('login');

  const handleLogout = () => {
    clearSession();
    setSession(null);
    setAuthMode('login');
  };

  if (!session) {
    return <AuthForm mode={authMode} onSwitchMode={setAuthMode} onAuthenticated={setSession} />;
  }

  return <ProductGenerator session={session} onLogout={handleLogout} />;
}

export default App;
