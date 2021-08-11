import React from 'react';
import { Switch, Route } from 'react-router-dom';

import MainView from './views/MainView';
import NotFoundView from './views/NotFoundView';

function App() {
  return (
    <Switch>
      <Route exact path="/" component={MainView} />
      <Route exact path="*" component={NotFoundView} />
    </Switch>
  );
}

export default App;
