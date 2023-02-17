import React, { useEffect, useState } from "react";

import { Button, Dropdown } from "react-bootstrap";

import styles from "../../assets/styles/listpage.module.css"
import axios from "axios"

import { Link } from "react-router-dom";
import PostBoxInList from "../../component/PostBoxInList";

const Category_List = [ 
    { id:0, name:"전체" },
    { id:1, name:"스포츠" },
    { id:2, name:"댄스" },
    { id:3, name:"공부" },
    { id:4, name:"외국어" },
    { id:5, name:"음악" },
    { id:6, name:"IT" },
    { id:7, name:"디자인" },
    { id:8, name:"요리" },
    { id:9, name:"미술" },
    { id:10, name:"운동" }
];

function PostList() {
    const [res, setRes] = useState([])

    const getPosts = (category) => {
        var config = {
            method: 'get',
        maxBodyLength: Infinity,
            url: `http://localhost:8080/boardAll?category=${category.id}`,
            headers: { 
            'Authorization': localStorage.getItem("Authorization")
            }
        };
        
        axios(config)
        .then(function (response) {
            const data = response.data;
            setRes(data);
        })
        .catch(function (error) {
            console.log(error);
        });
  
    }
    
    const [curCategory, setCurCategory] = useState(Category_List[0])
    const SelectCategory = (type) => {
        setCurCategory(Category_List[type])
    }

    useEffect(() => {
        getPosts(Category_List[0]);
    }, [])


    useEffect(() => {
        getPosts(curCategory);
    }, [curCategory])

    return (
        <div>
            <div className={styles.wrapper}>
                <div className={styles.headbox}>
                    <Link to="/"> <Button className={styles.retbutton}> 돌아가기 </Button> </Link>
                    <div className={styles.headtextbox}> 
                    <Dropdown>
                    <Dropdown.Toggle  id="dropdown-basic"> {curCategory.name}
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                    <Dropdown.Item onClick={ () => SelectCategory(0) } > { Category_List[0].name } </Dropdown.Item>
                    <Dropdown.Item onClick={ () => SelectCategory(1) } > { Category_List[1].name } </Dropdown.Item>
                    <Dropdown.Item onClick={ () => SelectCategory(2) } > { Category_List[2].name } </Dropdown.Item>
                    <Dropdown.Item onClick={ () => SelectCategory(3) } > { Category_List[3].name } </Dropdown.Item>
                    <Dropdown.Item onClick={ () => SelectCategory(4) } > { Category_List[4].name } </Dropdown.Item>
                    <Dropdown.Item onClick={ () => SelectCategory(5) } > { Category_List[5].name } </Dropdown.Item>
                    <Dropdown.Item onClick={ () => SelectCategory(6) } > { Category_List[6].name } </Dropdown.Item>
                    <Dropdown.Item onClick={ () => SelectCategory(7) } > { Category_List[7].name } </Dropdown.Item>
                    <Dropdown.Item onClick={ () => SelectCategory(8) } > { Category_List[8].name } </Dropdown.Item>
                    <Dropdown.Item onClick={ () => SelectCategory(9) } > { Category_List[9].name } </Dropdown.Item>
                    <Dropdown.Item onClick={ () => SelectCategory(10) } > { Category_List[10].name } </Dropdown.Item>
                    </Dropdown.Menu>
                    </Dropdown>
                    </div> 
                    <Link to="/posts/create"> <Button className={styles.retbutton}> 글 쓰기 </Button> </Link>
                </div>
                <div className={styles.listbox} id="listbox">
                    { res.length >= 1  ? <PostBoxInList data={res[0]} /> : null }
                    { res.length >= 2  ? <PostBoxInList data={res[1]} /> : null }
                    { res.length >= 3  ? <PostBoxInList data={res[2]} /> : null }
                    { res.length >= 4  ? <PostBoxInList data={res[3]} /> : null }
                    { res.length >= 5 ? <PostBoxInList data={res[4]} /> : null }
                </div>
            </div>
        </div>
    );
}

export default PostList;