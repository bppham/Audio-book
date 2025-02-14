import React from 'react'
import './Sidebar.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHouse, faChartSimple, faFileAudio, faUsers, faBook, faPeopleRoof, faBuildingCircleCheck, faList, faPen, faMicrophone, faCircleDollarToSlot } from '@fortawesome/free-solid-svg-icons'
import {Link} from "react-router-dom"

const Sidebar = () => {
  return (
    <div className='sidebar'>
        <div className="sidebar-container">
            <div className="sidebar-menu">
                <div className="sidebar-title">Dashboard</div>
                <ul className="sidebar-list">
                    <Link to="/" className='link'>
                        <li className="sidebar-list-item">
                            <FontAwesomeIcon className='sidebar-icon' icon={faHouse} /> Home
                        </li>
                    </Link>
                    
                    <li className="sidebar-list-item">
                        <FontAwesomeIcon className='sidebar-icon' icon={faChartSimple} /> Analytics
                    </li>
                </ul>
            </div>

            <div className="sidebar-menu">
                <div className="sidebar-title">Audiobook</div>
                <ul className="sidebar-list">
                    <Link to="/categories" className='link'>
                        <li className="sidebar-list-item">
                            <FontAwesomeIcon className='sidebar-icon' icon={faList} /> Categories
                        </li>
                    </Link>


                    <Link to="/audiobooks" className='link'>
                        <li className="sidebar-list-item">
                            <FontAwesomeIcon className='sidebar-icon' icon={faFileAudio} /> Audiobook
                        </li>
                    </Link>

                    <Link to="/authors" className='link'>
                        <li className="sidebar-list-item">
                            <FontAwesomeIcon className='sidebar-icon' icon={faPen} /> Author
                        </li>
                    </Link>

                    <Link to="/voices" className='link'>
                        <li className="sidebar-list-item">
                            <FontAwesomeIcon className='sidebar-icon' icon={faMicrophone} /> Voice actor
                        </li>
                    </Link>
                    
                </ul>
            </div>

            <div className="sidebar-menu">
                <div className="sidebar-title">Clients</div>
                <ul className="sidebar-list">
                    <Link to="/users" className='link'>
                        <li className="sidebar-list-item">
                            <FontAwesomeIcon className='sidebar-icon' icon={faUsers} /> User
                        </li>
                    </Link>
                </ul>
            </div>

            <div className="sidebar-menu">
                <div className="sidebar-title">Staff</div>
                <ul className="sidebar-list">
                    <li className="sidebar-list-item">
                        <FontAwesomeIcon className='sidebar-icon' icon={faPeopleRoof} /> Employee
                    </li>
                    <li className="sidebar-list-item">
                        <FontAwesomeIcon className='sidebar-icon' icon={faBuildingCircleCheck} /> Authorization
                    </li>
                </ul>
            </div>

            <div className="sidebar-menu">
                <div className="sidebar-title">Log</div>
                <ul className="sidebar-list">
                    <li className="sidebar-list-item">
                        <FontAwesomeIcon className='sidebar-icon' icon={faBook} /> Log event
                    </li>
                </ul>
            </div>
        </div>
    </div>
  )
}

export default Sidebar
