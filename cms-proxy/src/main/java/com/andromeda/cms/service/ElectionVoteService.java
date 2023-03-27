package com.andromeda.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andromeda.cms.model.ElectionVote;
import com.andromeda.commons.util.JsonUtils;

@Service
public class ElectionVoteService {
	
	@Autowired
	CmsProxyService cmsProxyService;

	public ElectionVote getMunugodeElectionVote() throws Exception {
		String munugodeElectionVoteStr = cmsProxyService.get("munugodeElectionVote");
		if(munugodeElectionVoteStr != null)
		{
			ElectionVote munugodeElectionVote = JsonUtils.deserialize(munugodeElectionVoteStr, ElectionVote.class);
			return munugodeElectionVote;
		}
		return null;
	}
	
	

}
