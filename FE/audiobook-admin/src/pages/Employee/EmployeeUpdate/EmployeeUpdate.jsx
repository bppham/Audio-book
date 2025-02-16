import React, { useEffect, useState } from 'react'
import './EmployeeUpdate.css'
import { useNavigate, useParams } from 'react-router-dom'
import {ToastContainer, toast} from 'react-toastify'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faX} from '@fortawesome/free-solid-svg-icons'
import { getEmployeeById, listRole, updateEmployee } from '../../../services/EmployeeService';

const EmployeeUpdate = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [employee, setEmployee] = useState({
        name: "",
        email: "",
        phoneNumber: "",
        roles: []
    })

    const [image, setImage] = useState(null);

    // Get employee by id
    const fetchData = async () => {
        try {
            const response = await getEmployeeById(id);
            console.log("📥 API Response:", response.data);
            setEmployee(response.data);
        } catch (error) {
            console.error("Error fetching employee: " + error);
        }
    };
    
    useEffect(() => {
        const fetchEmployee = async () => {
            await fetchData();
        };
        fetchEmployee();
    }, [id]);

    // Handle input change
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEmployee((prev) => ({ ...prev, [name]: value }));
    };

    // Preview Image
    const [imagePreview, setImagePreview] = useState(null);
    const handleImageChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const imageUrl = URL.createObjectURL(file);
            setImagePreview(imageUrl);
            setImage(file);
        }
    };

    // Return image url
    const handleImageUrl = (url) => {
        return "http://localhost:8080/api/files/" + url;
    }

    // Return file url
    const handleFileUrl = (url) => {
        return "http://localhost:8080/api/files/" + url;
    }

    // Show image at the beginning
    useEffect(() => {
        if (employee && employee.avatar) {
            const urlImage = handleImageUrl(employee.avatar)
          setImagePreview(urlImage);
        }
    }, [employee]);

    // Handel roles
    const [allRoles, setAllRoles] = useState([]);
    const fetchRoles = async () => {
        try {
            const response = await listRole();
            setAllRoles(response.data);
        } catch (error) {
            console.error("Error fetching roles:", error);
            toast.error("Failed to load roles!");
        }
    }

    useEffect(() => {
        fetchRoles();
    }, []);


    useEffect(() => {
        if (employee?.roles?.length > 0) {
            if (typeof employee.roles[0] === 'object') {
                const roleName = employee.roles.map(role => role);
                setEmployee(prev => ({
                    ...prev,
                    role: roleName,
                }));
            }
        }
    }, [employee]);
    
    const handleRolesChange = (event) => {
        const roleName = event.target.value;
        
        setEmployee((prev) => ({
            ...prev,
            roles: prev.roles.includes(roleName)
                ? prev.roles.filter(name => name !== roleName)  // Bỏ nếu đã có
                : [...prev.roles, roleName]  // Thêm nếu chưa có
        }));
    };


    // Handle update data
    const handleUpdate = async () => {
        console.log(employee);
        try {
            await updateEmployee(employee.id, employee, image);
            toast.success("Updated successfully!");
            navigate("/employees");
        } catch (error) {
            console.error("Error updating employee:", error);
            toast.error("Update failed!");
        }
    };
    

  return (
    <div className='employee-update'>
        <ToastContainer position="top-right" autoClose={3000} />
        <h1>Update an employee</h1>
        <div className="form-update-employee">
            <div className="row">
                <div className="item">
                    <label>Id</label>
                    <input type="text" name="id" value={employee.id} readOnly/>
                </div>
                <div className="item">
                    <label>Name</label>
                    <input type="text" name="name" value={employee.name} onChange={handleInputChange} />
                </div>
            </div>
            <div className="row">
                <div className="item">
                    <label>Email</label>
                    <input type="text" name="email" value={employee.email} onChange={handleInputChange} />
                </div>
                <div className="item">
                    <label>Phone Number</label>
                    <input type="text" name="phoneNumber" value={employee.phoneNumber} onChange={handleInputChange} />
                </div>
            </div>
            <div className="row">
                <div className="item">
                    <label>Image</label>
                    <input type="file" name="image" id="" onChange={handleImageChange}/>
                </div>
                {imagePreview && (
                    <div className="image-preview">
                        <img src={imagePreview} alt="Preview" />
                    </div>
                )}
            </div>
            <div className="row">
                <label>Roles</label>
                <div className="role-list">
                    {allRoles.map(role => (
                        <label key={role} className="role-item">
                            <input 
                                type="checkbox" 
                                value={role} 
                                onChange={handleRolesChange} 
                                checked={employee.roles.includes(role)}
                            />
                            {role}
                        </label>
                    ))}
                </div>
            </div>
            
        </div>
        <div className="action">
            <button onClick={handleUpdate}>Confirm</button>
            <button onClick={() => navigate('/employees')}>Cancel</button>
        </div>
    </div>
  )
}

export default EmployeeUpdate
