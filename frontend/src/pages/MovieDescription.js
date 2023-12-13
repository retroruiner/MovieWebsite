import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import StarImage from '../assets/star.png';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import '../styles/MovieDescription.css'

const genreMapping = {
  'SCI_FI': 'SciFi',
  'DRAMA': 'Drama',
  'ACTION': 'Action',
  'COMEDY': 'Comedy',
  'ROMANCE': 'Romance',
  'THRILLER': 'Thriller',
};


function MovieDescription() {
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const [collections, setCollections] = useState([]);
  const authToken = localStorage.getItem("authToken");
  const userId = localStorage.getItem("userId");
  const isAuthenticated = authToken !== null;

  useEffect(() => {
    fetch(`http://localhost:8080/api/movies/${id}`)
      .then(response => response.json())
      .then(data => setMovie(data));

    fetch(`http://localhost:8080/api/collections/findAll/${userId}`)
      .then(response => response.json())
      .then(data => setCollections(data));
  }, [id]);

  if (!movie) {
    return <div>Loading...</div>;
  }

  const releaseDate = new Date(movie.releaseDate);
  const formattedReleaseDate = releaseDate.toISOString().substring(0, 10);
  const genres = movie.genreList;
  const formattedGenres = genres.map(genre => genreMapping[genre]).join(', ');

  const addMovieToCollection = (movieId, collectionName) => {
    console.log(movieId + collectionName)
    fetch(`http://localhost:8080/api/collections/addMovie/${movieId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        authToken: authToken,
        collectionName: collectionName,
      }),
    })
      .then(response => response.json())
      .then(data => console.log(data))
      .catch((error) => {
        console.error('Error:', error);
      });
  };

  return (
    <div className='movieDescription'>
      <div className='movieInfo'>
        <img src={movie.image} alt={movie.title} width={200} />
        <h1>{movie.title}</h1>
        <p>Release Date: {formattedReleaseDate}</p>
        <p>Rating: {movie.rating} <img className='star' src={StarImage} alt='Star image' width={20} height={20} /></p>
        <p>Director: {movie.director}</p>
        <p>Country of Origin: {movie.countryOfOrigin}</p>
        <p>Genre: {formattedGenres}</p>
        <Link className="btn btn-outline-danger mx-2"
          to={`/`}>Back
        </Link>
      </div>

      <div className='dropdownContainer'>
        {isAuthenticated ? (
          <DropdownButton id="dropdown-basic-button" title="Add to Collection">
            {collections.map(collection => (
              <Dropdown.Item onClick={() => addMovieToCollection(movie.id, collection.name)}>
                {collection.name}
              </Dropdown.Item>
            ))}
          </DropdownButton>
        ) : (
          <div></div>
        )}
      </div>
    </div>
  );
}

export default MovieDescription;
