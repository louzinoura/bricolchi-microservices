import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext'; // Use the named export
import { useNavigate } from 'react-router-dom';
import './Annonce.css';


const Annonce = () => {
  const { token, user } = useAuth(); // Get token and user from useAuth
  const navigate = useNavigate();
  
  const userId = user?.id; // Extract user ID from user object
  
  // State variables
  const [annonces, setAnnonces] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showForm, setShowForm] = useState(false);
  
  // Form state
  const [formData, setFormData] = useState({
    titre: '',
    description: '',
    categorie: '',
    prix: '',
    localisation: '',
    adresse: '',
    images: '',
    tags: '',
    latitude: '',
    longitude: ''
  });

  // Fetch announcements and categories
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch announcements
        const annonceResponse = await fetch('http://localhost:8082/api/annonce');
        const annonceData = await annonceResponse.json();
        setAnnonces(annonceData.content);
        
        // Fetch categories
        const categorieResponse = await fetch('http://localhost:8082/api/annonce/categories');
        const categorieData = await categorieResponse.json();
        setCategories(categorieData);
        
        setLoading(false);
      } catch (err) {
        setError('Failed to load data');
        setLoading(false);
      }
    };
    
    fetchData();
  }, []);

  // Handle form input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!token) {
      setError('You must be logged in to create an announcement');
      return;
    }
    
    try {
      const response = await fetch('http://localhost:8080/api/annonce', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          ...formData,
          userId: userId,
          userName: user?.username || '',
          userEmail: user?.email || '',
          images: formData.images.split(',').filter(url => url.trim()),
          tags: formData.tags.split(',').filter(tag => tag.trim())
        })
      });
      
      if (response.ok) {
        const newAnnonce = await response.json();
        setAnnonces([newAnnonce, ...annonces]);
        setShowForm(false);
        resetForm();
      } else {
        const errorData = await response.json();
        setError(errorData.message || 'Failed to create announcement');
      }
    } catch (err) {
      setError('Network error');
    }
  };

  // Reset form
  const resetForm = () => {
    setFormData({
      titre: '',
      description: '',
      categorie: '',
      prix: '',
      localisation: '',
      adresse: '',
      images: '',
      tags: '',
      latitude: '',
      longitude: ''
    });
  };

  // View announcement details
  const viewDetails = (id) => {
    navigate(`/annonce/${id}`);
  };

  if (loading) return <div className="text-center mt-5">Loading...</div>;
  if (error) return <div className="alert alert-danger">{error}</div>;

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between mb-4">
        <h2>Announcements</h2>
        {token && (
          <button 
            className="btn btn-primary"
            onClick={() => setShowForm(!showForm)}
          >
            {showForm ? 'Cancel' : 'Create New'}
          </button>
        )}
      </div>

      {/* Create Announcement Form */}
      {showForm && (
        <div className="card mb-4">
          <div className="card-body">
            <h4 className="card-title">Create New Announcement</h4>
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Title*</label>
                <input 
                  type="text" 
                  className="form-control"
                  name="titre"
                  value={formData.titre}
                  onChange={handleChange}
                  required
                />
              </div>
              
              <div className="mb-3">
                <label className="form-label">Description</label>
                <textarea 
                  className="form-control"
                  name="description"
                  value={formData.description}
                  onChange={handleChange}
                />
              </div>
              
              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Category*</label>
                  <select 
                    className="form-select"
                    name="categorie"
                    value={formData.categorie}
                    onChange={handleChange}
                    required
                  >
                    <option value="">Select a category</option>
                    {categories.map(cat => (
                      <option key={cat} value={cat}>{cat}</option>
                    ))}
                  </select>
                </div>
                
                <div className="col-md-6 mb-3">
                  <label className="form-label">Price*</label>
                  <input 
                    type="number" 
                    className="form-control"
                    name="prix"
                    value={formData.prix}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>
              
              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Location*</label>
                  <input 
                    type="text" 
                    className="form-control"
                    name="localisation"
                    value={formData.localisation}
                    onChange={handleChange}
                    required
                  />
                </div>
                
                <div className="col-md-6 mb-3">
                  <label className="form-label">Address</label>
                  <input 
                    type="text" 
                    className="form-control"
                    name="adresse"
                    value={formData.adresse}
                    onChange={handleChange}
                  />
                </div>
              </div>
              
              <div className="mb-3">
                <label className="form-label">Image URLs (comma separated)</label>
                <input 
                  type="text" 
                  className="form-control"
                  name="images"
                  value={formData.images}
                  onChange={handleChange}
                  placeholder="https://example.com/image1.jpg, https://example.com/image2.jpg"
                />
              </div>
              
              <div className="mb-3">
                <label className="form-label">Tags (comma separated)</label>
                <input 
                  type="text" 
                  className="form-control"
                  name="tags"
                  value={formData.tags}
                  onChange={handleChange}
                  placeholder="tag1, tag2, tag3"
                />
              </div>
              
              <div className="d-grid">
                <button type="submit" className="btn btn-success">
                  Create Announcement
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Announcements List */}
      <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        {annonces.length > 0 ? (
          annonces.map(annonce => (
            <div key={annonce.id} className="col">
              <div className="card h-100">
                {annonce.images?.length > 0 && (
                  <img 
                    src={annonce.images[0]} 
                    className="card-img-top"
                    alt={annonce.titre}
                    style={{ height: '200px', objectFit: 'cover' }}
                  />
                )}
                <div className="card-body">
                  <h5 className="card-title">{annonce.titre}</h5>
                  <p className="card-text text-muted">{annonce.categorie}</p>
                  <p className="card-text">{annonce.description.substring(0, 100)}...</p>
                  <div className="d-flex justify-content-between align-items-center">
                    <strong className="text-primary">{annonce.prix} €</strong>
                    <button 
                      className="btn btn-sm btn-outline-secondary"
                      onClick={() => viewDetails(annonce.id)}
                    >
                      Details
                    </button>
                  </div>
                </div>
                <div className="card-footer text-muted">
                  <small>{annonce.localisation}</small>
                </div>
              </div>
            </div>
          ))
        ) : (
          <div className="col-12 text-center py-5">
            <h4>No announcements found</h4>
            <p>Be the first to create one!</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default Annonce;