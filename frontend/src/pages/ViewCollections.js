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
                </div>
            </div>
                    
    </div>

  )
}
