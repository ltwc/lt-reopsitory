package com.web.oa.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.Baoxiaobill;
import com.web.oa.pojo.Employee;
import com.web.oa.service.WorkFlowService;
import com.web.oa.utils.Constants;

@Controller
public class WorkFlowController {
	
	
	@Autowired
	private WorkFlowService workFlowService;
	
	
	//部署流程
	@RequestMapping("/deployProcess")
	public String deployProcess(String processName,MultipartFile fileName){
		
		try {
			workFlowService.deployProcess(processName, fileName.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/processDefinitionList";
		
	}
	
	
	//查询流程定义和部署信息
	@RequestMapping("/processDefinitionList")
	public ModelAndView processDefinitionList(){
		
		//部署信息表
		List<Deployment> deploymentList = this.workFlowService.findDeploymentList();
		List<ProcessDefinition> pdList = this.workFlowService.findProcessDefinitionList();
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("depList", deploymentList);
		mv.addObject("pdList", pdList);
		mv.setViewName("workflow_list");
		return mv;
	}
	
	
	//提交申请单，启动流程实例
	@RequestMapping("/saveStartLeave")
	public String saveStartLeave(Baoxiaobill bill,HttpSession session){
		//Employee employee = (Employee) session.getAttribute(Constants.GLOBAL_SESSION_ID);
		
		//从shiro的session中取activeUser
		Subject subject = SecurityUtils.getSubject();
		//取身份信息
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		
		
		workFlowService.saveLeaveAndStartProcess(bill, activeUser);
		return "redirect:/myTaskList";
	}
	
	
	//查找我的待办事务
	@RequestMapping("/myTaskList")
	public ModelAndView myTaskList(HttpSession session){
		//Employee employee = (Employee) session.getAttribute(Constants.GLOBAL_SESSION_ID);

		
		//从shiro的session中取activeUser
		Subject subject = SecurityUtils.getSubject();
		//取身份信息
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		//根据代办人的名字获取代办任务
		List<Task> list = workFlowService.findMyTaskListByUserId(activeUser.getUsername());
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("taskList", list);
		mv.setViewName("workflow_task");
		return mv;
	}
	
	
	//办理任务：跳转到任务的提交页面
	@RequestMapping("/viewTaskForm")
	public ModelAndView viewTaskForm(String taskId){
		
		//根据流程的任务id查询对应的请假单的信息
		Baoxiaobill bill = this.workFlowService.findBillByTask(taskId);
		
		//查流程的批注信息
		List<Comment> commentList = this.workFlowService.findCommentListByTask(taskId);
		
		ModelAndView mv = new ModelAndView();
		
		//审批页提交按钮集合
		List<String> submitList = workFlowService.findOutComeListByTaskId(taskId);
		
		mv.addObject("submitList", submitList);
		
		mv.addObject("bill", bill);
		mv.addObject("commentList", commentList);
		
		mv.addObject("taskId", taskId);
		
		mv.setViewName("approve_leave");
		return mv;
	}
	
	
	//流程任务完成，流程推进
	@RequestMapping("/submitTask")
	public String submitTask(int id,String taskId,String comment,HttpSession session,String message){
		//Employee employee = (Employee) session.getAttribute(Constants.GLOBAL_SESSION_ID);
		
		
		//从shiro的session中取activeUser
		Subject subject = SecurityUtils.getSubject();
		//取身份信息
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		System.out.println("submitTask:"+activeUser.getUsername());
		
		
		this.workFlowService.submitTask(taskId, comment, activeUser.getUsername(), id,message);
		
		return "redirect:/myTaskList";
	}
	
	
	
	//查看流程中的-->查看流程定义图
	@RequestMapping("/viewImage")
	public String viewImage(String deploymentId,String imageName,HttpServletResponse response) throws IOException{
		InputStream in = workFlowService.findImageInputStream(deploymentId,imageName);
		
		OutputStream out = response.getOutputStream();
		
		//将输入流中的数据读取出来，写到输出流中
		for (int b=-1;(b = in.read())!= -1;) {
			out.write(b);
		}
		out.close();
		in.close();
		return null;
	}
	
	
	//我的代办事务-->查看当前流程图
	@RequestMapping("/viewCurrentImage")
	public ModelAndView  viewCurrentImage(String taskId) {
		/**一：查看流程图*/
		//1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
		ProcessDefinition pd = workFlowService.findProcessDefinitionByTaskId(taskId);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("deploymentId", pd.getDeploymentId());
		mv.addObject("imageName", pd.getDiagramResourceName());
		/**二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
		Map<String, Object> map = workFlowService.findCoordingByTask(taskId);

		mv.addObject("acs", map);
		mv.setViewName("viewimage");
		return mv;
	}
	
	
	//删除部署的流程
	@RequestMapping("/delDeployment")
	public String delDeployment(String deploymentId){
		
		workFlowService.deleteDeployment(deploymentId);
		
		return "redirect:/processDefinitionList";
	}
	
	
	
}
