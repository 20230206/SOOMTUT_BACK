import React, { useEffect, useState } from "react";
import Dropdown from 'react-bootstrap/Dropdown';

function MyDropdown() {

    const [values, setValues] = useState([]);
    const [title, setTitle] = useState("카테고리");

    useEffect(() => {
        setMenu();
    }, [])

    const setMenu = () => {
      // db에 카테고리 모든 조회 요청
    }

    const SelectMenu = () => {

    }

    return (
      <Dropdown>
      <Dropdown.Toggle  id="dropdown-basic"> {title}
      </Dropdown.Toggle>

      <Dropdown.Menu>
        <Dropdown.Item onClick={() => {}}>  </Dropdown.Item>
        <Dropdown.Item >  </Dropdown.Item>
        <Dropdown.Item >  </Dropdown.Item>
      </Dropdown.Menu>
      </Dropdown>
    );


}
export default MyDropdown;