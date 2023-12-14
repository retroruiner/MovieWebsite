import React, { useState, useEffect } from 'react';
import { Link, useParams } from "react-router-dom";
import axios from 'axios';
import '../styles/ViewCollections.css'


export default function ViewCollections() {

    const [user, setUser] = useState({
        listOfCollections: []
    });

    const { id } = useParams();

    useEffect(() => {
        loadUser();
    }, []);

    const loadUser = async () => {
        const result = await axios.get(`http://localhost:8080/api/users/${id}`)
        setUser(result.data)
    }

    // const handleRemoveCollection = async (movieId) => {
    //     try {
    //       const authToken = localStorage.getItem("authToken");
    //       const response = await axios.delete(`http://localhost:8080/api/collections/removeMovie/${movieId}`, {
    //         data: {
    //           authToken: authToken,
    //           id: movieId,
    //           collectionName: collection.name
    //         }
    //       });
    
    //       // Handle the response, e.g., update the state or show a notification
    //       console.log('Movie removed:', response.data);
    //     } catch (error) {
    //       console.error('Error removing movie:', error);
    //     }
    //   };



    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h3 className="text-center m-4">List of collections</h3>

                    <div className="container">
                        <div className="row">
                            <div className="col-md-4 mb-3">
                                {user.listOfCollections.map((collection) => (
                                    <div key={collection.id}>
                                        <Link to={`/movies-in-collection/${collection.id}`}>{collection.name}</Link>
                                    </div>
                                ))}

                            </div>
                        </div>
                    </div>
                    <Link className="btn btn-primary my-2"
                        to={"/collections/create"}>Create new Collection
                    </Link>
                    <Link className="btn btn-outline-danger mx-2"
                        to={`/`}>Back
                    </Link>
                </div>
            </div>

        </div>

    )
}
