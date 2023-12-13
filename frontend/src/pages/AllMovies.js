import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import MovieItem from '../components/MovieItem';
import '../styles/AllMovies.css';


const AllMovies = () => {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/movies/findAll')
      .then(response => {
        setMovies(response.data);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, []);

  return (
    <div className='all_movies'>
      <h1>All movies in our catalog</h1>
      <div className='movieList'>
        {movies.map(movie => {
          return (
            <MovieItem
              id={movie.id}
              image={movie.image}
              title={movie.title}
              rating={movie.rating}
            />
          );
        })}
      </div>
      <Link className="btn btn-outline-danger mx-2"
        to={`/`}>Back
      </Link>
    </div>
  );
}

export default AllMovies;
