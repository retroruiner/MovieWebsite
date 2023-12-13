import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Genre.css';

function Genre() {
  return (
    <div className='genre-grid'>
      <h1>Select a Genre</h1>
      <div className='genre-buttons'>
        <Link to="/catalog/by-genre/action"><button><h1>Action</h1></button></Link>
        <Link to="/catalog/by-genre/comedy"><button><h1>Comedy</h1></button></Link>
        <Link to="/catalog/by-genre/drama"><button><h1>Drama</h1></button></Link>
        <Link to="/catalog/by-genre/thriller"><button><h1>Thriller</h1></button></Link>
        <Link to="/catalog/by-genre/sci-fi"><button><h1>Sci-Fi</h1></button></Link>
        <Link to="/catalog/by-genre/romance"><button><h1>Romance</h1></button></Link>
      </div>
    </div>
  );
}

export default Genre;
