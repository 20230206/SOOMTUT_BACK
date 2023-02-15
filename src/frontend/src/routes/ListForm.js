import React from "react";
import styles from "../assets/styles/listpage.module.css"

import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";

// 더보기 기능 추가
function ListForm() {
    return (
        <div>
            <div className={styles.wrapper}>
                <div className={styles.headbox}>
                    <Link to="/mypage"> <Button className={styles.retbutton}> 돌아가기 </Button> </Link>
                    <div className={styles.headtextbox}><span className={styles.headtext}> 관심 목록 </span></div>
                </div>
                <div className={styles.listbox}>
                    <div className={styles.itembox}>
                        <div className={styles.itemimagebox}> 이미지 </div>
                        <div className={styles.itemdiscriptionbox}> 
                            <p className={styles.discriptiontext}>수영 수업 합니다</p>
                            <p className={styles.discriptiontext}>구월동</p>
                            <p className={styles.discriptiontext}>30, 000원</p>
                        </div>
                    </div>
                    <div className={styles.itembox}>
                        <div className={styles.itemimagebox}> 이미지 </div>
                        <div className={styles.itemdiscriptionbox}> 
                            <p className={styles.discriptiontext}>수영 수업 합니다</p>
                            <p className={styles.discriptiontext}>구월동</p>
                            <p className={styles.discriptiontext}>30, 000원</p>
                        </div>
                    </div>
                    <div className={styles.itembox}>
                        <div className={styles.itemimagebox}> 이미지 </div>
                        <div className={styles.itemdiscriptionbox}> 
                            <p className={styles.discriptiontext}>수영 수업 합니다</p>
                            <p className={styles.discriptiontext}>구월동</p>
                            <p className={styles.discriptiontext}>30, 000원</p>
                        </div>
                    </div>
                    <div className={styles.itembox}>
                        <div className={styles.itemimagebox}> 이미지 </div>
                        <div className={styles.itemdiscriptionbox}> 
                            <p className={styles.discriptiontext}>수영 수업 합니다</p>
                            <p className={styles.discriptiontext}>구월동</p>
                            <p className={styles.discriptiontext}>30, 000원</p>
                        </div>
                    </div>
                    <div className={styles.itembox}>
                        <div className={styles.itemimagebox}> 이미지 </div>
                        <div className={styles.itemdiscriptionbox}> 
                            <p className={styles.discriptiontext}>수영 수업 합니다</p>
                            <p className={styles.discriptiontext}>구월동</p>
                            <p className={styles.discriptiontext}>30, 000원</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ListForm;