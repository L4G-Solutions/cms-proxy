package com.andromeda.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.andromeda.cms.model.ElectionVote;
import com.andromeda.cms.model.PhotoGallery;
import com.andromeda.cms.service.ElectionVoteService;

@RestController
public class ElectionVoteController {
	
	@Autowired
	ElectionVoteService electionVoteService;
	
	@ResponseBody
	@RequestMapping(value = "/cms/electionVote", method = { RequestMethod.GET })
	public  ElectionVote getMunugodeElectionVote() throws Exception
	{
		return electionVoteService.getMunugodeElectionVote();
	}
}
