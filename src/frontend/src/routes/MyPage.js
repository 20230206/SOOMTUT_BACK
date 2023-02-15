import React from "react";
import styles from "../assets/styles/mypage.module.css"
import { Button } from "react-bootstrap";
import {Link} from "react-router-dom"

function MyPage() {

    return (
        <div>
            <div className={styles.wrapper}>
                <div className={styles.pagebox}>
                     <p className={styles.pageboxtext}>마이 페이지</p>
                </div>
                <div className={styles.profilebox}>
                    <div className={styles.imagebox}>
                        <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F2513B53E55DB206927"/>
                    </div>
                    <div className={styles.profiles}>
                        <div className={styles.profilename}> <span> 신신희 님 </span></div>
                        <div className={styles.profileemail}> <span> sparta2023@naver.com </span></div>
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
                    <span className={styles.infotextfont}> 기타 </span>
                    <li className={`${styles.infotextfont} ${styles.textmarginleft}`}><Link to="/"> 내 위치 설정 </Link></li>
                    <li className={`${styles.infotextfont} ${styles.textmarginleft}`}><Link to="/"> 회원 탈퇴 </Link></li>


                </div>
            </div>
        </div>
    );

}

export default MyPage;