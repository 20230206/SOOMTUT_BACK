import React, { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

function SetSignin () {
    const navigate = useNavigate();
    const params = useParams().access;

    const SetToken = () => {
        localStorage.setItem('Authorization', params)
    }

    useEffect(() => {
        navigate("/")
    })

    return (
        <div>
            <SetToken />
        </div>
    );
}

export default SetSignin;