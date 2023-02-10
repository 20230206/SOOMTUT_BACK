import React from "react";
import { Button, Form } from "react-bootstrap";
import styles from "../assets/styles/formstyle.module.css"
import logo from "../assets/images/logo.png"

function SignupForm() {
    return (
        <div className={styles.wrapper}>
         <div className={styles.formbox}>
          <img src={logo} style={{width:"220px"}} alt="logo"/>
          <text className={styles.title}>회원가입</text>
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
             <Form.Text
              className="text-muted"
              style={{color:"red"}}>
                 가입이 불가능한 이메일입니다.
             </Form.Text>
            </Form.Group>
    
            <Form.Group className={styles.Group}>
             <Form.Label className={styles.label}>Nickname</Form.Label>
             <br></br>
             <Form.Control
              type="password"
              placeholder="nickname"
              className={styles.input} />
             <br></br>
             <Form.Text
              className="text-muted"
              style={{color:"red"}} >
                 사용이 불가능한 닉네임입니다.
             </Form.Text>
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
            가입하기
            </Button>
           </Form>
         </div>
        </div>
    );
}

export default SignupForm;