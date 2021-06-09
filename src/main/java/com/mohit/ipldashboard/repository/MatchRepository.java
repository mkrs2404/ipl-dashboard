package com.mohit.ipldashboard.repository;

import java.util.List;

import com.mohit.ipldashboard.model.Match;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

//CrudRepository takes 2 types. 1-> What type you're fetching. 2-> What id you're providing to fetch
public interface MatchRepository extends CrudRepository<Match, Long>{

    /*We want to fetch all matches of a team. Team could be either in team1 or team2. JPA allows such method names where query will be done on team1(field in Match class) or team1(field in Match class)
    So, we need to provide 2 params. It will return all matches where team1 = teamName1 or team2 = teamName2. Orders By Date field of Match in descending order. Thus, method name helps to define query in JPA */
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    /*Instead of creating a DAO class which would call the getByTeam1OrTeam2OrderByDateDesc method since we don't want pageable(Data Related) logic and weird method names in Controller,
    we can create a deafult method in Interface which is allowed since Java 8*/
    
    default List<Match> findLatestMatchesByTeam(String teamName, int count){
        //To get data in pages. Give page 0 of size count since we need only recent 'count' matches only
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
    
}
