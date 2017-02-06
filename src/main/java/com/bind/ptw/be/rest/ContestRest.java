package com.bind.ptw.be.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.MatchBeanList;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.QuestionBeanList;
import com.bind.ptw.be.services.ContestService;

@EnableAutoConfiguration
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
	
	@PostMapping("/updateMatchStatus")
	public BaseBean updateMatchStatus(@RequestBody MatchBean matchBean){
		return contestService.updateMatchStatus(matchBean);
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
	
	@PostMapping("/createQuestion")
	public QuestionBean createQuestion(@RequestBody QuestionBean questionBean){
		return contestService.createQuestion(questionBean);
	}
	
	@PostMapping("/getContestQuestion")
	public ContestBean getContestQuestion(@RequestBody ContestBean contestBean){
		return contestService.getContestQuestion(contestBean);
	}
	
	@PostMapping("/processContests")
	public BaseBean processContests(@RequestBody ContestBean contestBean){
		return contestService.processContests(contestBean);
	}
	
	@PostMapping("/updateQuestion")
	public BaseBean updateQuestion(@RequestBody QuestionBean questionBean){
		return contestService.updateQuestion(questionBean);
	}
	
	@PostMapping("/deleteQuestion")
	public BaseBean deleteQuestion(@RequestBody ContestBean contestBean){
		return contestService.deleteQuestion(contestBean);
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
	
}
