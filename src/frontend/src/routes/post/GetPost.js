import React, { useState, useEffect } from "react";
import { useParams } from "react-router";

import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";

import styles from "../../assets/styles/poststyle.module.css"
import axios from "axios";

function GetPost() {
    const postId = useParams().id;
    const [postdata, setPostdata] = useState([])
    const [isMy, setIsMy] = useState(false);
    const [fav, setFav] = useState(false);

    const GetPostInfo = () => {
                
        var config = {
            method: 'get',
        maxBodyLength: Infinity,
            url: `http://localhost:8080/posts/${postId}`,
            headers: { 
            'Authorization': localStorage.getItem("Authorization")
            }
        };
        
        axios(config)
        .then(function (response) {
            setPostdata(response.data)
        })
        .catch(function (error) {
            console.log(error);
        });
        
    }

    const GetPostIsMy = () => {
                
        var config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: `http://localhost:8080/posts/${postId}/ismypost`,
            headers: { 
                'Authorization': localStorage.getItem("Authorization")
            }
        };
        
        axios(config)
        .then(function (response) {
            setIsMy(response.data)
        })
        .catch(function (error) {
            console.log(error);
        });
        
    }

    const GetFav = () => {
        var config = {
            method: 'get',
          maxBodyLength: Infinity,
            url: `http://localhost:8080/posts/${postId}/bookmark`,
            headers: { 
              'Authorization': localStorage.getItem("Authorization")
            }
          };
          
          axios(config)
          .then(function (response) {
            setFav(response.data)
          })
          .catch(function (error) {
            console.log(error);
          });
          
    }

    useEffect(() => {
        GetPostInfo();
        GetPostIsMy();
        GetFav();
    }, [ ])

    const RequestFav = () => {
        console.log(fav)
        var data = JSON.stringify({
            "curfav": true
          });
          
          var config = {
            method: 'post',
          maxBodyLength: Infinity,
            url: `http://localhost:8080/posts/${postId}/bookmark`,
            headers: { 
              'Authorization': localStorage.getItem("Authorization"), 
              'Content-Type': 'application/json'
            },
            data : data
          };
          
          axios(config)
          .then(function (response) {
            setFav(response.data)
          })
          .catch(function (error) {
            console.log(error);
          });
          
    }

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
                        <span> {postdata.tutorNickname} </span> <br />
                        <span> {postdata.location} </span> <span> LV20 </span> <br />
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
                    {/* 이버튼을 포스트 주인이라면 -> 수정하기 버튼
                                       주인이 아니라면 -> 북마크 버튼 */
                     isMy ? 
                    <Button className={styles.favbutton} >
                        수정 하기
                    </Button> :
                    <Button
                     className={styles.favbutton} 
                     onClick={() => RequestFav() }> {fav ? "❤" : "🤍"} {postdata.fee} 
                    </Button>
                    }
                    <Button className={styles.chatbutton}> 채팅 문의 </Button>
                </div>

            </div>
        </div>
    );
}

export default GetPost;