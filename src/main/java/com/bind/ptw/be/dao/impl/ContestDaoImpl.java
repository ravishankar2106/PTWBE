package com.bind.ptw.be.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.bind.ptw.be.dao.ContestDao;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.entities.Contest;
import com.bind.ptw.be.entities.ContestHome;
import com.bind.ptw.be.entities.ContestStatus;
import com.bind.ptw.be.entities.ContestType;
import com.bind.ptw.be.entities.Match;
import com.bind.ptw.be.entities.MatchHome;
import com.bind.ptw.be.entities.MatchStatus;
import com.bind.ptw.be.entities.Tournament;
import com.bind.ptw.be.entities.TournamentHome;
import com.bind.ptw.be.entities.TournamentTeam;
import com.bind.ptw.be.entities.TournamentTeamHome;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

@Repository("contestDao")
public class ContestDaoImpl implements ContestDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	private Session getSession() {
	    return hibernateTemplate.getSessionFactory().getCurrentSession();
	}
	
	@Override
	public MatchBean createMatch(MatchBean matchBean) throws PTWException{
		MatchBean retMatchBean = new MatchBean();
		try{
			Tournament tournament = getTournament(matchBean.getTournamentId());
			TournamentTeam teamA = getTournamentTeam(matchBean.getTeamA().getTournamentTeamId());
			if(teamA == null || (!tournament.getTournamentId().equals(teamA.getTournament().getTournamentId()))){
				throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Team A");
			}
			
			TournamentTeam teamB = getTournamentTeam(matchBean.getTeamB().getTournamentTeamId());
			if(teamB == null || (!tournament.getTournamentId().equals(teamB.getTournament().getTournamentId()))){
				throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Team B");
			}
			MatchHome matchHome = new MatchHome(this.getSession());
			Match match = new Match();
			match.setTournament(tournament);
			match.setTeamA(teamA);
			match.setTeamB(teamB);
			match.setMatchDateTime(matchBean.getMatchDateTime());
			
			Integer matchNo = matchBean.getMatchNo();
			if(StringUtil.isEmptyNull(matchNo)){
				matchNo = matchHome.getMatchNo(matchBean.getTournamentId())+ 1;
			}
			match.setMatchNo(matchNo);
			
			String venue = matchBean.getVenue();
			if(StringUtil.isEmptyNull(venue)){
				venue = tournament.getTournamentVenue();
			}
			match.setVenue(venue);
			
			MatchStatus matchStatus = new MatchStatus();
			matchStatus.setMatchStatusId(1);
			match.setMatchStatus(matchStatus);
			
			matchHome.persist(match);
			retMatchBean.setMatchId(match.getMatchId());
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retMatchBean;
	}

	private Tournament getTournament(Integer tournamentId) {
		TournamentHome tournamentHome = new TournamentHome(this.getSession());
		Tournament tournament = tournamentHome.findById(tournamentId);
		return tournament;
	}
	
	private TournamentTeam getTournamentTeam(Integer tournamentTeamId) {
		TournamentTeamHome tournamentTeamHome = new TournamentTeamHome(this.getSession());
		TournamentTeam tournamentTeam = tournamentTeamHome.findById(tournamentTeamId);
		return tournamentTeam;
	}

	@Override
	public List<MatchBean> getMatches(MatchBean matchBean) throws PTWException {
		MatchHome matchHome = new MatchHome(this.getSession());
		List<MatchBean> retMatchBeanList = null;
		try{
			List<Match> dbMatchList = matchHome.findMatchByFilter(matchBean);
			if(dbMatchList != null && !dbMatchList.isEmpty()){
				retMatchBeanList = new ArrayList<MatchBean>();
				for (Match match : dbMatchList) {
					MatchBean retMatchBean = new MatchBean();
					retMatchBean.setMatchId(match.getMatchId());
					retMatchBean.setMatchNo(match.getMatchNo());
					retMatchBean.setTournamentId(match.getTournament().getTournamentId());
					retMatchBean.setVenue(match.getVenue());
					retMatchBean.setMatchDateTime(match.getMatchDateTime());
					retMatchBean.setMatchDateTimeStr(StringUtil.convertDateTImeToString(match.getMatchDateTime()));
					retMatchBean.setMatchStatusId(match.getMatchStatus().getMatchStatusId());
					retMatchBean.setMatchStatusStr(match.getMatchStatus().getMatchStatusName());
					
					retMatchBean.setTeamA(getTournamentTeamBean(match.getTeamA()));
					retMatchBean.setTeamB(getTournamentTeamBean(match.getTeamB()));
					retMatchBeanList.add(retMatchBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retMatchBeanList;
	}

	private TournamentTeamBean getTournamentTeamBean(TournamentTeam tournamentTeam) {
		TournamentTeamBean tournamentTeamBean = new TournamentTeamBean();
		tournamentTeamBean.setTournamentTeamId(tournamentTeam.getTournamentTeamId());
		tournamentTeamBean.setTeamId(tournamentTeam.getTeam().getTeamId());
		tournamentTeamBean.setTeamName(tournamentTeam.getTeam().getTeamName());
		tournamentTeamBean.setTeamShortName(tournamentTeam.getTeam().getTeamShortName());
		return tournamentTeamBean;
	}

	@Override
	public void updateMatchStatus(MatchBean matchBean) throws PTWException {
		try{
			MatchHome matchHome = new MatchHome(this.getSession());
			Match match = matchHome.findById(matchBean.getMatchId());
			if(match == null){
				throw new PTWException(PTWConstants.ERROR_CODE_MATCH_NOT_FOUND, PTWConstants.ERROR_DESC_MATCH_NOT_FOUND);
			}
			MatchStatus newMatchStatus = new MatchStatus();
			newMatchStatus.setMatchStatusId(matchBean.getMatchStatusId());
			match.setMatchStatus(newMatchStatus);
			matchHome.merge(match);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION); 
		}
	}

	@Override
	public void deleteMatch(MatchBean matchBean) throws PTWException {
		try{
			MatchHome matchHome = new MatchHome(this.getSession());
			Match match = matchHome.findById(matchBean.getMatchId());
			if(match == null){
				throw new PTWException(PTWConstants.ERROR_CODE_MATCH_NOT_FOUND, PTWConstants.ERROR_DESC_MATCH_NOT_FOUND);
			}
			matchHome.remove(match);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION); 
		}
	}

	@Override
	public ContestBean createContest(ContestBean contestBean) throws PTWException {
		ContestBean retContestBean = new ContestBean();
		ContestHome contestHome = new ContestHome(this.getSession());
		try{
			Contest contest = new Contest();
			contest.setContestName(contestBean.getContestName());
			contest.setPublishStartDateTime(contestBean.getPublishStartDate());
			contest.setPublishEndDateTime(contestBean.getPublishEndDate());
			contest.setCutoffDateTime(contestBean.getCutoffDate());
			contest.setBonusPoints(contestBean.getBonusPoints());
			
			Tournament tournament = new Tournament();
			tournament.setTournamentId(contestBean.getTournamentId());
			contest.setTournament(tournament);
			
			if(!StringUtil.isEmptyNull(contestBean.getMatchId())){
				MatchHome matchHome = new MatchHome(this.getSession());
				Match match = matchHome.findById(contestBean.getMatchId());
				if(!contestBean.getTournamentId().equals(match.getTournament().getTournamentId())){
					throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_MATCH_INVALID, PTWConstants.ERROR_DESC_CONTEST_MATCH_INVALID);
				}
				match.setMatchId(contestBean.getMatchId());
				contest.setMatch(match);
			}
			
			ContestType contestType = new ContestType();
			if(!StringUtil.isEmptyNull(contestBean.getContestTypeId())){
				contestType.setContestTypeId(contestBean.getContestTypeId());
			}else{
				contestType.setContestTypeId(1);
			}
			contest.setContestType(contestType);
			
			ContestStatus contestStatus = new ContestStatus();
			contestStatus.setContestStatusId(1);
			contest.setContestStatus(contestStatus);
			
			contestHome.persist(contest);
			retContestBean.setContestId(contest.getContestId());
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retContestBean;
	}

	@Override
	public List<ContestBean> getMatches(ContestBean contestBean, Boolean isOngoingContest) throws PTWException {
		List<ContestBean> retContestBeanList = null;
		ContestHome contestHome = new ContestHome(this.getSession());
		try{
			List<Contest> dbContest = contestHome.findContestByFilter(contestBean, isOngoingContest);
			if(dbContest != null && !dbContest.isEmpty()){
				retContestBeanList = new ArrayList<ContestBean>();
				for (Contest contest : dbContest) {
					ContestBean retContestBean = new ContestBean();
					retContestBean.setContestId(contest.getContestId());
					retContestBean.setContestName(contest.getContestName());
					retContestBean.setPublishStartDate(contest.getPublishStartDateTime());
					retContestBean.setPublishStartDateStr(StringUtil.convertDateTImeToString(contest.getPublishStartDateTime()));
					retContestBean.setPublishEndDate(contest.getPublishEndDateTime());
					retContestBean.setPublishEndDateStr(StringUtil.convertDateTImeToString(contest.getPublishEndDateTime()));
					retContestBean.setCutoffDate(contest.getCutoffDateTime());
					retContestBean.setCutoffDateStr(StringUtil.convertDateTImeToString(contest.getCutoffDateTime()));
					retContestBean.setBonusPoints(contest.getBonusPoints());
					retContestBean.setContestTypeId(contest.getContestType().getContestTypeId());
					retContestBean.setContestTypeName(contest.getContestType().getContestTypeName());
					retContestBean.setTournamentId(contest.getTournament().getTournamentId());
					retContestBean.setContestStatusId(contest.getContestStatus().getContestStatusId());
					if(contest.getMatch() != null){
						retContestBean.setMatchId(contest.getMatch().getMatchId());
						retContestBean.setContestTypeName(contest.getContestType().getContestTypeName());
					}
					
					retContestBeanList.add(retContestBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retContestBeanList;
	}

	@Override
	public void updateContest(ContestBean contestBean) throws PTWException {
		ContestHome contestHome = new ContestHome(this.getSession());
		try{
			Contest contest = contestHome.findById(contestBean.getContestId());
			if(contest == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_CONTEST, PTWConstants.ERROR_DESC_INVALID_CONTEST);
			}
			if(!StringUtil.isEmptyNull(contestBean.getContestName())){
				contest.setContestName(contestBean.getContestName());
			}
			if(!StringUtil.isEmptyNull(contestBean.getBonusPoints())){
				contest.setBonusPoints(contestBean.getBonusPoints());
			}
			if(contestBean.getPublishStartDate() != null){
				contest.setPublishStartDateTime(contestBean.getPublishStartDate());
			}
			if(contestBean.getPublishEndDate() != null){
				contest.setPublishEndDateTime(contestBean.getPublishEndDate());
			}
			if(contestBean.getCutoffDate() != null){
				contest.setCutoffDateTime(contestBean.getCutoffDate());
			}
			if(!StringUtil.isEmptyNull(contestBean.getContestTypeId())){
				contest.getContestType().setContestTypeId(contestBean.getContestTypeId());
			}
			if(!StringUtil.isEmptyNull(contestBean.getContestStatusId())){
				contest.getContestStatus().setContestStatusId(contestBean.getContestStatusId());
			}
			contestHome.merge(contest);
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	@Override
	public void deleteContest(ContestBean contestBean) throws PTWException {
		ContestHome contestHome = new ContestHome(this.getSession());
		try{
			Contest contest = contestHome.findById(contestBean.getContestId());
			if(contest == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_CONTEST, PTWConstants.ERROR_DESC_INVALID_CONTEST);
			}
			contestHome.remove(contest);
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
			
	}
	
	
	
	
}
