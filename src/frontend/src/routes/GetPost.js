import React, { useState, useEffect } from "react";
import { useParams } from "react-router";

import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";

import styles from "../assets/styles/poststyle.module.css"
import axios from "axios";

function GetPost() {
    const postId = useParams().id;
    const [postdata, setPostdata] = useState([])

    const Get = () => {
                
        var config = {
            method: 'get',
        maxBodyLength: Infinity,
            url: `http://localhost:8080/posts/${postId}`,
            headers: { 
            'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMUB1c2VyLmNvbSIsImF1dGgiOiJNRU1CRVIiLCJleHAiOjE2NzY3NzM0NjYsImlhdCI6MTY3NjU1NzQ2Nn0.a2gfUZ9WWqSDd5Ouv7WHRjAFhnsJnDYcgLXzl43YBmY'
            }
        };
        
        axios(config)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
            setPostdata(response.data)
        })
        .catch(function (error) {
            console.log(error);
        });
        
    }

    useEffect(() => {
        Get();
    }, [ ])

    return (
        <div>
            <div className={styles.wrapper}> 
                <div className={styles.headbox}>
                    <Link to="/posts"> <Button className={styles.headboxbutton}> 돌아가기 </Button> </Link>
                    <div className={styles.headboxtextonRead}><span> {postdata.title} </span></div>
                </div>
                    
                <div className={styles.imagebox}>
                    <img src={postdata.image} />
                </div>

                <div className={styles.tutorinfobox} >
                    <div className={styles.tutorimagebox}> </div>
                    <div className={styles.tutordiscripbox}>
                        <span> 아무개 </span> <br />
                        <span> 인천 구월동 </span> <span> Lv.20 </span> <br />
                    </div>
                </div>

                    
                <div className={styles.contentsbox}>
                    <div className={styles.contentdescrip}>
                        <p>
                            {postdata.content}
                        </p>
                    </div>
                </div>

                <div className={styles.menubox}>
                    <Button className={styles.favbutton}> {postdata.fee} </Button>
                    <Button className={styles.chatbutton}> 채팅 문의 </Button>
                </div>

            </div>
        </div>
    );
}

export default GetPost;