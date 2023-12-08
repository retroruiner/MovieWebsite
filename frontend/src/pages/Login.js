import axios from "axios";
import React, { useState, useEffect, setUser, user} from "react";
import { Link, useNavigate } from "react-router-dom";

function Login() {

  let navigate = useNavigate(); //To jump to another pages

  const [nickname, setNickname] = useState("");
  const [password, setPassword] = useState("");

  // const onInputChange = (e) => {
  //     setUser({ ...user, [e.target.name]: e.target.value }); //keep on adding the user in state
  // };

  const onLogin = async (e) => {
      e.preventDefault();  //prevent wierd url
      await axios.post("http://localhost:8080/api/users/login", user);
      navigate("/");  //Home
  };

  return (
    <div className="row">
      <div className="offset-lg-3 col-lg-6" style={{ marginTop: '100px' }}>
          <form onSubmit={onLogin} className="container">
              <div className="card">
                  <div className="card-header">
                      <h2>User Login</h2>
                  </div>
                  <div className="card-body">
                      <div className="form-group">
                          <label>User Nickname <span className="errmsg">*</span></label>
                          <input value={nickname} onChange={e => setNickname(e.target.value)} className="form-control"></input>
                      </div>
                      <div className="form-group">
                          <label>Password <span className="errmsg">*</span></label>
                          <input type="password" value={password} onChange={e => setPassword(e.target.value)} className="form-control"></input>
                      </div>
                  </div>
                  <div className="card-footer">
                      <button type="submit" className="btn btn-primary">Login</button> |
                      <Link className="btn btn-success" to={'/register'}>New User</Link>
                      <Link className="btn btn-success" to={'/'}>Cancel</Link>
                  </div>
              </div>
          </form>
      </div>
    </div>
  )
}

export default Login
