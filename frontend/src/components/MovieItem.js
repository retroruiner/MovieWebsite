import React from 'react';
import StarImage from '../assets/star.png';
import '../styles/MovieItem.css'
import { Link } from "react-router-dom";


function MovieItem({ id, image, title, rating }) {
  console.log(id);
  return (
    <div className='movieItem'>

      <Link to={`/movies/${id}`}>
        <div><img src={image} width={150} height={200} /></div>
      </Link>
      <p>{title}</p>
      <div className='ratingContainer'>
        <p>{rating}</p>
        <img className='star' src={StarImage} alt='Star image' width={20} height={20} />
      </div>
    </div>
  )
}

export default MovieItem
