import './App.css';
import NavBar from './components/Navbar';
import Home from './pages/Home';
import Login from './pages/Login';
import Catalog from './pages/Catalog';
import AddUser from './users/AddUser';
import Footer from './components/Footer';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <Router>
        <NavBar />
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/register" element={<AddUser />}></Route>
          <Route path="/catalog" element={<Catalog />}></Route>
        </Routes>
        <Footer />
    </Router>
    </div>
  );
}

export default App;
