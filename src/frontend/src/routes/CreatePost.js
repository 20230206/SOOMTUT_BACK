import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import styles from "../assets/styles/poststyle.module.css"
import MyDropdown from "../component/MyDropdown";

function CreatePost() {
    const [title, setTitle] = useState("");
    const InputTitle = (event) => {
        setTitle(event.target.value)
    }

    const [fee, setFee] = useState(0);
    const InputFee = (event) => {
        var regex = /^[0-9]{0,99}$/
        var isRegex =  regex.test(event.target.value);
        if(isRegex) {
            setFee(event.target.value)
        }
    }
    
    const [contents, setContents] = useState("");
    const InputContents = (event) => {
        setContents(event.target.value)
    }

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

            <div className={styles.titlebox}>
                <textarea
                value={title}
                className={styles.titleinput}
                placeholder="제목을 입력하세요"
                onChange={InputTitle}
                />
            </div>

            <div className={styles.categorybox}>
                <MyDropdown />
            </div>

            <div className={styles.feebox}>
                <textarea
                value={fee}
                className={styles.feeinput}
                placeholder="가격(\)" 
                onChange={InputFee}
                />
            </div>

            <div className={styles.contentsbox}>
                <textarea
                 value={contents}
                 className={styles.contentsinput}
                 placeholder="내용을 입력하세요"
                 onChange={InputContents}
                />
            </div>
        </div>
    );
}

export default CreatePost;