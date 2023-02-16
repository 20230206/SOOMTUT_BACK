import React from "react";
import { Button, Form } from "react-bootstrap";
import styles from "../assets/styles/poststyle.module.css"
import MyDropdown from "../component/MyDropdown";

function Post() {
    return (
        <div className={styles.wrapper}>
            <div className={styles.headbox}>
                <Button className={styles.headboxbutton}> 돌아가기 </Button>
                <div className={styles.headboxtext}><span> 글쓰기 </span></div>
                <Button className={styles.headboxbutton}> 완료 </Button>
            </div>

            <div className={styles.imagebox}>
                {/* img */}
            </div>

            <div className={styles.categorybox}>
                <MyDropdown />
            </div>

            <div className={styles.feebox}>

            <textarea
                 className={styles.feeinput}
                 placeholder="가격(\)">

                </textarea>
            </div>

            <div className={styles.contentsbox}>
                <textarea
                 className={styles.contentsinput}
                 placeholder="내용을 입력하세요">

                </textarea>
            </div>
        </div>
    );
}

export default Post;