
package com.turtlebone.contract.service;

import com.turtlebone.contract.model.ContractActivityModel;
import java.util.Date;

public interface ContractActivityService{
	
	public int create(ContractActivityModel contractActivityModel);
	
	public int createSelective(ContractActivityModel contractActivityModel);
	
	public ContractActivityModel findByPrimaryKey(Integer id);
	
	public int updateByPrimaryKey(ContractActivityModel contractActivityModel);
	
	public int updateByPrimaryKeySelective(ContractActivityModel contractActivityModel);
	
	public int deleteByPrimaryKey(Integer id);
	

	public int selectCount(ContractActivityModel contractActivityModel);
	
}