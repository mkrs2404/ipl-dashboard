import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import TeamPage from './pages/TeamPage';
import { MatchPage } from './pages/MatchPage';

function App() {
  return (
    <div className="App">
      <Router>
        {/* Switch is required because url will match both the routes. So, both will be loaded. Switch loads the first match */}
        <Switch> 

          <Route path="/teams/:teamName/matches/:year">
            <MatchPage />
          </Route>

          <Route path="/teams/:teamName">
            <TeamPage />
          </Route>
          
        </Switch>
      </Router>
    </div>
  );
}

export default App;
