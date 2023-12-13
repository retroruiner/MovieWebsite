import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function AddUser() {
    let navigate = useNavigate(); //To jump to another pages

    const [user, setUser] = useState({
        fullName: "",
        nickname: "",
        email: "",
        dateOfBirth: "",
        password: ""
    })

    const { fullName, nickname, email, dateOfBirth, password } = user

    const onInputChange = (e) => {
        setUser({ ...user, [e.target.name]: e.target.value }); //keep on adding the user in state
    };

    const onSubmit = async (e) => {
        e.preventDefault();  //prevent wierd url
        await axios.post("http://localhost:8080/api/users/register", user);

        navigate("/");  //Home
    };

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h2 className="text-center m-4">Register User</h2>

                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className="mb-3">
                            <label htmlFor="FullName" className="form-label">
                                Name
                            </label>
                            <input
                                type={"text"}
                                className="form-control"
                                placeholder="Enter your full name"
                                name="fullName"
                                value={fullName}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="Nickname" className="form-label">
                                Nickname
                            </label>
                            <input
                                type={"text"}
                                className="form-control"
                                placeholder="Create your nickname"
                                name="nickname"
                                value={nickname}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="Email" className="form-label">
                                Email
                            </label>
                            <input
                                type={"text"}
                                className="form-control"
                                placeholder="Enter your email address"
                                name="email"
                                value={email}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="DateOfBirth" className="form-label">
                                Date of Birth
                            </label>
                            <input
                                type="date"
                                className="form-control"
                                placeholder="Enter your date of birth"
                                name="dateOfBirth"
                                value={dateOfBirth}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="Password" className="form-label">
                                Password
                            </label>
                            <input
                                type={"text"}
                                className="form-control"
                                placeholder="Create a password"
                                name="password"
                                value={password}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <button type="submit" className="btn btn-outline-primary">
                            Submit
                        </button>
                        <Link className="btn btn-outline-danger mx-2" to="/">
                            Cancel
                        </Link>
                    </form>
                </div>
            </div>
        </div>
    );
}
