import React, { useState } from 'react'
import './Login.css'
import sub_img_1 from '../../../assets/login-sub-img1.png'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEyeSlash, faEye } from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from 'react-router-dom'
const Login = () => {
    const navigate = useNavigate();
    const [showPassword, setShowPassword] = useState(false);

  return (
    <div className='login-admin'>
        <div className="login-container">
            <div className="login-info">
                <h3>This is the login page for BTalk audiobook management</h3>
                <div className="info-description">
                    <li>Use your email and password to log in</li>
                    <li>If you forget your password, click "Forgot password". The system will send a verification code to your email</li>
                    <li>If you don’t have an account, please contact the manager or administrator</li>
                </div>
                <img src={sub_img_1} />
            </div>
            <div className="login-section">
                <h1>LOGIN</h1>
                <div className="login-section-container">
                    <div className="item">
                        <label>Email:</label>
                        <input type="email" />
                    </div>
                    <div className="item">
                        <label>Password:</label>
                        <div className="password-container">
                            <input type={showPassword ? "text" : "password"} />
                            <span
                            className="eye-icon"
                            onClick={() => setShowPassword((prev) => !prev)}
                            >
                            <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye} />
                            </span>
                        </div>
                    </div>
                    <div className="forget-password-item">
                        <p onClick={() => navigate('/audiobooks')}>Forget password?</p>
                    </div>
                </div>
                <div className="login-action">
                    <button>LOGIN</button>
                </div>
            </div>
        </div>
    </div>
  )
}

export default Login
