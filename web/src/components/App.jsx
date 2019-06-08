
import React from 'react';
import { Switch, Route } from 'react-router';
import { BrowserRouter } from 'react-router-dom';

import SignIn from './homeComponents/SignIn';
import SignUp from './homeComponents/SignUp';
import Home from './Home';

export default class App extends React.Component {
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route path="/register" component={SignUp} />
          <Route path="/signIn/:id" component={SignIn} />
          <Route path="/home" component={Home} />
          <Route path="/" component={Home} />
        </Switch>
      </BrowserRouter>
    );
  }
}