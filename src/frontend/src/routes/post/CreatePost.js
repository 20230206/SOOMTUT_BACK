import React, { useState } from "react";
import { Button, Dropdown } from "react-bootstrap";
import styles from "../../assets/styles/poststyle.module.css"
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

const Category_List = [ 
    { id:0, name:"카테고리" },
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

function CreatePost() {
    const navigate = useNavigate();

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

    const [curCategory, setCurCategory] = useState(Category_List[0])
    const [categoryId, setCategoryId] = useState(0);
    const SelectCategory = (type) => {
        setCurCategory(Category_List[type])
        setCategoryId(Category_List[type].id)
    }
    
    const [contents, setContents] = useState("");
    const InputContents = (event) => {
        setContents(event.target.value)
    }

    const RequestCreatePost = () => {
        var data = JSON.stringify({
            "title": title,
            "image": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPple0XLVI1C5Qk6WZRtHEvgc8Ns7_CW09qeC3IlzUIw&s",
            "content": contents,
            "category": categoryId,
            "fee": fee
          });
          
          var config = {
            method: 'post',
          maxBodyLength: Infinity,
            url: 'http://localhost:8080/createpost',
            headers: { 
              'Authorization': localStorage.getItem("Authorization"),
              'Content-Type': 'application/json'
            },
            data : data
          };
          
          axios(config)
          .then(function (response) {
            navigate("/posts/"+response.data.postId)
          })
          .catch(function (error) {
            console.log(error);
          });
          
    }

    return (
        <div className={styles.wrapper}>
            <div className={styles.headbox}>
                <Link to="/posts"> <Button className={styles.headboxbutton}> 돌아가기 </Button> </Link>
                <div className={styles.headboxtext}><span> 글쓰기 </span></div>
                <Button
                 className={styles.headboxbutton}
                 onClick={() => RequestCreatePost()}>
                    완료
                </Button>
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

            <div className={styles.categoryandfeebox}>

            <div className={styles.categorybox}>
             <Dropdown>
              <Dropdown.Toggle  id="dropdown-basic"  className={styles.categorydropdown}> {curCategory.name}
              </Dropdown.Toggle>
 
              <Dropdown.Menu>
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

            <div className={styles.feebox}>
                <textarea
                value={fee}
                className={styles.feeinput}
                placeholder="가격(\)" 
                onChange={InputFee}
                />
            </div>
            
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