import React, { useState, useEffect } from 'react';
import { Link, useParams } from "react-router-dom";
import axios from 'axios';
import MovieItem from '../components/MovieItem';
import '../styles/AllMovies.css';


const ViewCollection = () => {
    const [collection, setCollection] = useState({
        movies: [],
        name: ""
      });
    
      const { id } = useParams();
    
      useEffect(() => {
        loadCollection();
      }, []);
    
      const loadCollection = async () => {
        const result = await axios.get(`http://localhost:8080/api/collections/${id}`)  //find collection by id
        setCollection(result.data)
      }

      const handleRemoveMovie = async (movieId) => {
        try {
            const authToken = localStorage.getItem("authToken");
            const response = await axios.delete(`http://localhost:8080/api/collections/removeMovie/${movieId}`, {
                data: {
                    authToken: authToken,
                    id: movieId,
                    collectionName: collection.name
                }
            });

            // Handle the response, e.g., update the state or show a notification
            console.log('Movie removed:', response.data);
        } catch (error) {
            console.error('Error removing movie:', error);
        }
    };

  return (
    <div className='all_movies'>
      <h1>In collection:</h1>
      <div className='movieList'>
        {collection.movies.map(movie => (
            <div key={movie.id} className="movie">
                <MovieItem
                id={movie.id}
                image={movie.image}
                title={movie.title}
                rating={movie.rating}
                />
                {/* Delete button for each MovieItem */}
                <button
                className="btn btn-outline-danger btn-sm"
                onClick={() => handleRemoveMovie(movie.id)}
                >
                Delete
                </button>
            </div>
        ))}
      </div>
        <Link className="btn btn-outline-danger mx-2"
            to={`collection/${userId}`} replace>
                Back
        </Link>
    </div>
  );
}

export default ViewCollection;
