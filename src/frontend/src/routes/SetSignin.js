import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import axios from 'axios'

function SetSignin () {
    axios.defaults.withCredentials = true;

    const navigate = useNavigate();
    const params = useParams().access;
    const [loading, SetLoading] = useState(false);

    const SetToken = () => {
        var config = {
            method: 'get',
        maxBodyLength: Infinity,
            url: 'http://localhost:8080/auth/createrefreshforoauth2',
            headers: { 
                'Authorization': params
            }
        };
        
        axios(config)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
            SetLoading(response.data);
        })
        .catch(function (error) {
            console.log(error);
        });
        
    }

    useEffect(() => {
        SetToken();
    }, [])

    useEffect(() => {
        navigate("/");
    }, [loading])

    return (
        <div>
            <SetToken />
        </div>
    );
}

export default SetSignin;