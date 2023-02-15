import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import styles from "../assets/styles/formstyle.module.css"
import axios from "axios";

import { useNavigate, Link } from "react-router-dom"
import kakao from "../assets/images/kakaosignup.png"
import logo from "../assets/images/logo.png"

function SigninForm() {

    const navigate = useNavigate();
    
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    const InputEmail = (event) => {
        setEmail(event.target.value)
    }
    const InputPassword = (event) => {
        setPassword(event.target.value);
    }

    const SubmitSignin = (email, password) => {
        var data = JSON.stringify({
            "email": email,
            "password": password
            });
            
        var config = {
        method: 'post',
        maxBodyLength: Infinity,
        url: 'http://localhost:8080/signin',
        headers: { 
            'Content-Type': 'application/json'
        },
        data : data
        };
        
        axios(config)
        .then(function (response) {
            //console.log(JSON.stringify(response.data));
            console.log(response.headers.get("Authorization"));
            localStorage.setItem('Authorization', JSON.stringify(response.headers.get("Authorization")))
            navigate("/");
        })
        .catch(function (error) {
        console.log(error);
        });
    }

    const handleSubmit = (event) => {
        event.preventDefault();
    }

    return (
        <div className={styles.wrapper}>
            <div className={styles.formbox}>
            <img src={logo} style={{width:"220px"}} alt="logo"/>
            <p className={styles.title}>로그인</p>
            <br></br>
            <Form onSubmit={handleSubmit}>
            <Form.Group className={styles.Group}>
                <Form.Label className={styles.label}>Email</Form.Label>
                <br></br>
                <Form.Control
                value={email}
                type="email"
                placeholder="email"
                className={styles.input}
                onChange={InputEmail} />
                <br></br>
            </Form.Group>
        
            <Form.Group className={styles.Group}>
                <Form.Label className={styles.label}>Password</Form.Label>
                <br></br>
                <Form.Control
                value={password}
                type="password"
                placeholder="Password"
                className={styles.input}
                onChange={InputPassword} />
                <br></br>
            </Form.Group>

            <Button className={styles.summit} variant="primary" type="submit" onClick={() => SubmitSignin(email, password)}>
            로그인
            </Button>
            </Form>
        
            <Link to="http://localhost:8080/oauth2/authorization/kakao" > <img src={kakao} className={styles.kakao} alt="kakaosignup"/> </Link>
            <Link to="http://localhost:8080/oauth2/authorization/google" ><Button> Google </Button></Link>
            </div>
        </div>
    );
}

export default SigninForm