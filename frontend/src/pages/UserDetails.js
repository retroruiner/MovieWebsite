import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from 'axios';

export default function UserDetails() {
    const [users, setUsers] = useState([]);

    const { id } = useParams();

    useEffect(() => {
        loadUsers();
    }, []);

    const loadUsers=async() => {
        const result = await axios.get("http://localhost:8080/api/users");
        //const result = await axios.get(`http://localhost:8080/api/user/${id}`);
        setUsers(result.data);
    }

  return (
    <div className="container">
      <div className="py-4">
      <table className="table border shadow">
        <thead>
            <tr>
            <th scope="col">ID</th>
            <th scope="col">FullName</th>
            <th scope="col">Nickname</th>
            <th scope="col">DateOfBirth</th>
            <th scope="col">Email</th>

            </tr>
        </thead>
        <tbody>
            {
                users.map((user, index) => (
                    <tr>
                    <th scope="row" key = {index}>{index+1}</th>
                    <td>{user.id}</td>
                    <td>{user.fullName}</td>
                    <td>{user.nickname}</td>
                    <td>{user.dateOfBirth}</td>
                    <td>{user.email}</td>
                    </tr>
                ))
            }
        </tbody>
        </table>
      </div>
    </div>
  )
}
