package com.bind.ptw.be.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bind.ptw.be.dao.ContestDao;
import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.MatchBeanList;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.QuestionBeanList;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.dto.UserContestAnswer;
import com.bind.ptw.be.services.ContestService;
import com.bind.ptw.be.services.util.ContestBeanValidator;
import com.bind.ptw.be.services.util.TournamentBeanValidator;
import com.bind.ptw.be.util.PTWConstants;
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
	public QuestionBeanList getMatchQuestions(MatchBean matchBean){
		QuestionBeanList retQuestionBeanList = new QuestionBeanList();
		try{
			TournamentBeanValidator.vaidateRequest(matchBean);
			ContestBeanValidator.validateMatchId(matchBean.getMatchId());
			List<QuestionBean> questionBeanList = contestDao.getMatchQuestions(matchBean);
			retQuestionBeanList.setQuestionBeanList(questionBeanList);
		}catch(PTWException exception){
			retQuestionBeanList.setResultCode(exception.getCode());
			retQuestionBeanList.setResultDescription(exception.getDescription());
		}
		return retQuestionBeanList;
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
			for (ContestBean retContestBean : contestBeanList) {
				List<QuestionBean> questionList = contestDao.getQuestion(retContestBean);
				if(questionList != null && !questionList.isEmpty()){
					for (QuestionBean questionBean : questionList) {
						List<AnswerOptionBean> answers = contestDao.getAnswersForQuestion(questionBean);
						questionBean.setAnswerOptionList(answers);
					}
				}
				retContestBean.setQuestionList(questionList);
			}
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

	@Override
	public QuestionBean createQuestion(QuestionBean questionBean) {
		QuestionBean retBean = null;
		try{
			TournamentBeanValidator.vaidateRequest(questionBean);
			ContestBeanValidator.validateCreateQuestion(questionBean, tournamentDao, contestDao);
			retBean = contestDao.createQuestion(questionBean);
			
			if(questionBean.getAnswerTypeId() == 1){
				Integer contestId = questionBean.getContestId();
				ContestBean contestBean = new ContestBean();
				contestBean.setContestId(contestId);
				List<ContestBean> contestList = contestDao.getMatches(contestBean, false);
				if(contestList != null && !contestList.isEmpty()){
					ContestBean dbContestBean = contestList.get(0);
					Integer matchId = dbContestBean.getMatchId();
					MatchBean matchBean = new MatchBean();
					matchBean.setMatchId(matchId);
					List<TournamentTeamBean> tournamentTeams = contestDao.getMatchTeams(matchBean);
					if(tournamentTeams != null && !tournamentTeams.isEmpty()){
						List<AnswerOptionBean> answerOptionBeanList = new ArrayList<AnswerOptionBean>();
						for (TournamentTeamBean tournamentTeam : tournamentTeams) {
							AnswerOptionBean answerOptionBean = new AnswerOptionBean();
							answerOptionBean.setAnswerOptionStr(tournamentTeam.getTeamName());
							answerOptionBeanList.add(answerOptionBean);
						}
						QuestionBean answerQuestionBean = new QuestionBean();
						answerQuestionBean.setQuestionId(retBean.getQuestionId());
						answerQuestionBean.setAnswerOptionList(answerOptionBeanList);
						contestDao.createAnswerOptions(answerQuestionBean);
					}
				}
				
			}
			
		}catch(PTWException exception){
			retBean = new QuestionBean();
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean updateQuestion(QuestionBean questionBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(questionBean);
			ContestBeanValidator.validateUpdateQuestion(questionBean, tournamentDao, contestDao);
			contestDao.updateQuestion(questionBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public ContestBean getContestQuestion(ContestBean contestBean) {
		ContestBean retBean = new ContestBean();
		try{
			TournamentBeanValidator.vaidateRequest(contestBean);
			ContestBeanValidator.validateContestId(contestBean.getContestId());
			List<QuestionBean> questionList = contestDao.getQuestion(contestBean);
			retBean.setContestId(contestBean.getContestId());
			retBean.setQuestionList(questionList);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean deleteQuestion(ContestBean contestBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(contestBean);
			ContestBeanValidator.validateContestId(contestBean.getContestId());
			List<QuestionBean> questionList = contestDao.getQuestion(contestBean);
			if(questionList != null && !questionList.isEmpty()){
				for (QuestionBean questionBean : questionList) {
					contestDao.deleteQuestion(questionBean);
				}
			}
			
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean createAnswerOptions(QuestionBean questionBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(questionBean);
			ContestBeanValidator.validateQuestionId(questionBean.getQuestionId());
			ContestBeanValidator.validateAnswerOption(questionBean.getAnswerOptionList());
			contestDao.createAnswerOptions(questionBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean updateAnswerOption(AnswerOptionBean answerOptionBean) {
		BaseBean retBean = new BaseBean();
		try{
			if(answerOptionBean == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_REQUEST, PTWConstants.ERROR_DESC_INVALID_REQUEST);
			}
			ContestBeanValidator.validateAnswerOptionId(answerOptionBean.getAnswerOptionId());
			contestDao.updateAnswerOption(answerOptionBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}
	
	

	@Override
	public QuestionBean getAnswersForQuestion(QuestionBean questionBean) {
		QuestionBean retBean = new QuestionBean();
		try{
			TournamentBeanValidator.vaidateRequest(questionBean);
			ContestBeanValidator.validateQuestionId(questionBean.getQuestionId());
			List<AnswerOptionBean> answers = contestDao.getAnswersForQuestion(questionBean);
			retBean.setQuestionId(questionBean.getQuestionId());
			retBean.setAnswerOptionList(answers);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean deleteAnswerOption(AnswerOptionBean answerOptionBean) {
		BaseBean retBean = new BaseBean();
		try{
			if(answerOptionBean == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_REQUEST, PTWConstants.ERROR_DESC_INVALID_REQUEST);
			}
			ContestBeanValidator.validateAnswerOptionId(answerOptionBean.getAnswerOptionId());
			contestDao.deleteAnswerOption(answerOptionBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean submitUserAnswer(UserContestAnswer userContestAsnwer){
		BaseBean retBean = new BaseBean();
		try{
			ContestBeanValidator.validateUserAnswer(userContestAsnwer, contestDao);
			contestDao.addUserAnswer(userContestAsnwer);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		
		return retBean;
	}
	
}
