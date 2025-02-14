import React, { useEffect, useState } from 'react'
import './AudioBookList.css'
import {ToastContainer, toast} from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css';
import Swal from 'sweetalert2';
import { deleteAudiobook, listAudioBook } from '../../../services/AudiobookService';
import {Link, useNavigate} from 'react-router-dom'

const AudioBookList = () => {
    const navigate = useNavigate();

    // Show list audiobook
    const [audiobooks, setAudiobooks] = useState([]);

    const fetchAudioBooks = async () => {
        try {
            const response = await listAudioBook();
            setAudiobooks(response.data);
        } catch (error) {
            console.error("Error fetching audiobooks:", error);
            toast.error("Failed to load audiobooks!");
        }
    }

    useEffect(() => {
        fetchAudioBooks();
    }, []);

    // Preview Image
    const [imagePreview, setImagePreview] = useState(null);
    const handleImageChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const imageUrl = URL.createObjectURL(file);
            setImagePreview(imageUrl);
        }
    };

    // Return image url
    const handleImageUrl = (url) => {
        return "http://localhost:8080/api/files/" + url;
    }

    // Delete a category
    const handleDelete = async (id) => {
        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this audiobook!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, delete it!"
        }).then(async (result) => {
            if (result.isConfirmed) {
                try {
                    await deleteAudiobook(id);
                    toast.success("Audiobook deleted successfully!");
                    fetchAudioBooks();
                } catch (error) {
                    toast.error("Failed to delete audiobook!");
                }
            }
        });
    };

    // Search
    const [searchTerm, setSearchTerm] = useState("");
    const filteredAudiobooks = audiobooks.filter(audiobook =>
        audiobook.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        audiobook.id.toString().includes(searchTerm) ||
        audiobook.author.toLowerCase().includes(searchTerm.toLowerCase()) ||
        audiobook.voice.toLowerCase().includes(searchTerm.toLowerCase())
    )

    // Filter
    const [sortOrder, setSortOrder] = useState("");  
    const sortedAudiobooks = [...filteredAudiobooks].sort((a, b) => {
        if (sortOrder === "id-asc") return a.id - b.id;
        if (sortOrder === "id-desc") return b.id - a.id;
        if (sortOrder === "name-asc") return a.title.localeCompare(b.title);
        if (sortOrder === "name-desc") return b.title.localeCompare(a.title);
        return 0;
    });

    // Pagination
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentAudiobooks = sortedAudiobooks.slice(indexOfFirstItem, indexOfLastItem);
    const totalPages = Math.ceil(sortedAudiobooks.length / itemsPerPage);

  return (
    <div className='audiobook-list'>
        <ToastContainer position="top-right" autoClose={3000} />
        <h1>Audiobook</h1>
        <div className="audiobook-list-header">
            <div className="title">
                Audiobook list
                <div className="action">
                    <input
                        type="text"
                        placeholder="Input audiobook name or id ..." 
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}   
                    />
                    <select value={sortOrder} onChange={(e) => setSortOrder(e.target.value)}>
                        <option value="">Sort by</option>
                        <option value="id-asc">By Id: Ascending</option>
                        <option value="id-desc">By Id: Descending</option>
                        <option value="name-asc">By name: A to Z</option>
                        <option value="name-desc">By name: Z to A</option>     
                    </select>
                </div>
            </div>
            <div className="add">
                <Link to="/audiobooks/new-audiobook">
                    <button>Add New Product</button>
                </Link>  
            </div>
        </div>
        <div className="audiobook-list-container">
            <table>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Image</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Voice</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {currentAudiobooks.map(audiobook => (
                        <tr key={audiobook.id}>
                            <td>{audiobook.id}</td>
                            <td>
                                <img src={handleImageUrl(audiobook.image)} alt="" />
                            </td>
                            <td>{audiobook.title}</td>
                            <td>{audiobook.author.name}</td>
                            <td>{audiobook.voice.name}</td>
                            <td >
                                <div className="action">
                                    <button onClick={() => navigate(`/audiobooks/update/${audiobook.id}`)}>Update</button>
                                    <button onClick={() => {handleDelete(audiobook.id) }}>
                                        Delete
                                    </button>
                                    <button onClick={() => navigate(`/audiobooks/detail/${audiobook.id}`)}>Detail</button>
                                </div>
                                
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
        <div className="pagination">
            <button 
                onClick={() => setCurrentPage(prev => Math.max(prev - 1, 1))}
                disabled={currentPage === 1}>
                Prev
            </button>
            <span>{currentPage} / {totalPages}</span>
            <button 
                    onClick={() => setCurrentPage(prev => Math.min(prev + 1, Math.ceil(audiobooks.length / itemsPerPage)))}
                    disabled={currentPage === Math.ceil(audiobooks.length / itemsPerPage)}
                >
                    Next
            </button>
        </div>
    </div>
  )
}

export default AudioBookList
