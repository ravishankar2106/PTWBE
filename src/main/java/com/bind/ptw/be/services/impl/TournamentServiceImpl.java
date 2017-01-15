package com.bind.ptw.be.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.SportTypeBean;
import com.bind.ptw.be.dto.SportTypeBeanList;
import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.dto.TeamTypeBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentBeanList;
import com.bind.ptw.be.services.TournamentService;
import com.bind.ptw.be.services.util.TournamentBeanValidator;
import com.bind.ptw.be.util.PTWException;

@Service("tournamentService")
@Transactional
public class TournamentServiceImpl implements TournamentService{

	@Autowired
	TournamentDao tournamentDao;
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public TournamentBean createTournament(TournamentBean inputTournamentBean){
		TournamentBean tournamentBeanResponse;
		try{
			TournamentBeanValidator.vaidateRequest(inputTournamentBean);
			TournamentBeanValidator.validateCreateTournament(inputTournamentBean, tournamentDao);
			tournamentBeanResponse = tournamentDao.createTournament(inputTournamentBean);
		}catch(PTWException exception){
			exception.printStackTrace();
			tournamentBeanResponse = new TournamentBean();
			tournamentBeanResponse.setResultCode(exception.getCode());
			tournamentBeanResponse.setResultDescription(exception.getDescription());
		}	
		return tournamentBeanResponse;
	}

	@Override
	public BaseBean deleteTournament(TournamentBean tournamentBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(tournamentBean);
			TournamentBeanValidator.validateTournamentId(tournamentBean.getTournamentId());
			tournamentDao.deleteTournament(tournamentBean);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public TournamentBeanList getOngoingTournament(TournamentBean tournamentBean){
		TournamentBeanList tournamentBeanList = new TournamentBeanList();
		List<TournamentBean> tournaments = tournamentDao.getTournament(tournamentBean, true);
		tournamentBeanList.setTournamentList(tournaments);
		return tournamentBeanList;
	}
	
	@Override
	public BaseBean updateTournament(TournamentBean tournamentBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(tournamentBean);
			TournamentBeanValidator.validateUpdateTournament(tournamentBean, tournamentDao);
			tournamentDao.updateTournament(tournamentBean);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean archieveTournament(TournamentBean tournamentBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(tournamentBean);
			TournamentBeanValidator.validateTournamentId(tournamentBean.getTournamentId());
			tournamentDao.archieveTournament(tournamentBean);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public TeamTypeBeanList getTeamType() {
		TeamTypeBeanList teamTypeBeanList = new TeamTypeBeanList();
		List<TeamTypeBean> teamTypeList = tournamentDao.getTeamTypes(new TeamTypeBean());
		teamTypeBeanList.setTeamTypes(teamTypeList);
		return teamTypeBeanList;
	}

	@Override
	public SportTypeBeanList getSportType() {
		SportTypeBeanList sportTypeBeanResponse= new SportTypeBeanList();
		List<SportTypeBean> sportTypeBeanList = tournamentDao.getSportTypes(new SportTypeBean());
		sportTypeBeanResponse.setSportTypes(sportTypeBeanList);
		return sportTypeBeanResponse;
	}

	
}
