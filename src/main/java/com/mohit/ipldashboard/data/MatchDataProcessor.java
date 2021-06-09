package com.mohit.ipldashboard.data;

import java.time.LocalDate;

import com.mohit.ipldashboard.model.Match;

import org.springframework.batch.item.ItemProcessor;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {
  
    @Override
    public Match process(final MatchInput matchInput) throws Exception {
      
        Match match = new Match();
        match.setId(Integer.parseInt(matchInput.getId()));
        match.setCity(matchInput.getCity());

        match.setDate(LocalDate.parse(matchInput.getDate()));

        //First team who bats is team1
        String firstInningsTeam , secondInningsTeam;

        if("bat".equals(matchInput.getToss_decision())){
            firstInningsTeam = matchInput.getToss_winner();
            secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) ?
                                matchInput.getTeam2() : matchInput.getTeam1();
        }

        else{
            firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) ?
                               matchInput.getTeam2() : matchInput.getTeam1();
            secondInningsTeam = matchInput.getToss_winner();
        }

        match.setTeam1(firstInningsTeam);
        match.setTeam2(secondInningsTeam);

        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setResult(matchInput.getResult());
        match.setVenue(matchInput.getVenue());
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setMatchWinner(matchInput.getWinner());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());

        return match;
    }
  
  }
