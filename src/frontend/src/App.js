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
import MyFavList from "./routes/MyFavList";
import MyClassList from "./routes/MyClassList";
import MyClassedList from "./routes/MyClassedList";
import MyChatList from "./routes/MyChatList";
import PostList from "./routes/PostList";


function App() {
  return (
    <Router>
      <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/signin" element={<Signin />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/setsignin/:access" element={<SetSignin />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/mypage/favlist" element={<MyFavList />} />
          <Route path="/mypage/myclasslist" element={<MyClassList/>} />
          <Route path="/mypage/myclassedlist" element={<MyClassedList />} />
          <Route path="/mypage/chat" element={<MyChatList />} />
          <Route path="/posts" element={<PostList />} />
      </Routes>
    </Router>
  );
}

export default App;
