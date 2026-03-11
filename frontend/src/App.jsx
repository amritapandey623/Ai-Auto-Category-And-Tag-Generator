import React, { useState } from 'react';
import './App.css';

function App() {
  const [formData, setFormData] = useState({ name: '', description: '' });
  const [savedProduct, setSavedProduct] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await fetch('http://localhost:8080/api/products/generate-and-save', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });

      if (!response.ok) throw new Error("Server error");

      const data = await response.json();
      setSavedProduct(data);
      alert("Success: Product & AI Metadata Stored!");
    } catch (err) {
      console.error("Error:", err);
      alert("Backend Connection Failed. Is Spring Boot running on port 8080?");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
     

      <main className="content">
        {  }
        <div className="card form-card">
          <form onSubmit={handleSubmit}>
            <div className="input-group">
              <label>Product Name</label>
              <input 
                type="text"
                placeholder="Enter Product Name" 
                onChange={e => setFormData({...formData, name: e.target.value})} 
                required 
              />
            </div>
            <div className="input-group">
              <label>Description</label>
              <textarea 
                placeholder="Enter description for AI analysis..." 
                onChange={e => setFormData({...formData, description: e.target.value})} 
                required 
              />
            </div>
            <button type="submit" className="submit-btn" disabled={loading}>
              {loading ? <span className="loader"></span> : "Analyze & Save to Database"}
            </button>
          </form>
        </div>

        { }
        {savedProduct && (
          <div className="card result-card">
            <div className="result-header">
              <span className="db-badge">Stored in MongoDB</span>
              <h2>AI Generation Result</h2>
            </div>
            
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

            <div className="tag-section">
              <strong>SEO Keywords:</strong>
              <div className="tag-cloud">
                {savedProduct.seoTags?.map((tag, i) => (
                  <span key={i} className="tag-pill">{tag}</span>
                ))}
              </div>
            </div>

            <div className="tag-section">
              <strong>Sustainability Filters:</strong>
              <div className="tag-cloud">
                {savedProduct.filters?.map((f, i) => (
                  <span key={i} className="filter-pill">{f}</span>
                ))}
              </div>
            </div>
          </div>
        )}
      </main>
    </div>
  );
}

export default App;