import React from "react";
import { Container, Navbar, Nav } from "react-bootstrap";
import styles from '../assets/styles/navstyles.module.css'

import logo from '../assets/images/logo.png'


function SoomtutNavbar() {
    return (
        <div>
         <Navbar fixed="top">
          <Container className={styles.wrapper}>
           <Navbar.Brand href="#">
            <img src={logo} className={styles.logo} alt='logo' />
           </Navbar.Brand>

           <Nav.Link className={styles.signin} href="/signin"> 로그인 </Nav.Link>
           <Nav.Link className={styles.signup} href="/signup"> 회원가입 </Nav.Link>
          </Container>
         </Navbar>
         <hr />
        </div>
    );
}

export default SoomtutNavbar;