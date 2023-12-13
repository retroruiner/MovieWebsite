import React from 'react';
import { Link } from 'react-router-dom';
import HomePageImage from '../assets/HomePageImage.png'
import "../styles/Home.css";


function Home() {
  return (
    <div className='home' style={{ backgroundImage: `url(${HomePageImage})` }}>
      <div className='headerContainer'>
        <h1>Welcome to Movie Website</h1>
        <p>This is a simple website created with ReactJS. Enjoy exploring!</p>
        <Link to="/catalog/all-movies">
          <button>Browse movies</button>
        </Link>
      </div>


    </div>
  )
}

export default Home
