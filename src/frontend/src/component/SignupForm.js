import React from "react";
import { Container, Button, Form } from "react-bootstrap";
import styles from "../assets/styles/formstyle.module.css"

function SignupForm() {
    return (
        <div className={styles.wrapper}>
            <Form>
                <Form.Group>
                    <Form.Label className={styles.label}>Email</Form.Label>
                    <br></br>
                    <Form.Control
                     type="email"
                     placeholder="email" />
                    <br></br>
                    <Form.Text className="text-muted">
                        가입이 불가능한 이메일입니다.
                    </Form.Text>
                </Form.Group>

                <Form.Group>
                    <Form.Label className={styles.label}>Password</Form.Label>
                    <br></br>
                    <Form.Control
                     type="password"
                     placeholder="Password" />
                    <br></br>
                </Form.Group>

                <Form.Group>
                    <Form.Check type="checkbox" label="기억하기" />
                </Form.Group>

                <Button variant="primary" type="submit">
                Submit
                </Button>
            </Form>
        </div>
        
    );
        
}

export default SignupForm;