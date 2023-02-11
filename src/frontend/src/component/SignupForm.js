import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import styles from "../assets/styles/formstyle.module.css"
import logo from "../assets/images/logo.png"

function SignupForm() {
    const [email, setEmail] = useState("");
    const [nickname, setNickname] = useState("");
    const [password, setPassword] = useState("");

    const [isValidEmail, setValidEmail] = useState(false);
    const [isValidNickname, setValidNickname] = useState(false);
    const [isValidPassword, setValidPassword] = useState(false);


    const InputEmail = (event) => {
        setValidEmail(CheckEmail(event))
        setEmail(event.target.value)
    }
    const CheckEmail = (event) => {
        // 중복검사
        var regex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/
        return regex.test(event.target.value)
    }

    const InputNickname = (event) => {
        setValidNickname(CheckNickname(event))
        setNickname(event.target.value);
    }

    const CheckNickname = (event) => {
        // 중복검사
        const regex = /^[가-힣a-zA-Z0-9]{4,10}$/
        console.log(regex.test(event.target.value));
        return regex.test(event.target.value)
    }

    const InputPassword = (event) => {
        setValidPassword(CheckPassword(event))
        setPassword(event.target.value);
    }

    const CheckPassword = (event) => {
        var regex = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
        return regex.test(event.target.value);
    }

    return (
        <div className={styles.wrapper}>
         <div className={styles.formbox}>
          <img src={logo} style={{width:"220px"}} alt="logo" />
          <text className={styles.title}>회원가입</text>
          <br />
           <Form>
            <Form.Group className={styles.Group}>
             <Form.Label className={styles.label}>Email</Form.Label>
             <br />
             <Form.Control
              value={email}
              type="email"
              placeholder="Email을 입력해 주세요"
              className={styles.input}
              onChange={InputEmail} />
              <br />
             <Form.Text
              className="text-muted"
              style={isValidEmail ? {color:"green"} : {color:"red"}}>
                 {isValidEmail ? "가입 가능한 이메일입니다" : "가입 불가능한 이메일입니다." }
             </Form.Text>
            </Form.Group>
    
            <Form.Group className={styles.Group}>
             <Form.Label className={styles.label}>Nickname</Form.Label>
             <br />
             <Form.Control
              value={nickname}
              type="text"
              placeholder="4~10자 사이의 한글, 영문, 숫자"
              className={styles.input}
              onChange={InputNickname} />
             <br />
             <Form.Text
              className="text-muted"
              style={isValidNickname ? {color:"green"} : {color:"red"}}>
                 {isValidNickname? "사용 가능한 닉네임입니다." : "사용 불가능한 닉네임입니다."}
             </Form.Text>
            </Form.Group>
    
            <Form.Group className={styles.Group}>
             <Form.Label className={styles.label}>Password</Form.Label>
             <br />
             <Form.Control
              value={password}
              type="password"
              placeholder="6~20자 사이의 영문, 숫자, 특수문자"
              className={styles.input}
              onChange={InputPassword} />
             <br />
             <Form.Text
              className="text-muted"
              style={isValidPassword ? {color:"green"} : {color:"red"}}>
                 {isValidPassword? "사용 가능한 비밀번호입니다." : "사용 불가능한 비밀번호입니다."}
             </Form.Text>
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