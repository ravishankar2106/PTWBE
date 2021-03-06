package com.bind.ptw.be.rest;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.AnswerTypeBeanList;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.MatchBeanList;
import com.bind.ptw.be.dto.PossibleAnswerBean;
import com.bind.ptw.be.dto.PrizeContestBean;
import com.bind.ptw.be.dto.PrizeContestBeanList;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserGroupBeanList;
import com.bind.ptw.be.services.ContestService;

@RestController
@RequestMapping("/contest")
public class ContestRest {

	@Autowired
	ContestService contestService;
	
	@PostMapping("/createMatch")
	public MatchBean createMatch(@RequestBody MatchBean matchBean){
		return contestService.createMatch(matchBean);
	}
	
	@PostMapping("/getMatch")
	public MatchBeanList getMatches(@RequestBody MatchBean matchBean){
		return contestService.getMatches(matchBean);
	}
	
	@PostMapping("/updateMatch")
	public BaseBean updateMatch(@RequestBody MatchBean matchBean){
		return contestService.updateMatch(matchBean);
	}
	
	@PostMapping("/deleteMatch")
	public BaseBean deleteMatch(@RequestBody MatchBean matchBean){
		return contestService.deleteMatch(matchBean);
	}
	
	@PostMapping("/createContest")
	public ContestBean createContest(@RequestBody ContestBean contestBean){
		return contestService.createContest(contestBean);
	}
	
	@PostMapping("/getContest")
	public ContestBeanList getContests(@RequestBody ContestBean contestBean){
		return contestService.getContests(contestBean);
	}
	
	@PostMapping("/updateContest")
	public BaseBean updateContest(@RequestBody ContestBean contestBean){
		return contestService.updateContest(contestBean);
	}
	
	@PostMapping("/updateContestStatus")
	public BaseBean updateContestStatus(@RequestBody ContestBean contestBean){
		return contestService.updateContestStatus(contestBean);
	}
	
	@PostMapping("/deleteContest")
	public BaseBean deleteContest(@RequestBody ContestBean contestBean){
		return contestService.deleteContest(contestBean);
	}
	
	@PostMapping("/processContest")
	public BaseBean processContests(@RequestBody ContestBean contestBean){
		return contestService.processContests(contestBean);
	}
	
	@GetMapping("/answerTypes")
	public AnswerTypeBeanList getAnswerTypes(){
		return contestService.getAnswerTypes();
	}
	
	@PostMapping("/createQuestion")
	public QuestionBean createQuestion(@RequestBody QuestionBean questionBean){
		return contestService.createQuestion(questionBean);
	}
	
	@PostMapping("/getContestQuestion")
	public ContestBean getContestQuestion(@RequestBody ContestBean contestBean){
		return contestService.getContestQuestion(contestBean);
	}
	
	@PostMapping("/updateQuestion")
	public BaseBean updateQuestion(@RequestBody QuestionBean questionBean){
		return contestService.updateQuestion(questionBean);
	}
	
	@PostMapping("/deleteQuestion")
	public BaseBean deleteQuestion(@RequestBody QuestionBean questionBean){
		return contestService.deleteQuestion(questionBean);
	}
	
	@PostMapping("/getPossibleAnswers")
	public PossibleAnswerBean getPossibleAnswers(@RequestBody QuestionBean questionBean){
		return contestService.getPossibleAnswers(questionBean);
	}
	
	@PostMapping("/createAnswerOption")
	public BaseBean createAnswerOptions(@RequestBody QuestionBean questionBean){
		return contestService.createAnswerOptions(questionBean);
	}
	
	@PostMapping("/updateAnswerOption")
	public BaseBean updateAnswerOption(@RequestBody AnswerOptionBean answerOptionBean){
		return contestService.updateAnswerOption(answerOptionBean);
	}
	
	@PostMapping("/getAnswerForQuestion")
	public QuestionBean getAnswersForQuestion(@RequestBody QuestionBean questionBean){
		return contestService.getAnswersForQuestion(questionBean);
	}
	
	@PostMapping("/deleteAnswerOption")
	public BaseBean deleteAnswerOption(@RequestBody AnswerOptionBean answerOptionBean){
		return contestService.deleteAnswerOption(answerOptionBean);
	}
	
	@PostMapping("/createPrizeContest")
	public BaseBean createPrizeContest(@RequestBody PrizeContestBean prizeContestBean){
		return contestService.createPrizeContest(prizeContestBean);
	}
	
	@PostMapping("/updatePrizeContest")
	public BaseBean UpdatePrizeContest(@RequestBody PrizeContestBean prizeContestBean){
		return contestService.updatePrizeContest(prizeContestBean);
	}
	
	@PostMapping("/getPrizeContest")
	public PrizeContestBeanList getPrizeContest(@RequestBody PrizeContestBean prizeContestBean){
		return contestService.getPrizeContests(prizeContestBean);
	}
	
	@PostMapping("/deletePrizeContest")
	public BaseBean deletePrizeContest(@RequestBody PrizeContestBean prizeContestBean){
		return contestService.deletePrizeContest(prizeContestBean);
	}
	
	@PostMapping("/getPrizeGroups")
	public UserGroupBeanList getPrizeGroups(@RequestBody UserGroupBean userGroupBean){
		return contestService.getPrizeGroups(userGroupBean);
	}
	
	@GetMapping("/getOngoingContest")
	public ContestBeanList getOngoingContest(){
		return contestService.getProcessingContests();
	}
	
	/*@GetMapping("/pushTest")
	public BaseBean pushTest(){
		return contestService.sendNotification();
	}*/
	
	@PostMapping("/processFanGroup")
	public BaseBean processFanGroup(@RequestParam Integer tournamentId){
		BaseBean baseBean = new BaseBean();
		contestService.processFanGroupRanking(tournamentId);
		return baseBean;
		
	}
	
	@PostMapping("/processPrizeContest")
	public BaseBean processPrizeContest(){
		return contestService.processPrizeContest();
	}
	
}


