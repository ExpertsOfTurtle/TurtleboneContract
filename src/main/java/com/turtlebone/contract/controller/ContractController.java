package com.turtlebone.contract.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.turtlebone.contract.bean.CreateContractRequest;
import com.turtlebone.contract.common.IActivityAction;
import com.turtlebone.contract.common.IContractStatus;
import com.turtlebone.contract.exception.ContractException;
import com.turtlebone.contract.model.ContractActivityModel;
import com.turtlebone.contract.model.ContractModel;
import com.turtlebone.contract.model.UserModel;
import com.turtlebone.contract.service.ContractActivityService;
import com.turtlebone.contract.service.ContractService;
import com.turtlebone.contract.service.UserService;
import com.turtlebone.contract.util.DateUtil;
import com.turtlebone.contract.util.StringUtil;
import com.turtlebone.contract.util.UUIDUtil;
@Controller
@EnableAutoConfiguration
@RequestMapping(value="/contract")
public class ContractController {
	private static Logger logger = LoggerFactory.getLogger(ContractController.class);
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractActivityService activityService;
	@Autowired
	private UserService userService;
		
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createContract(@RequestBody CreateContractRequest request) {
		logger.info("Creating contract by [{}]: title=[{}]", request.getCreator(), request.getTitle());
		
		try {
			validatRequest(request);
		} catch (ContractException e) {
			logger.error(e.getErrorMesage());
			ResponseEntity.ok(e.getErrorMesage());
		};
		
		ContractModel contract = new ContractModel();
		Date currentTime = new Date();
		contract.setContractContent(request.getContent());
		contract.setContractName(request.getTitle());
		contract.setCreateBy(request.getCreator());
		contract.setCreatetime(currentTime);
		contract.setContractType(request.getType());
		contract.setContractStatus(IContractStatus.PENDING);
		contract.setContractNo(UUIDUtil.generateId(6));
		Date effectiveDate = DateUtil.parse(request.getEffectiveDate());
		Date expiredDate = DateUtil.parse(request.getExpiredDate());
		if (effectiveDate == null) {
			effectiveDate = DateUtil.getNDaysLaterDate(0, "yyyy-MM-dd");
		}
		if (expiredDate == null) {
			expiredDate = DateUtil.getNDaysLaterDate(7, "yyyy-MM-dd");
		}
		contract.setEffectiveDate(effectiveDate);
		contract.setExpiredDate(expiredDate);
		
		int contractId = contractService.create(contract);
		
		for (String username : request.getPartyList()) {
			ContractActivityModel activity = new ContractActivityModel();
			activity.setContactid(contractId);
			activity.setUsername(username);
			activity.setAction(IActivityAction.PENDING);
			activityService.create(activity);
		}
		
		logger.info("Contract[{}] created, id={}", contract.getContractNo(), contractId);
		return ResponseEntity.ok(contract);
	}
	
	private boolean validatRequest(CreateContractRequest request) throws ContractException{
		if (StringUtil.isEmpty(request.getCreator())) {
			throw new ContractException("Missing creator");
		} else if (StringUtil.isEmpty(request.getTitle())) {
			throw new ContractException("Missing title");
		} else if (StringUtil.isEmpty(request.getContent())) {
			throw new ContractException("Missing content");
		} else if (StringUtil.isEmpty(request.getType())) {
			throw new ContractException("Missing contract type");
		}
		if (request.getPartyList() == null || request.getPartyList().size() == 0) {
			throw new ContractException("PartyList is empty");
		}
		List<UserModel> userList = userService.selectByUserList(request.getPartyList());
		if (userList.size() != request.getPartyList().size()) {
			throw new ContractException("Some user not exist");
		}
		return true;
	}
}
