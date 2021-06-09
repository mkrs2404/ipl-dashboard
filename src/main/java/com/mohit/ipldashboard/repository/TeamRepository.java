package com.mohit.ipldashboard.repository;

import com.mohit.ipldashboard.model.Team;

import org.springframework.data.repository.CrudRepository;

//CrudRepository takes 2 types. 1-> What type you're fetching. 2-> What id you're providing to fetch
public interface TeamRepository extends CrudRepository<Team, Long> {
    
    /* JPA looks at method name to find what implmentation should be. Since it starts with findBy.
    It will start looking after findBy. Hence, will query for teamName. It will look at the Team(Return type) table.
    Search for all records whose teamName(method name (should match with field in Team table)) match the method parameter.*/
    Team findByTeamName(String teamName);
}
