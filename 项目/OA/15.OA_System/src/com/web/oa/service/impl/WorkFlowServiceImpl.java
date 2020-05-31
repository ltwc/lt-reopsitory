package com.web.oa.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.lang.StringUtils;

import com.web.oa.mapper.BaoxiaobillMapper;
import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.Baoxiaobill;
import com.web.oa.pojo.Employee;
import com.web.oa.service.WorkFlowService;
import com.web.oa.utils.Constants;


@Service
public class WorkFlowServiceImpl implements WorkFlowService{

	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private BaoxiaobillMapper baoxiaobillMapper;

	//部署流程定义
	@Override
	public void deployProcess(String processName, InputStream input) {
		
		ZipInputStream zipInputStream = new ZipInputStream(input);
		Deployment deployment = repositoryService.createDeployment()
									.name(processName)
									.addZipInputStream(zipInputStream)
									.deploy();
	}


	//查询部署信息
	@Override
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery().list();
		return list;
	}

	//查询流程定义信息
	@Override
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
		return list;
	}


	
	
	
	
	@Override
	public void saveLeaveAndStartProcess(Baoxiaobill bill, Employee employee) {
		//1.保存申请单
		bill.setCreatdate(new Date());
		bill.setState(1);
		bill.setUserId(employee.getId().intValue());
		
		baoxiaobillMapper.insert(bill);//把新添加数据的主键回填到pojo对象中
		
		//2.启动流程实例
		//流程数据和业务表（Leavebill表）的数据相关联，把用户表的数据保存到流程数据库中（hi-procinst）
		String key = Constants.BAOXIAO_KEY;
		String bussiness_key = key+"."+bill.getId();
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", employee.getName());
		
		//this.runtimeService.startProcessInstanceByKey(key,map);
		this.runtimeService.startProcessInstanceByKey(key,bussiness_key,map);
		
	}

	
	
	
	@Override
	public void saveLeaveAndStartProcess(Baoxiaobill bill, ActiveUser activeUser) {
		//1.保存申请单
		bill.setCreatdate(new Date());
		bill.setState(1);
		bill.setUserId((int)activeUser.getUserid());
		
		baoxiaobillMapper.insert(bill);//把新添加数据的主键回填到pojo对象中
		
		//2.启动流程实例
		//流程数据和业务表（Leavebill表）的数据相关联，把用户表的数据保存到流程数据库中（hi-procinst）
		String key = Constants.BAOXIAO_KEY;
		String bussiness_key = key+"."+bill.getId();
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", activeUser.getUsername());
		
		//this.runtimeService.startProcessInstanceByKey(key,map);
		this.runtimeService.startProcessInstanceByKey(key,bussiness_key,map);
		
	}
	
	
	
	
	

	

	//查找我的待办事务
	@Override
	public List<Task> findMyTaskListByUserId(String name) {
		return this.taskService.createTaskQuery().taskAssignee(name).list();
	}


	@Override
	public Baoxiaobill findBillByTask(String taskId) {
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessInstance pi = this.runtimeService.createProcessInstanceQuery()
								.processInstanceId(task.getProcessInstanceId()).singleResult();
		
		String bussiness_key = pi.getBusinessKey();
//		System.out.println(bussiness_key);
		String billId = bussiness_key.split("\\.")[1];//数组的第二个元素
		
		Baoxiaobill bill = baoxiaobillMapper.selectByPrimaryKey(Integer.parseInt(billId));
		return bill;
	}


	//查询批注信息
	@Override
	public List<Comment> findCommentListByTask(String taskId) {
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		List<Comment> list = this.taskService.getProcessInstanceComments(task.getProcessInstanceId());
		return list;
	}


	//流程任务完成
	@Override
	public void submitTask(String taskId,String comment,String name,int id,String message) {
		
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		String processinstanceId = task.getProcessInstanceId();
		Authentication.setAuthenticatedUserId(name);
			
		//1.加批注 ：comment
		this.taskService.addComment(taskId, processinstanceId, comment);

		//2.任务完成 finish 流程推进
		//this.taskService.complete(taskId);
	
		//流程变量的使用 -->带有分支才使用
		Map<String, Object> map = new HashMap<>();//存放流程变量
		map.put("message", message);
		this.taskService.complete(taskId,map);
				
//		System.out.println("当前任务完成");
		
		//3.判断流程实例是否结束，如果结束，请假单的状态改为2
		ProcessInstance pi = this.runtimeService.createProcessInstanceQuery()
								.processInstanceId(processinstanceId).singleResult();		
		
		if(pi == null){ //流程实例结束
			Baoxiaobill bill = this.baoxiaobillMapper.selectByPrimaryKey(id);
			//把状态改为2
			bill.setState(2);
			//修改请假单状态
			this.baoxiaobillMapper.updateByPrimaryKey(bill);
			
		}
		
	}


	//查看流程中的-->查看流程定义图
	@Override
	public InputStream findImageInputStream(String deploymentId, String imageName) {

		return this.repositoryService.getResourceAsStream(deploymentId, imageName);
	}


	//我的代办事务-->查看当前流程图
	@Override
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//查询流程定义的对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef 
					.processDefinitionId(processDefinitionId)//使用流程定义ID查询
					.singleResult();
		return pd;
	}

	@Override
	public Map<String, Object> findCoordingByTask(String taskId) {
		//存放坐标
		Map<String, Object> map = new HashMap<String,Object>();
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义的ID
		String processDefinitionId = task.getProcessDefinitionId();
		//获取流程定义的实体对象（对应.bpmn文件中的数据）
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		//流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//创建流程实例查询
											.processInstanceId(processInstanceId)//使用流程实例ID查询
											.singleResult();
		//获取当前活动的ID
		String activityId = pi.getActivityId();
		//获取当前活动对象
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);//活动ID
		//获取坐标
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		return map;
	}

	
	
	public List<String> findOutComeListByTaskId(String taskId) {
		//返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		//1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId ).singleResult();
		//2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		//获取当前活动的id
		String activityId = pi.getActivityId();
		//4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		//5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvm:pvmList){
				String name = (String) pvm.getProperty("name");
				if(StringUtils.isNotBlank(name)){
					list.add(name);
				} else{
					list.add("默认提交");
				}
			}
		}
		
		for (String name : list) {
			System.out.println(name);
		}
		return list;
		
	}


	//删除部署的流程
	@Override
	public void deleteDeployment(String deploymentId) {//通过部署id删除
		//第二个参数表示是否强行删除，如果是true,表示如果当前流程定义中有活动的任务，也强行删除
		repositoryService.deleteDeployment(deploymentId,true);
//		System.out.println("删除完成");
	}


	@Override
	public Task findTaskByBussinessKey(String bussinessKey) {
		return this.taskService.createTaskQuery().processInstanceBusinessKey(bussinessKey).singleResult();
	}


	
		
	
	
	
	
	
}
