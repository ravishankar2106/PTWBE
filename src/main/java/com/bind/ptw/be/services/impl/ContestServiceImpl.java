package com.bind.ptw.be.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bind.ptw.be.dao.ContestDao;
import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.MatchBeanList;
import com.bind.ptw.be.services.ContestService;
import com.bind.ptw.be.services.util.ContestBeanValidator;
import com.bind.ptw.be.services.util.TournamentBeanValidator;
import com.bind.ptw.be.util.PTWException;

@Service("contestService")
@Transactional
public class ContestServiceImpl implements ContestService{

	@Autowired
	ContestDao contestDao;
	
	@Autowired
	TournamentDao tournamentDao;
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public MatchBean createMatch(MatchBean matchBean) {
		MatchBean retBean = null;
		try{
			TournamentBeanValidator.vaidateRequest(matchBean);
			ContestBeanValidator.validateCreateMatch(matchBean, tournamentDao, contestDao);
			retBean = contestDao.createMatch(matchBean);
		}catch(PTWException exception){
			retBean = new MatchBean();
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public MatchBeanList getMatches(MatchBean matchBean) {
		MatchBeanList matchBeanList = new MatchBeanList();
		try{
			List<MatchBean> matches = contestDao.getMatches(matchBean);
			matchBeanList.setMatchBeanList(matches);
			matchBeanList.setTournamentId(matchBean.getTournamentId());
		}catch(PTWException exception){
			matchBeanList.setResultCode(exception.getCode());
			matchBeanList.setResultDescription(exception.getDescription());
		}
		
		return matchBeanList;
	}

	@Override
	public BaseBean updateMatchStatus(MatchBean matchBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(matchBean);
			ContestBeanValidator.validateMatchId(matchBean.getMatchId());
			ContestBeanValidator.validateStatusId(matchBean.getMatchStatusId());
			contestDao.updateMatchStatus(matchBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean deleteMatch(MatchBean matchBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(matchBean);
			ContestBeanValidator.validateMatchId(matchBean.getMatchId());
			contestDao.deleteMatch(matchBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public ContestBean createContest(ContestBean contestBean) {
		ContestBean retBean = null;
		try{
			TournamentBeanValidator.vaidateRequest(contestBean);
			ContestBeanValidator.validateCreateContest(contestBean, tournamentDao, contestDao);
			retBean = contestDao.createContest(contestBean);
		}catch(PTWException exception){
			retBean = new ContestBean();
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public ContestBeanList getContests(ContestBean contestBean) {
		ContestBeanList resContestBeanList = new ContestBeanList();
		try{
			TournamentBeanValidator.vaidateRequest(contestBean);
			TournamentBeanValidator.validateTournamentId(contestBean.getTournamentId());
			List<ContestBean> contestBeanList = contestDao.getMatches(contestBean, false);
			resContestBeanList.setContestBeanList(contestBeanList);
		}catch(PTWException exception){
			resContestBeanList.setResultCode(exception.getCode());
			resContestBeanList.setResultDescription(exception.getDescription());
		}
		return resContestBeanList;
	}
	
	@Override
	public ContestBeanList getOngoingContests(ContestBean contestBean) {
		ContestBeanList retContestBeanList = new ContestBeanList();
		try{
			TournamentBeanValidator.vaidateRequest(contestBean);
			TournamentBeanValidator.validateTournamentId(contestBean.getTournamentId());
			List<ContestBean> contestBeanList = contestDao.getMatches(contestBean, true);
			retContestBeanList.setContestBeanList(contestBeanList);
		}catch(PTWException exception){
			retContestBeanList.setResultCode(exception.getCode());
			retContestBeanList.setResultDescription(exception.getDescription());
		}
		return retContestBeanList;
	}

	@Override
	public BaseBean updateContest(ContestBean contestBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(contestBean);
			ContestBeanValidator.validateUpdateContest(contestBean, tournamentDao, contestDao);
			contestDao.updateContest(contestBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean updateContestStatus(ContestBean contestBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(contestBean);
			ContestBean statusContestBean = ContestBeanValidator.validateUpdateContestStatus(contestBean, tournamentDao, contestDao);
			contestDao.updateContest(statusContestBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean deleteContest(ContestBean contestBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(contestBean);
			ContestBeanValidator.validateContestId(contestBean.getContestId());
			contestDao.deleteContest(contestBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}
	
	
}
