import React from "react";
import { Button, Form } from "react-bootstrap";
import styles from "../assets/styles/formstyle.module.css"

import { Link } from "react-router-dom"
import kakao from "../assets/images/kakaosignup.png"

function SigninForm() {
       return (
        <div className={styles.wrapper}>
         <div className={styles.formbox}>
          <text className={styles.title} style={{color:"#4E57DE"}}> SOOMTUT </text>
          <br></br>
          <text className={styles.title}>로그인</text>
          <br></br>
          <br></br>
           <Form>
            <Form.Group className={styles.Group}>
             <Form.Label className={styles.label}>Email</Form.Label>
             <br></br>
             <Form.Control
              type="email"
              placeholder="email"
              className={styles.input} />
             <br></br>
            </Form.Group>
    
            <Form.Group className={styles.Group}>
             <Form.Label className={styles.label}>Password</Form.Label>
             <br></br>
             <Form.Control
              type="password"
              placeholder="Password"
              className={styles.input} />
             <br></br>
            </Form.Group>
                
            <Button className={styles.summit} variant="primary" type="submit">
            로그인
            </Button>
           </Form>
          
          <Link to="/signupkakao"> <img src={kakao} className={styles.kakao} alt="kakaosignup" /> </Link>
         </div>
        </div>
       );
}

export default SigninForm