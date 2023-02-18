import React, { useState, useEffect } from "react";
import { Container, Navbar, Nav } from "react-bootstrap";

import styles from '../assets/styles/navstyles.module.css'
import axios from 'axios'

import logo from '../assets/images/logo.png'


function SoomtutNavbar() {
    // 쿠키 사용시 적용해줘야 함
    axios.defaults.withCredentials = true;

    const [signin, setSignin] = useState(false)
    const [token, setToken] = useState(null)
    const [name, setName] = useState("...");

    const GetAccessToken = () => {
        var config = {
            method: 'get',
          maxBodyLength: Infinity,
            url: 'http://localhost:8080/validtoken',
            headers: { 

            }
          };
          
          axios(config)
          .then(function (response) {
            setToken(response.headers.get("Authorization"))
            setSignin(true);
          })
          .catch(function (error) {
            console.log(error);
          });
    }

    useEffect(() => {
        GetAccessToken();
    }, [])

    useEffect(() => {
        GetUserDetailInfo();
    }, [token])
    
    const GetUserDetailInfo = () => {
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
            setName(response.data.nickname);
          })
          .catch(function (error) {
            console.log(error);
          });
          
    }

    const signout = () => {
        // 서버에 로그아웃 요청 보내고
                
        var config = {
            method: 'post',
        maxBodyLength: Infinity,
            url: 'http://localhost:8080/signout'
        };
        
        axios(config)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
            setSignin(response.data)
        })
        .catch(function (error) {
            console.log(error);
        });
        

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