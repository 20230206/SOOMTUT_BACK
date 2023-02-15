import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom"

import Home from "./routes/Home"
import Signin from "./routes/Signin";
import Signup from "./routes/Signup";
import SetSignin from "./routes/SetSignin"
import MyPage from "./routes/MyPage";
import FavList from "./routes/FavList";
import MyClassList from "./routes/MyClassList";
import MyClassedList from "./routes/MyClassedList";
import MyChatList from "./routes/MyChatList";

function App() {
  return (
    <Router>
      <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/signin" element={<Signin />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/setsignin/:access" element={<SetSignin />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/mypage/favlist" element={<FavList />} />
          <Route path="/mypage/myclasslist" element={<MyClassList/>} />
          <Route path="/mypage/myclassedlist" element={<MyClassedList />} />
          <Route path="/mypage/chat" element={<MyChatList />} />
      </Routes>
    </Router>
  );
}

export default App;
