import React from "react";
import styles from "../assets/styles/postforminlist.module.css"

function PostBoxInList(props) {
    return (
        <div>
            <div className={styles.itembox}>
                <div className={styles.itemimagebox}> <img src={props.data.image} alt="discriptionimage" /> </div>
                <div className={styles.itemdiscriptionbox}> 
                    <span className={styles.discriptiontext}> {props.data.tutorNickname} </span> <br />
                    <span className={styles.discriptiontext}> {props.data.title} </span> <br />
                    <span className={styles.discriptiontext}> {props.data.location} </span> <br />
                    <span className={styles.discriptiontext}> {props.data.fee} </span> 
                </div>
            </div>
        </div>
    );
}

export default PostBoxInList;