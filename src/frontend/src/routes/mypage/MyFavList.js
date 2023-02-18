import React, {
    useState,
    useEffect
} from "react";

import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";

import PostBoxInList from "../../component/PostBoxInList";

import axios from "axios";
import styles from "../../assets/styles/listpage.module.css"

function MyFavList() {

    const GetFavList = () => {
                
        var config = {
            method: 'get',
        maxBodyLength: Infinity,
            url: 'http://localhost:8080/bookmark?page=0&size=5',
            headers: { 
            'Authorization': localStorage.getItem("Authorization")
            }
        };
        
        axios(config)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
            console.log(error);
        });
        
    }

    useEffect(() => {
        GetFavList();
    }, [])

    return (
    <div>
        <div className={styles.wrapper}>
            <div className={styles.headbox}>
                <Link to="/mypage"> <Button className={styles.retbutton}> 돌아가기 </Button> </Link>
                <div className={styles.headtextbox}> 
                    <span className={styles.headtext}> 나의 관심 목록 </span>
                </div> 
                <Link to="/posts/create"> <Button className={styles.retbutton}> 글 쓰기 </Button> </Link>
            </div>
            <div className={styles.listbox} id="listbox">
                {/* { res.length >= 1  ? <PostBoxInList data={res[0]} /> : null }
                { res.length >= 2  ? <PostBoxInList data={res[1]} /> : null }
                { res.length >= 3  ? <PostBoxInList data={res[2]} /> : null }
                { res.length >= 4  ? <PostBoxInList data={res[3]} /> : null }
                { res.length >= 5 ? <PostBoxInList data={res[4]} /> : null } */}
            </div>
        </div>
    </div>
    );
}

export default MyFavList;