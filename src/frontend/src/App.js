import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom"

import Home from "./routes/Home"
import Signin from "./routes/Signin";
import Signup from "./routes/Signup";
import SetSignin from "./routes/SetSignin"

import MyPage from "./routes/mypage/MyPage";
import MyFavList from "./routes/mypage/MyFavList";
import MyClassList from "./routes/mypage/MyClassList";
import MyClassedList from "./routes/mypage/MyClassedList";
import MyChatList from "./routes/mypage/MyChatList";

import PostList from "./routes/post/PostList";
import CreatePost from "./routes/post/CreatePost"
import GetPost from "./routes/post/GetPost";


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
          <Route path="/posts/:id" element={<GetPost />} />
          <Route path="/posts/create" element={<CreatePost />} />
      </Routes>
    </Router>
  );
}

export default App;
