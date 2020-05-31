package com.web.oa.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.Baoxiaobill;
import com.web.oa.pojo.Employee;

public interface WorkFlowService {

	public void deployProcess(String processName,InputStream input);

	public List<Deployment> findDeploymentList();

	public List<ProcessDefinition> findProcessDefinitionList();

	
	
	
	public void saveLeaveAndStartProcess(Baoxiaobill bill, Employee employee);
	
	public void saveLeaveAndStartProcess(Baoxiaobill bill, ActiveUser activeUser);
	
	
	
	
	

	public List<Task> findMyTaskListByUserId(String name);

	public Baoxiaobill findBillByTask(String taskId);

	public List<Comment> findCommentListByTask(String taskId);

	public void submitTask(String taskId, String comment, String name, int id,String message);

	public InputStream findImageInputStream(String deploymentId, String imageName);

	public ProcessDefinition findProcessDefinitionByTaskId(String taskId);

	public Map<String, Object> findCoordingByTask(String taskId);
	
	public List<String> findOutComeListByTaskId(String taskId);

	public void deleteDeployment(String deploymentId);

	public Task findTaskByBussinessKey(String bussinessKey);

	
	
}
