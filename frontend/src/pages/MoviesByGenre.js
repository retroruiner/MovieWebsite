import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import MovieItem from '../components/MovieItem';
import { useParams } from 'react-router-dom';
import '../styles/MoviesByGenre.css'; 

function MoviesByGenre() {
  const { genre } = useParams(); 
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:8080/api/movies/filterByGenre?genreName=${genre}`) 
      .then(response => {
        setMovies(response.data);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, [genre]);

  return (
    <div className='movies_by_genre'>
      <h1>Movies for {genre}</h1>
      <div className='movieList'>
        {movies.map(movie => (
          <MovieItem
            key={movie.id}
            id={movie.id}
            image={movie.image}
            title={movie.title}
            rating={movie.rating}
          />
        ))}
      </div>
      <Link className="btn btn-outline-danger mx-2"
        to={`/catalog/by-genre`}>Back
      </Link>
    </div>
  );
}

export default MoviesByGenre;
