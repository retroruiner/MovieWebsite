import React, { useState } from "react";
import { Link, useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

export default function CreateCollection() {
    let navigate = useNavigate();
    //const { id } = useParams();
  const [newCollectionName, setNewCollectionName] = useState('');
  const userId = localStorage.getItem("userId");
  console.log('UserId received in "Create Collection" page', userId);

  const createCollection = async (e) => {
    e.preventDefault();

    try {
      const authToken = localStorage.getItem("authToken");
      const response = await axios.post('http://localhost:8080/api/collections/createCollection', {
        authToken: authToken,
        collectionName: newCollectionName,
      });  //TODO: copy this part to other Login page and delete the LoginResponse class

      console.log('New collection created:', response.data);

      navigate(`/collections/${userId}`);
    } catch (error) {
      console.error('Error creating collection:', error);
    }
  };

  return (
    <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h3 className="text-center m-4">Create a New Collection</h3>

                    <form onSubmit={(e) => createCollection(e)}>
                        <div className="mb-3">

                            <label htmlFor="collectionName">Collection Name:  </label>
                            <input
                                type="text" required
                                id="collectionName"
                                value={newCollectionName}
                                onChange={(e) => setNewCollectionName(e.target.value)}
                            />
                        </div>

                        <button type="submit" className="btn btn-outline-primary">
                            Create
                        </button>
                        <Link className="btn btn-outline-danger mx-2"
                            to={`collections/${userId}`} replace>
                                Cancel
                        </Link>
                            {/* <button className="btn btn-primary my-2"
                                onClick={createCollection}>Create
                            </button>
                             */}
                    

                    </form>

                </div>
            </div>           
    </div>
  );
}
