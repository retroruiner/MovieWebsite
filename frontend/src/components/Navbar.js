import React, { useState } from 'react';
import {Link} from 'react-router-dom';
import "../styles/Navbar.css";
import MovieLogo from "../assets/MovieLogo.png";

function Navbar() {
  const [showDropdown, setShowDropdown] = useState(false);

  const toggleDropdown = () => {
    setShowDropdown(!showDropdown);
  };
  return (
    <div>
       <div className='navbar'>
        <div className='leftSide'>
          <img src={MovieLogo}/>
        </div>
        <div className='rightSide'>
            <Link to = "/">Home</Link>
            <div className="dropdown" onMouseEnter={toggleDropdown} onMouseLeave={toggleDropdown}>
            <Link to="/catalog">Catalog</Link>
            {showDropdown && (
              <div className="dropdown-content">
                <Link to="/catalog/all-movies">All movies</Link>
                <Link to="/catalog/by-genre">By genre</Link>
              </div>
            )}
          </div>
            <input type="text" placeholder="Search..." />
            <Link to = "/login">Sign in</Link> 
            <Link to = "/register">Sign up</Link> 
        </div>
    </div>
    </div>

  )
}

export default Navbar
