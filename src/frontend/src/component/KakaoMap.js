/* global kakao */
import React, { useEffect, useState } from "react";

const KakaoMap = (props) => {

    const [pos, setPos] = useState();
    
    useEffect(()=>{
        var container = document.getElementById('map');
        var options = {
            center: new kakao.maps.LatLng(37.365264512305174, 127.10676860117488),
            level: 3
        };
        var map = new kakao.maps.Map(container, options);
        setPos(props)
    }, [])


    return (
        <div style={{ display:'flex' }}>
            <div id="map" style={{
                    width:'400px',
                    height:'300px',
            }}> </div>
        </div>
    );
}

export default KakaoMap;