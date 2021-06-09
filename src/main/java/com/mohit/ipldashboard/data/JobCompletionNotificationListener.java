package com.mohit.ipldashboard.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.mohit.ipldashboard.model.Team;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  /**Uses JDBC API underneath. No need to create connection and perform all methods
  private final JdbcTemplate jdbcTemplate;

  We'll instead use EntityManager which belongs to JPA. Provides CRUD methods whereas JDBC Template needs sql queries.**/

  @Autowired
  private final EntityManager em;


  @Autowired
  public JobCompletionNotificationListener(EntityManager em) {
    this.em = em;
  }

  @Override
  @Transactional //Start a transaction when method begins and ends when method ends
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED!");

      Map<String, Team> teamData = new HashMap<>();

      //Fetching teams and their total matches from team1
      em.createQuery("Select m.team1, Count(*) from Match m Group By m.team1", Object[].class)
        .getResultList()  //Until here it will give an Object[]. each Object will contain a String and a number
        .stream()
        .map(obj -> new Team((String) obj[0], (long) obj[1])) //Will map each object of that array to a Team
        .forEach(team -> teamData.put(team.getTeamName(), team)); //Adding each team to TeamData map

      /*For each row in Match table, winner could be either team1 or team2. We have fetched only team1's total matches. Same team can also be in team2
        We can use union team1 and team2 but JPQL doesn't support union. So, fetching one by one.*/

      //Fetching teams and their total matches from team2
      em.createQuery("Select m.team2, Count(*) from Match m Group By m.team2", Object[].class)
        .getResultList()
        .stream()
        .forEach(obj -> {
          Team team = teamData.get((String) obj[0]);
          // Handling case when a team has always played as team2. So, it won't be present in teamData map
          if(team == null)
            teamData.put((String) obj[0], new Team((String) obj[0], (long) obj[1]));
          else
            team.setTotalMatches(team.getTotalMatches() + (long) obj[1]);
        });

        //Setting match wins
        em.createQuery("Select m.matchWinner , count(*) from Match m Group By m.matchWinner", Object[].class)
          .getResultList()
          .stream()
          .forEach(obj -> {
            Team team = teamData.get((String) obj[0]);
            if(team != null)
              team.setTotalWins((long) obj[1]);
          });

          teamData.values().forEach(team -> em.persist(team));

          teamData.values().forEach(team -> System.out.println(team));

    }
  }
}