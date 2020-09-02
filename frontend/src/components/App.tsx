import React, { Component } from 'react';
import logo from '../resources/logo.png';
import './App.css';
import StocksTable from './StocksTable';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
        </header>
        <StocksTable />
      </div>
    );
  }
}

export default App;
