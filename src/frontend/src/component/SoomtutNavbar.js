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

    const IsSignnedIn = () => {
        var config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: 'http://localhost:8080/auth/validtoken',
            headers: { 

            }
          };
          
          axios(config)
          .then(function (response) {
            setToken(response.headers.get("Authorization"));
            setSignin(response.data);
          })
          .catch(function (error) {
            console.log(error);
          });
    }

    const signout = () => {
        // 서버에 로그아웃 요청 보내고 refresh cookie를 삭제해준다
        var config = {
            method: 'post',
            maxBodyLength: Infinity,
            url: 'http://localhost:8080/auth/signout'
        };
        
        axios(config)
        .then(function (response) {
            setSignin(false);
        })
        .catch(function (error) {
            console.log(error);
        });

        // 화면을 새로고침 해준다
        window.location.reload();
    }
    
    useEffect(() => {
        IsSignnedIn();
    }, [])

    useEffect(() => {
        if(signin === true) {
            GetUserInfo();
        }
        else {
        }
    }, [signin])

    useEffect(() => {
        
    }, [name])
    
    const GetUserInfo = () => {
        var config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: 'http://localhost:8080/getmyinfo',
            headers: { 
                'Authorization': token
            }
        };
        
        axios(config)
        .then(function (response) {
            setName(response.data.nickname)
        })
        .catch(function (error) {
            console.log(error);
        });
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