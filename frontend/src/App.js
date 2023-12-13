import './App.css';
import NavBar from './components/Navbar';
import Home from './pages/Home';
import Login from './pages/Login';
import Catalog from './pages/Catalog';
import AddUser from './users/AddUser';
import EditUser from './users/EditUser';
import ViewUser from './users/ViewUser';
import ViewCollections from './pages/ViewCollections';
import MoviesInCollection from './pages/MoviesInCollection';
import CreateCollection from './pages/CreateCollection';
import AllMovies from './pages/AllMovies'
import Genre from './pages/Genre';
import Footer from './components/Footer';
import MovieDescription from './pages/MovieDescription';
import MoviesByGenre from './pages/MoviesByGenre';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <Router>
        <NavBar />
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/register" element={<AddUser />}></Route>
          <Route path="/editUser/:id" element={<EditUser />}></Route>
          <Route path="/viewProfile/:id" element={<ViewUser />}></Route>
          <Route path="/collections/:id" element={<ViewCollections />}></Route>
          <Route path="/movies-in-collection/:id" element={<MoviesInCollection />}></Route>
          <Route path="/collections/create" element={<CreateCollection />}></Route>
          <Route path="/movies/:id" element={<MovieDescription />}></Route>
          <Route path="/catalog" element={<Catalog />}></Route>
          <Route path="/catalog/by-genre" element={<Genre />}></Route>
          <Route path="/catalog/all-movies" element={<AllMovies />}></Route>
          <Route path="/catalog/by-genre/:genre" element={<MoviesByGenre />} />
        </Routes>
        <Footer />
      </Router>
    </div>
  );
}

export default App;
