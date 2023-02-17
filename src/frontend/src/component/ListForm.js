import React, { useEffect, useState } from "react";
import ReactDOM from 'react-dom/client';
import styles from "../assets/styles/listpage.module.css"

import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import PostBoxInList from "./PostBoxInList";

// 더보기 기능 추가
function ListForm(props) {
    const [data, setData] = useState([]);

    useEffect(() => {
        setData(props.data)
    }, [])


    return (
        <div>
            <div className={styles.wrapper}>
                <div className={styles.headbox}>
                    <Link to="/mypage"> <Button className={styles.retbutton}> 돌아가기 </Button> </Link>
                    <div className={styles.headtextbox}> 
                        <span className={styles.headtext}> {props.title} </span>
                    </div> 
                </div>
                <div className={styles.listbox} id="listbox">
                    <PostBoxInList />
                    <PostBoxInList />
                    <PostBoxInList />
                    <PostBoxInList />
                    <PostBoxInList />
                </div>
            </div>
        </div>
    );
}

export default ListForm;