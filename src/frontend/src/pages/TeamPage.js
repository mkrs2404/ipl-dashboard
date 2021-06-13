import React, {useEffect, useState} from 'react'
import { useParams } from 'react-router';
import { MatchDetailCard } from '../components/MatchDetailCard'
import { MatchSmallCard } from '../components/MatchSmallCard'
import { PieChart } from 'react-minimal-pie-chart';

import './TeamPage.scss'
import { Link } from 'react-router-dom';


export const TeamPage = () =>{

  const [team, setTeam] = useState({matches: []});
  const {teamName} = useParams();
  useEffect(
        () => {
            const fetchTeam = async () => {
                const response = await fetch(`http://localhost:8080/teams/${teamName}`)
                const data = await response.json();
                setTeam(data);
            };
            fetchTeam();
        }, [teamName] //Call the useEffect only when something changes in this array. Here array is empty, so, useEffect is not going to call itself again and again. 
              //We are fetching data and putting into state. this is changing Team . Hence, useState is called again and again      
  );
  
  if(!team || !team.teamName){
        return <h1>Team Not Found</h1>
  }
  
  return (
    <div className="TeamPage">
      <div className="team-name-section"><h1 className="team-name">{team.teamName}</h1></div>
      <div className="win-loss-section">
        Wins / Losses
        <PieChart
          data={[
            { title: 'Wins', value: team.totalWins, color: '#4da375' },
            { title: 'Losses', value: team.totalMatches - team.totalWins, color: '#a34d5d' },
          ]}
        />
      </div>
      <div className="match-detail-section">
        <h3>Latest Matches</h3>
        <MatchDetailCard teamName={team.teamName} match={team.matches[0]}/>
      </div>
      {/* Calling MatchSmallCard as many times as there are elements in the array 'team.matches' 
        Slice slices the array from1 to n as we are already displaying the latest match in Detail Card*/}
      {team.matches.slice(1).map(match => <MatchSmallCard key={match.id} teamName={team.teamName} match={match}/>)}  
      <div className="more-link">
        <Link to={`/teams/${teamName}/matches/${process.env.REACT_APP_DATA_START_YEAR}`}>More ></Link>
      </div>
    </div>
  );
}

export default TeamPage;
