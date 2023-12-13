import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function ViewUser() {
    let navigate = useNavigate();


  const [user, setUser] = useState({
    fullName: "",
    nickname: "",
    email: "",
    dateOfBirth: ""
  });

  const { id } = useParams();

  useEffect(() => {
    loadUser();
  }, []);

  const loadUser = async () => {
    const result = await axios.get(`http://localhost:8080/api/users/${id}`)  //find user by id
    setUser(result.data)
  }

  const deleteUser = async (id) => {
    await axios.delete(`http://localhost:8080/api/users/${id}`);
    localStorage.clear();
    navigate("/");
  };

  const dob = new Date(user.dateOfBirth);
  const formattedDob = dob.toISOString().substring(0, 10);

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">User Details</h2>

          <div className="card">
            <div className="card-header">
              Details of user : {user.nickname}
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <b>Full Name:  </b>
                  {user.fullName}
                </li>
                <li className="list-group-item">
                  <b>Email:  </b>
                  {user.email}
                </li>
                <li className="list-group-item">
                  <b>Date of Birth:  </b>
                  {formattedDob}
                </li>
              </ul>
            </div>
          </div>
          <Link className="btn btn-primary my-2" to={"/"}>
            Back to Home
          </Link>
          <Link className="btn btn-primary my-2"
            to={`/editUser/${user.id}`}>
            Edit profile
          </Link>

          <button className="btn btn-danger mx-2" onClick={() => deleteUser(user.id)}>
            Delete account
          </button>
        </div>
      </div>
    </div>
  );
}