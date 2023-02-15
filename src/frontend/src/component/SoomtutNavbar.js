import React, { useState, useEffect } from "react";
import { Container, Navbar, Nav } from "react-bootstrap";
import styles from '../assets/styles/navstyles.module.css'
import axios from 'axios'

import logo from '../assets/images/logo.png'


function SoomtutNavbar() {
    const [signin, setSignin] = useState(false)
    const [token, setToken] = useState(null)

    const subscribe = () => {
        setToken(localStorage.getItem('Authorization'));
    }

    const validToken = () => {
        if(token === null) return;
        var data = '';

        var config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: 'http://localhost:8080/validtoken',
            headers: { 
                'Authorization': token
            },
            data : data
        };

        axios(config)
        .then(function (response) {
            setSignin(response.data);
        })
        .catch(function (error) {
        });

    }

    useEffect(() => {
        subscribe();
    }, [localStorage.getItem('Authorization')])

    useEffect(() => {
        validToken();
    }, [token])
    
    const signout = () => {
        // 서버에 로그아웃 요청 보내고

        // 로컬 스토리지의 authorization을 제거해준다.
        localStorage.setItem('Authorization', null);
        // 화면을 새로고침 해준다
        window.location.reload();
    }

    return (
        <div>
         <Navbar fixed="top">
          <Container className={styles.wrapper}>
           <Navbar.Brand href="#">
            <img src={logo} className={styles.logo} alt='logo' />
           </Navbar.Brand>
           {signin ? <Nav.Link className={styles.signin} onClick={() => signout()}> 로그아웃 </Nav.Link> : <Nav.Link className={styles.signin} href="/signin"> 로그인 </Nav.Link> }
           {signin ? <Nav.Link className={styles.signup} href="/signup"> 마이페이지 </Nav.Link>  : <Nav.Link className={styles.signup} href="/signup"> 회원가입 </Nav.Link> }
          </Container>
         </Navbar>
         <hr />
        </div>
    );
}

export default SoomtutNavbar;