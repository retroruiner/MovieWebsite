import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import "../styles/Navbar.css";
import MovieLogo from "../assets/MovieLogo.png";
import axios from "axios";

function Navbar() {
  let navigate = useNavigate();
  const [showDropdown, setShowDropdown] = useState(false);
  const [showSearchDropdown, setSearchDropdown] = useState(false);
  const [searchText, setSearchText] = useState("");
  const [searchResults, setSearchResults] = useState([]);

  const authToken = localStorage.getItem("authToken");
  const userId = localStorage.getItem("userId");
  console.log("AuthToken in Navbar: ", authToken);
  console.log("User in Navbar: ", authToken);

  const isAuthenticated = authToken !== null;

  const toggleDropdown = () => {
    setShowDropdown(!showDropdown);
  };


  const onLogout = async () => {
    try {
      await axios.post("http://localhost:8080/api/users/logout", { authToken });
      localStorage.removeItem("authToken");
    } catch (error) {
      console.error("Logout failed", error);
    }
  };

  const handleSearchChange = (e) => {
    setSearchText(e.target.value);
  };

  useEffect(() => {
    if (searchText) {
      axios.get(`http://localhost:8080/api/movies/simpleSearch?searchTerm=${searchText}`)
        .then(response => {
          setSearchResults(response.data);
        })
        .catch(error => {
          console.error('Error fetching data:', error);
        });
    } else {
      setSearchResults([]);
    }
  }, [searchText]);

  return (
    <div>
      <div className='navbar'>
        <div className='leftSide'>
          <Link to="/">
            <img src={MovieLogo} alt="Movie Logo" />
          </Link>
        </div>
        <div className='rightSide'>
          <Link to="/">Home</Link>
          <div className="dropdown" onMouseEnter={toggleDropdown} onMouseLeave={toggleDropdown}>
            <Link to="/catalog">Catalog</Link>
            {showDropdown && (
              <div className="dropdown-content">
                <Link to="/catalog/all-movies">All movies</Link>
                <Link to="/catalog/by-genre">By genre</Link>
              </div>
            )}
          </div>
          <div className='search'>
            <input type="text" placeholder="Find a movie..." value={searchText} onChange={handleSearchChange} />
            {searchResults.length > 0 && (
              <div className='search-content'>
                {searchResults.map(movie => (
                  <div key={movie.id} className='movie-item-search'>
                    <Link to={`/movies/${movie.id}`}>
                      <div className="movie-info">
                        <img src={movie.image} alt={movie.title} width={50} height={80} />
                        <p>{movie.title}</p>
                      </div>
                    </Link>
                  </div>
                ))}
              </div>
            )}
          </div>

          {isAuthenticated ? (
            <div>
              {userId ? (
                <Link to={`/viewProfile/${userId}`}>Profile</Link>
              ) : (
                <span>User ID not available</span>
              )}
              <Link to={`/collections/${userId}`}>My Collections</Link>

              <Link to="/" onClick={onLogout}>Logout</Link>

            </div>
          ) : (
            <Link to="/login">Sign in</Link>
          )}
        </div>
      </div>
    </div>

  )
}

export default Navbar
