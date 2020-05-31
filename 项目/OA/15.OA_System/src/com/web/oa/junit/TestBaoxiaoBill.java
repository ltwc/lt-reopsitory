package com.web.oa.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.apache.commons.lang.StringUtils;
import com.web.oa.mapper.BaoxiaobillMapper;
import com.web.oa.mapper.SysPermissionMapperCustom;
import com.web.oa.pojo.SysPermission;
import com.web.oa.pojo.TreeMenu;
import com.web.oa.service.WorkFlowService;
import com.web.oa.utils.Constants;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext.xml","classpath:spring/springmvc.xml"})

public class TestBaoxiaoBill {
	
	@Autowired
	private BaoxiaobillMapper baoxiaobillMapper;
	
	@Autowired
	private SysPermissionMapperCustom sysPermissionMapperCustom;
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private WorkFlowService workFlowService;
	

	//1. 把流程图部署到数据库中   产生流程定义（类似类）ID：HelloProcess:1:4  中间数表示版本
	@Test //多次定义流程业务，默认使用最新的  --》act_re_deployment
	public void testDeployDB() throws FileNotFoundException{
		//getRepositoryService：主要操作流程定义业务，部署流程定义业务
		
		//第二种部署流程的方式 开发web应用多
		String path = "D:\\diagram\\baoxiaoprocess.zip";
		InputStream in = new FileInputStream(path);
		ZipInputStream zipInputStream = new ZipInputStream(in);
		
		Deployment deployment = repositoryService.createDeployment()
									.name("报销测试110")
									.addZipInputStream(zipInputStream)
									.deploy();
									
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}	
	
	

	@Test // 2.启动流程实例（类似对象）
	public void testProcess(){
		String key = "baoxiaoprocess";
		Map<String,Object> map = new HashMap<>();
		map.put("userId", "zhang");
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(key,map);
		System.out.println("流程实例的id:"+pi.getId()); //流程实例的id -->act_hi_procinst 流程实例的信息的历史表
		System.out.println("流程定义的id:"+pi.getProcessDefinitionId()); //流程定义的id -->表act_re_procdef流程定义表
	}
	
	@Test //3.我的代办事务列表
	public void testFindMytask(){
		String taskAssignee = "mike";
		List<Task> list = taskService.createTaskQuery()
						      .taskAssignee(taskAssignee)
						      .list();
		for (Task task : list) {
			System.out.println("任务ID:"+task.getId());
			System.out.println("流程定义的id:"+task.getProcessDefinitionId());
			System.out.println("流程实例的id:"+task.getProcessInstanceId());
			System.out.println("任务创建时间:"+task.getCreateTime());
		}
	}
	
	@Test //4. 完成个人任务（推进流程）
	public void testFinishTask(){
		String taskId = "605";// 任务ID
		taskService.complete(taskId);
		System.out.println("当前任务完成");
	}
	
	
	@Test //5.查询流程定义的列表
	public void testFindProcessDefsList(){
		List<ProcessDefinition> list = repositoryService
										.createProcessDefinitionQuery()
										.list();
		for (ProcessDefinition pd : list) {
			System.out.println("流程定义ID："+pd.getId());
			System.out.println("流程部署对象ID："+pd.getDeploymentId());
			System.out.println("流程定义的key："+pd.getKey());
			System.out.println("流程定义的名称："+pd.getName());
			System.out.println("流程定义的资源文件："+pd.getResourceName());
		}
	}
	
	@Test //6.删除定义的流程
	public void testRemoveProcessDef(){
		String deploymentId = "1"; //通过部署id删除
		//第二个参数表示是否强行删除，如果是true,表示如果当前流程定义中有活动的任务，也强行删除
		repositoryService.deleteDeployment(deploymentId,true);
		System.out.println("删除完成");
	}
	
	
	@Test //7.查看流程定义图
	public void viewPic() throws IOException {
		String deploymentId = "501";
		String resourceName = "baoxiaoprocess.png";
		InputStream in = repositoryService
									.getResourceAsStream(deploymentId , resourceName );
		File targetFile = new File("D:/diagram1/"+resourceName);
		//FileUtils.copyInputStreamToFile(in, targetFile );		
		//3：从response对象获取输出流
		OutputStream out = new FileOutputStream(targetFile);
		//4：将输入流中的数据读取出来，写到输出流中
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();
		System.out.println("图片已经保存");
	}
	
	
	@Test //8.判断流程是否结束
	public void testProcessInstanceEnd(){
		String processId = "801"; //流程实例id
		ProcessInstance pi = runtimeService
						.createProcessInstanceQuery()
						.processInstanceId(processId)
						.singleResult();
		//根据流程实例的非空来判断是否完成
		if(pi != null){
			System.out.println("流程正在运行");
		}else{
			System.out.println("流程已经结束");
		}
	}
	
	
	@Test //9.判断流程是否结束
	public void testHistoryTaskList(){
		List<HistoricTaskInstance> list = historyService
											.createHistoricTaskInstanceQuery()
											.list();
		for (HistoricTaskInstance hti : list) {
			System.out.println("流程实例的id:"+hti.getProcessInstanceId()); //流程实例的id -->act_hi_procinst 流程实例的信息的历史表
			System.out.println("流程定义的id:"+hti.getProcessDefinitionId()); //流程定义的id -->表act_re_procdef流程定义表
		}
	}	
	
	
	
	
	@Test
	public void testFindOutComeListByTaskId() {
		//返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		String taskId = "1117";
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
		
	}
	
	
	@Test  //当前流程图
	public void viewCurrentImage(){
		String taskId = "702";
		/**一：查看流程图*/
		//1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
		ProcessDefinition pd = workFlowService.findProcessDefinitionByTaskId(taskId);

		System.out.println("deploymentId: " + pd.getDeploymentId());
		System.out.println("imageName: "+pd.getDiagramResourceName());
		/**二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
		Map<String, Object> map = workFlowService.findCoordingByTask(taskId);

		System.out.println("x: " + map.get("x"));
		System.out.println("y: " + map.get("y"));
		System.out.println("width: " + map.get("width"));
		System.out.println("height: " + map.get("height"));
	}
	
	@Test //我的报销单的流程图
	public void viewCurrentImageByBill() {
		// 业务表的id ---> 流程定义
		String baoxiaoId = "13";//url链接里的id(billid)
		String bussinessKey = Constants.LEAVEBILL_KEY + "." + baoxiaoId; // baoxiao.13
		
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(bussinessKey).singleResult();
		
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
								.processDefinitionId(task.getProcessDefinitionId()).singleResult();
		

		System.out.println("deploymentId: " + pd.getDeploymentId());
		System.out.println("imageName: "+pd.getDiagramResourceName());
		/**二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
		Map<String, Object> map = workFlowService.findCoordingByTask(task.getId());

		System.out.println("x: " + map.get("x"));
		System.out.println("y: " + map.get("y"));
		System.out.println("width: " + map.get("width"));
		System.out.println("height: " + map.get("height"));
	}
	
	
	
	//测试获得菜单和二级菜单
	@Test
	public void testFindMenuList(){
		
		List<TreeMenu> treeMenus = sysPermissionMapperCustom.findMenuList();
		for (TreeMenu treeMenu : treeMenus) {
			System.out.println(treeMenu.getId()+"-"+treeMenu.getName());
			//当前一级菜单的二级菜单的数据
			List<SysPermission> subMenus = treeMenu.getChildren();
			for (SysPermission subMenu : subMenus) {
				System.out.println("\t"+subMenu.getName()+"-"+subMenu.getUrl()+subMenu.getPercode());
			}
		}
		
		
	}
	
	
	
}
