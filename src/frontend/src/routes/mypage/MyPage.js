import React, {
    useState,
    useEffect
} from "react";

import { 
    Button,
    Card
} from "react-bootstrap";
import { Link } from "react-router-dom"

import styles from "../../assets/styles/mypage.module.css"
import axios from "axios"

function MyPage() {
    const [myInfo, setMyInfo] = useState([]);
    const [location, setLocation] = useState("서울특별시 서초구 반포동");
    const GetMyInfo = () => {
                
        var config = {
            method: 'get',
        maxBodyLength: Infinity,
            url: 'http://localhost:8080/getmyinfo',
            headers: { 
            'Authorization': localStorage.getItem("Authorization")
            }
        };
        
        axios(config)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
            setMyInfo(response.data)
        })
        .catch(function (error) {
            console.log(error);
        });
  
    }

    useEffect(() => {
        GetMyInfo();
    }, [])

    const SetMyLocation = () => {
        
    }

    return (
        <div>
            <div className={styles.wrapper}>
                <div className={styles.pagebox}>
                     <p className={styles.pageboxtext}>마이 페이지</p>
                </div>
                <div className={styles.profilebox}>
                    <div className={styles.imagebox}>
                        <img src={myInfo.profileImage} alt="profileImage"/>
                    </div>
                    <div className={styles.profiles}>
                        <div className={styles.profilename}> <span> {myInfo.nickname} </span></div>
                        <div className={styles.profileemail}> <span> {myInfo.email} </span></div>
                        <div className={styles.profilebutton}> <Button> 프로필 업로드 </Button></div>
                    </div>
                </div>
                <div className={styles.infobox}>
                    <br /><br />
                    <span className={styles.infotextfont}> 나의 프로필 </span> 
                    <li className={`${styles.infotextfont} ${styles.textmarginleft}`}><Link to="/"> 프로필 보기 </Link></li>
                    <br /><br />
                    <span className={styles.infotextfont}> 나의 튜터링 </span>
                    <li className={`${styles.infotextfont} ${styles.textmarginleft}`}><Link to="/mypage/favlist"> 관심 목록 </Link></li>
                    <li className={`${styles.infotextfont} ${styles.textmarginleft}`}><Link to="/mypage/myclassedlist"> 수강한 수업 목록 </Link></li>
                    <li className={`${styles.infotextfont} ${styles.textmarginleft}`}><Link to="/mypage/myclasslist"> 나의 수업 목록 </Link></li>
                    <li className={`${styles.infotextfont} ${styles.textmarginleft}`}><Link to="/mypage/chat"> 채팅 목록 </Link></li>
                    <br /><br />


                    <div  className={styles.mylocations}>
                        <Card>
                        <Card.Header className={styles.locationtitle}> 내 활동 지역 
                            <Button
                             className={styles.locationbutton}
                             onClick={() => SetMyLocation()}
                             > 위치 수정 </Button>
                        </Card.Header>
                        <Card.Body>
                            <blockquote className="blockquote mb-0">
                            <p>
                                {location}
                            </p>
                            <footer className="blockquote-footer">
                                Someone famous in <cite title="Source Title">Source Title</cite>
                            </footer>
                            </blockquote>
                        </Card.Body>
                        </Card>

                    </div>

                    <li className={`${styles.infotextfont} ${styles.textmarginleft}`}><Link to="/"> 회원 탈퇴 </Link></li>


                </div>
            </div>
        </div>
    );

}

export default MyPage;