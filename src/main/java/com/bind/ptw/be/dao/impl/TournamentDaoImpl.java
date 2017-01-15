package com.bind.ptw.be.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dto.SportTypeBean;
import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.entities.SportType;
import com.bind.ptw.be.entities.TeamType;
import com.bind.ptw.be.entities.Tournament;
import com.bind.ptw.be.entities.TournamentHome;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

@Repository("tournamentDao")
public class TournamentDaoImpl implements TournamentDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	private Session getSession() {
	    return hibernateTemplate.getSessionFactory().getCurrentSession();
	}
	
	public TournamentBean createTournament(TournamentBean tournament){
		Tournament tournamentEntity = new Tournament();
		tournamentEntity.setTournamentName(tournament.getTournamentName());
		tournamentEntity.setTournamentDescription(tournament.getTournamentDesc());
		tournamentEntity.setTournamentVenue(tournament.getTournamentVenue());
		tournamentEntity.setStartDate(tournament.getStartDate());
		tournamentEntity.setEndDate(tournament.getEndDate());
		
		TeamType teamType = new TeamType();
		teamType.setTeamTypeId(tournament.getTeamTypeId());
		tournamentEntity.setTeamType(teamType);
		
		SportType sportType = new SportType();
		sportType.setSportTypeId(tournament.getSportTypeId());
		tournamentEntity.setSportType(sportType);
		
		tournamentEntity.setArchievedFlag(false);
		
		TournamentHome tournamentHome = new TournamentHome(getSession());
		tournamentHome.persist(tournamentEntity);
		tournament.setTournamentId(tournamentEntity.getTournamentId());
		return tournament;
	}
	
	public List<TournamentBean> getTournament(TournamentBean tournamentBean, Boolean onlyOngoingTournament){
		List<TournamentBean> resultTournamentBeanList = null;
		TournamentHome tournamentHome = new TournamentHome(getSession());
		List<Tournament> resultTournamentList = tournamentHome.findTournamentByFilters(tournamentBean, onlyOngoingTournament);
		if(resultTournamentList != null && !resultTournamentList.isEmpty()){
			resultTournamentBeanList = new ArrayList<TournamentBean>();
			for (Tournament tournament : resultTournamentList) {
				TournamentBean resultTournamentBean = new TournamentBean();
				resultTournamentBean.setTournamentId(tournament.getTournamentId());
				resultTournamentBean.setTournamentDesc(tournament.getTournamentDescription());
				resultTournamentBean.setTournamentName(tournament.getTournamentName());
				resultTournamentBean.setStartDate(tournament.getStartDate());
				resultTournamentBean.setStartDateStr(StringUtil.convertDateToString(tournament.getStartDate()));
				resultTournamentBean.setEndDate(tournament.getEndDate());
				resultTournamentBean.setEndDateStr(StringUtil.convertDateToString(tournament.getEndDate()));
				resultTournamentBean.setSportTypeId(tournament.getSportType().getSportTypeId());
				resultTournamentBean.setTeamTypeId(tournament.getTeamType().getTeamTypeId());
				resultTournamentBean.setTournamentVenue(tournament.getTournamentVenue());
				resultTournamentBeanList.add(resultTournamentBean);
			}
		}
		return resultTournamentBeanList;
	}
	
	@Override
	public void updateTournament(TournamentBean tournamentBean) throws PTWException {
		TournamentHome tournamentHome = new TournamentHome(getSession());
		Tournament dbTournament = tournamentHome.findById(tournamentBean.getTournamentId());
		if(dbTournament == null){
			throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_ID_NOT_FOUND, PTWConstants.ERROR_DESC_TOURNAMENT_ID_NOT_FOUND);
		}
		dbTournament.setTournamentName(tournamentBean.getTournamentName());
		dbTournament.setTournamentDescription(tournamentBean.getTournamentDesc());
		dbTournament.setTournamentVenue(tournamentBean.getTournamentVenue());
		dbTournament.setStartDate(tournamentBean.getStartDate());
		dbTournament.setEndDate(tournamentBean.getEndDate());
		tournamentHome.merge(dbTournament);
	}
	
	@Override
	public void archieveTournament(TournamentBean tournamentBean) throws PTWException{
		TournamentHome tournamentHome = new TournamentHome(getSession());
		Tournament dbTournament = tournamentHome.findById(tournamentBean.getTournamentId());
		if(dbTournament == null){
			throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_ID_NOT_FOUND, PTWConstants.ERROR_DESC_TOURNAMENT_ID_NOT_FOUND);
		}
		dbTournament.setArchievedFlag(true);
		tournamentHome.merge(dbTournament);
	}

	@Override
	public void deleteTournament(TournamentBean tournamentBean) throws PTWException{
		TournamentHome tournamentHome = new TournamentHome(getSession());
		Tournament dbTournament = tournamentHome.findById(tournamentBean.getTournamentId());
		if(dbTournament == null){
			throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_ID_NOT_FOUND, PTWConstants.ERROR_DESC_TOURNAMENT_ID_NOT_FOUND);
		}
		tournamentHome.remove(dbTournament);
	}
	
	@Override
	public List<SportTypeBean> getSportTypes(SportTypeBean requestSportTypeBean){
		List<SportTypeBean> sportTypeBeanList = null;
		TournamentHome tournamentHome = new TournamentHome(getSession());
		List<SportType> sportTypeList = tournamentHome.findSportType(requestSportTypeBean);
		if(sportTypeList != null && !sportTypeList.isEmpty()){
			sportTypeBeanList = new ArrayList<SportTypeBean>();
			for (SportType sportType : sportTypeList) {
				SportTypeBean sportTypeBean= new SportTypeBean();
				sportTypeBean.setSportTypeId(sportType.getSportTypeId());
				sportTypeBean.setSportTypeName(sportType.getSportTypeName());
				sportTypeBeanList.add(sportTypeBean);
			}
		}
		return sportTypeBeanList;
		
	}
	
	public List<TeamTypeBean> getTeamTypes(TeamTypeBean requestTeamTypeBean){
		List<TeamTypeBean> teamTypeBeanList = null;
		TournamentHome tournamentHome = new TournamentHome(getSession());
		List<TeamType> teamTypeList = tournamentHome.findTeamType(requestTeamTypeBean);
		if(teamTypeList != null && !teamTypeList.isEmpty()){
			teamTypeBeanList = new ArrayList<TeamTypeBean>();
			for (TeamType teamType : teamTypeList) {
				TeamTypeBean teamTypeBean= new TeamTypeBean();
				teamTypeBean.setTeamTypeId(teamType.getTeamTypeId());
				teamTypeBean.setTeamTypeName(teamType.getTeamTypeName());
				teamTypeBeanList.add(teamTypeBean);
			}
		}
		return teamTypeBeanList;
		
	}

	
}
