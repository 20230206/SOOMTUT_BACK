import React, { useEffect, useState } from "react";
import ReactDOM from 'react-dom/client';
import styles from "../assets/styles/listpage.module.css"

import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import MyDropdown from "./MyDropdown";
import PostBoxInList from "./PostBoxInList";

// 더보기 기능 추가
function ListForm(props) {
    const [headType, setHeadType] = useState(0);
    const [data, setData] = useState([]);

    const HeadType = (options) => {

        if(options === "normal") setHeadType(0);
        else if(options === "dropdown") setHeadType(1);
    }

    useEffect(() => {
        HeadType(props.option)
        setData(props.data)
    }, [])

    useEffect(() => {
        PostAppend(data);
    }, [data])

    const PostAppend = (data) => {
        console.log(data);
        const listbox = document.getElementById("listbox")
        for(var i=0; i<data.length; i++) {
            const element = React.createElement(<PostBoxInList data={data[i]} />)
            ReactDOM.render(element, listbox)
        }
    }

    return (
        <div>
            <div className={styles.wrapper}>
                <div className={styles.headbox}>
                    <Link to="/mypage"> <Button className={styles.retbutton}> 돌아가기 </Button> </Link>
                    <div className={styles.headtextbox}> 
                        { headType === 0 ? <span className={styles.headtext}> {props.title} </span> : null } 
                        { headType === 1 ?  <MyDropdown />: null}
                    </div> 
                </div>
                <div className={styles.listbox} id="listbox">
                </div>
            </div>
        </div>
    );
}

export default ListForm;