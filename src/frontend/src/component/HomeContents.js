import styles from "../assets/styles/homecontents.module.css"
import { Link } from "react-router-dom";

function HomeContents() {
    return (
        <div className={styles.wrapper}>
         <br></br>
         <div className={styles.frontbanner}>

         </div>
         <br></br>
         <div className={styles.buttonbox}>
            <button className={styles.button}> <Link to="/posts">  나의 튜터 찾기 </Link> </button>  
            <button className={styles.button}> 내 주변의 튜터 찾기 </button>
         </div>
         <br></br>
         <div className={styles.backcontents}>

         </div>
        </div>
    );
}
export default HomeContents;