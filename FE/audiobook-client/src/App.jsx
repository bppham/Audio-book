import './App.css'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from './components/Navbar/Navbar';
import Sidebar from './components/Sidebar/Sidebar';
import Home from './pages/Home/Home';
import AudioBook from './pages/AudioBook/AudioBook';
import Library from './pages/Library/Library';

function App() {

  return (
    <Router>
        <Navbar/>
        <div className="container">
            <Sidebar/>
            <Routes>
              <Route path = "/" element={<Home/>}/>
              <Route path = "/:id" element={<AudioBook/>}/>
            </Routes>
        </div>

    </Router>
  )
}

export default App
