import React, { useState, useEffect } from "react";
import { Container, Navbar, Nav } from "react-bootstrap";

import styles from '../assets/styles/navstyles.module.css'
import axios from 'axios'

import logo from '../assets/images/logo.png'


function SoomtutNavbar() {
    axios.defaults.withCredentials = true;

    const [signin, setSignin] = useState(false)
    const [token, setToken] = useState(null)
    const [name, setName] = useState("...");

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
        <div className={styles.wrapper}>
         <Navbar bg="white" variant="white">
             <Container>
             <Navbar.Brand href="#home">
                 <img
                 alt=""
                 src={logo}
                 width="240"
                 className="d-inline-block align-top"
                 />{' '}
             </Navbar.Brand>
                <div className={styles.navmenu}>
                    {signin &&
                        <Navbar.Text className={styles.navmenuitem}>{name}님 안녕하세요</Navbar.Text> 
                    }
                    {signin ? 
                        <Nav.Link className={styles.navmenuitem} onClick={() => signout()}> 로그아웃 </Nav.Link> : 
                        <Nav.Link className={styles.navmenuitem} href="/signin"> 로그인 </Nav.Link> 
                    }
                    {signin ? 
                        <Nav.Link className={styles.navmenuitem} href="/mypage"> 마이페이지 </Nav.Link>  : 
                        <Nav.Link className={styles.navmenuitem} href="/signup"> 회원가입 </Nav.Link> 
                    }
                </div>
             </Container>
         </Navbar>
        </div>
    );
}

export default SoomtutNavbar;