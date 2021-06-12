import React, {useEffect, useState} from 'react'
import { MatchDetailCard } from '../components/MatchDetailCard'
import { MatchSmallCard } from '../components/MatchSmallCard'

export const TeamPage = () =>{

  const [team, setTeam] = useState({matches: []});

  useEffect(
        () => {
            const fetchMatches = async () => {
                const response = await fetch('http://localhost:8080/teams/Delhi Capitals')
                const data = await response.json();
                setTeam(data);
            };
            fetchMatches();
        }, [] //Call the useEffect only when something changes in this array. Here array is empty, so, useEffect is not going to call itself again and again. 
              //We are fetching data and putting into state. this is changing Team . Hence, useState is called again and again
        
    )
  return (
    <div className="TeamPage">
      <h1>{team.teamName}</h1>
      <MatchDetailCard match={team.matches[0]}/>
      {/* Calling MatchSmallCard as many times as there are elements in the array 'team.matches' 
        Slice slices the array from1 to n as we are already displaying the latest match in Detail Card*/}
      {team.matches.slice(1).map(match => <MatchSmallCard match={match}/>)}  
    </div>
  );
}

export default TeamPage;
