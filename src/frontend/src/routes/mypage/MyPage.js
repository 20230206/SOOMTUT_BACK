/*global kakao*/
import React, {
    useState,
    useEffect
} from "react";

import { 
    Button,
    Card,
    Modal
} from "react-bootstrap";
import { Link } from "react-router-dom"

import styles from "../../assets/styles/mypage.module.css"
import axios from "axios"

import Postcode from '@actbase/react-daum-postcode';

function MyPage() {
    var geocoder = new kakao.maps.services.Geocoder();
    const [myInfo, setMyInfo] = useState([]);
  
    const [location, setLocation] = useState("서울특별시 서초구 반포동");
    const [posX, setPosX] = useState(37.365264512305174);
    const [posY, setPosY] = useState(127.10676860117488);

    const GetMyInfo = () => {
        axios.defaults.withCredentials = true;
                
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
            setMyInfo(response.data)
            setLocation(response.data.address)
        })
        .catch(function (error) {
            console.log(error);
        });
  
    }
    
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    useEffect(() => {
        GetMyInfo();
    }, [])

    useEffect(() => {
        AddressToMapXY();
    }, [location])

    useEffect(() => {
        MoveMap();
    }, [posX, posY])

    const callback = (result, status) => {
        if(status === kakao.maps.services.Status.OK) {
            setPosX(result[0].y);
            setPosY(result[0].x);
        }
    }

    const AddressToMapXY = () => {
        geocoder.addressSearch(location, callback);
    }

    const MoveMap = () => {
        var container = document.getElementById('map');
        var options = {
            center: new kakao.maps.LatLng(posX, posY),
            level: 2
        };
        var map = new kakao.maps.Map(container, options);

        var markerPosition = new kakao.maps.LatLng(posX, posY)
        var marker = new kakao.maps.Marker({
            position: markerPosition
        });

        marker.setMap(map);
        map.setDraggable(false);
        map.setZoomable(false);
    }

    const ChangeLocation = (address) => {
        var data = JSON.stringify({
            "vectorX": posX,
            "vectorY": posY,
            "address": address
          });
          
          var config = {
            method: 'put',
          maxBodyLength: Infinity,
            url: 'http://localhost:8080/updatelocation',
            headers: { 
              'Content-Type': 'application/json'
            },
            data : data
          };
          
          axios(config)
          .then(function (response) {

          })
          .catch(function (error) {
            console.log(error);
          });
          
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

                    <Modal show={show} onHide={handleClose}>
                    <Modal.Body style={{height:"540px"}}>
                        <Postcode
                        style={{ width: 460, height: 320 }}
                        jsOptions={{ animation: true, hideMapBtn: true }}
                        onSelected={data => {
                            console.log(JSON.stringify(data))
                            setLocation(data.address)
                            ChangeLocation(data.address)
                            
                            handleClose();
                        }}
                        />
                    </Modal.Body>
                    </Modal>

                    <div  className={styles.mylocations}>
                        <Card>
                        <Card.Header className={styles.locationtitle}> 내 활동 지역 
                            <Button
                             className={styles.locationbutton}
                             onClick={() => handleShow()}
                             > 위치 수정 </Button>
                        </Card.Header>
                        <Card.Body>
                            <blockquote className="blockquote mb-0">
                            <p>
                                {location}
                            </p>
                            <footer className="blockquote-footer">
                                
                            <div style={{ display:'flex' }}>
                                <div id="map" style={{
                                        width:'400px',
                                        height:'300px',
                                }}> </div>
                            </div>

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