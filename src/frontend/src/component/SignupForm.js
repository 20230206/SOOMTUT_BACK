import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import axios from "axios";


import styles from "../assets/styles/formstyle.module.css"
import logo from "../assets/images/logo.png"
import { useNavigate } from "react-router-dom";

function SignupForm() {
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [nickname, setNickname] = useState("");
    const [password, setPassword] = useState("");

    const [isValidEmail, setValidEmail] = useState(false);
    const [isValidNickname, setValidNickname] = useState(false);
    const [isValidPassword, setValidPassword] = useState(false);

    const [dupleEmail, setDupleEmail] = useState(false);
    const [dupleNickname, setDupleNickname] = useState(false);

    const InputEmail = (event) => {
        setValidEmail(CheckEmail(event))
        setEmail(event.target.value)
    }

    const CheckEmail = (event) => {
        // 중복검사
        var regex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{3,3}$/
        var isRegex =  regex.test(event.target.value);
        if(isRegex) { 
            EmailDupleCheck(event.target.value);
        }
        
        return isRegex;
    }
        

    const InputNickname = (event) => {
        setValidNickname(CheckNickname(event))
        setNickname(event.target.value);
    }

    const CheckNickname = (event) => {
        // 중복검사
        const regex = /^[가-힣a-zA-Z0-9]{4,10}$/
        var isRegex =  regex.test(event.target.value);
        if(isRegex) { 
            isRegex = isRegex && !NicknameDupleCheck(event.target.value)
            console.log(isRegex)
        }
        return isRegex;
    }

    const InputPassword = (event) => {
        setValidPassword(CheckPassword(event))
        setPassword(event.target.value);
    }

    const CheckPassword = (event) => {
        var regex = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]).*$/;
        return regex.test(event.target.value);
    }

    const EmailDupleCheck = (input) => {
        var data = '';

        var config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: `http://localhost:8080/signup/check?email=${input}`,
            headers: { },
            data : data
        };

        axios(config)
        .then(function (response) {
            setDupleEmail(response.data)
        })
        .catch(function (error) {
            console.log(error);
        });
    }

    const NicknameDupleCheck = (input) => {
        var data = '';

        var config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: `http://localhost:8080/signup/check?nickname=${input}`,
            headers: { },
            data : data
        };

        axios(config)
        .then(function (response) {
            console.log("response.data : " + response.data)
            setDupleNickname(response.data)
        })
        .catch(function (error) {
            console.log(error);
        });
    }

    function SubmitAccount(nickname, email, password) {
        console.log("Method[SubmitAccount] has called")

        var data = JSON.stringify({
            "nickname": nickname,
            "email": email,
            "password": password
          });
          
          var config = {
            method: 'post',
          maxBodyLength: Infinity,
            url: 'http://localhost:8080/signup',
            headers: { 
              'Content-Type': 'application/json'
            },
            data : data
          };
          
          axios(config)
          .then(function (response) {
            console.log(JSON.stringify(response.data));
            alert("회원가입에 성공하였습니다.")
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
          <img src={logo} style={{width:"220px"}} alt="logo" />
          <p className={styles.title}>회원가입</p>
          <br />
           <Form onSubmit={handleSubmit}>
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
              style={isValidEmail && !dupleEmail ? {color:"green"} : {color:"red"}}>
                 {isValidEmail && !dupleEmail ? "가입 가능한 이메일입니다" : "가입 불가능한 이메일입니다." }
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
              style={isValidNickname && !dupleNickname ? {color:"green"} : {color:"red"}}>
                 {isValidNickname && !dupleNickname ? "사용 가능한 닉네임입니다." : "사용 불가능한 닉네임입니다."}
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
            {/* onClick={SubmitAccount(nickname, email, password)} */}
            <Button className={styles.summit} variant="primary" type="submit" onClick={() => SubmitAccount(nickname,email,password)}>
            가입하기
            </Button>
           </Form>
         </div>
        </div>
    );
}

export default SignupForm;