import React from 'react';
import { Switch, Route } from 'react-router-dom';

import MainView from './views/MainView';
import RegisterView from './views/RegisterView';
import LoginView from './views/LoginView';
import CreateStudyView from './views/CreateStudyView';
import MyStudyView from './views/MyStudyView';
import NotFoundView from './views/NotFoundView';

import Auth from './hoc/auth';

import './asset/GlobalStyle.css';
import MessageView from './views/MessageView';

// null  =>  누구나 가능
// true  =>  로그안 한 사람만 가능
// fasle =>  로그인 한 사람은 불가능

function App() {
  return (
    <Switch>
      <Route exact path="/" component={Auth(MainView, null)} />
      <Route exact path="/message/send" component={Auth(MessageView, null)} />
      <Route exact path="/register" component={Auth(RegisterView, false)} />
      <Route exact path="/login" component={Auth(LoginView, false)} />
      <Route exact path="/study/create" component={Auth(CreateStudyView, true)} />
      <Route exact path="/my/info" component={Auth(MyStudyView, true)} />
      <Route exact path="*" component={Auth(NotFoundView, null)} />
    </Switch>
  );
}

export default App;
