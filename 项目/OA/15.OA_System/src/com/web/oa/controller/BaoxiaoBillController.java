package com.web.oa.controller;

import java.util.List;
import java.util.Map;


import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.oa.pojo.ActiveUser;
import com.web.oa.pojo.Baoxiaobill;
import com.web.oa.service.BaoxiaoService;
import com.web.oa.service.WorkFlowService;
import com.web.oa.utils.Constants;

@Controller
public class BaoxiaoBillController {

	public static final int PAGE_SIZE = 8;
	
	@Autowired
	private BaoxiaoService baoxiaoService;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	//我的报销单
	@RequestMapping("/myBaoxiaoBill")
	public String myBaoxiaoBill(ModelMap model,
			@RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum){
		
		//1：查询所有的报销信息（对应的BaoxiaoBill） 返回List<BaoxiaoBill>
		//Employee employee =  (Employee) session.getAttribute(Constants.GLOBAL_SESSION_ID);
		
		System.out.println("分页开始1");
		//从shiro的session中取activeUser
		Subject subject = SecurityUtils.getSubject();
		//取身份信息
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		
		PageHelper.startPage(pageNum,PAGE_SIZE);
		List<Baoxiaobill> list = baoxiaoService.findBaoxiaoBillListByUser((int)activeUser.getUserid());
		PageInfo<Baoxiaobill> page = new PageInfo<>(list);
		
		//放置到上下文对象中
		model.addAttribute("baoxiaoList",list);
		model.addAttribute("page",page);
		
		return "baoxiaobill";
	}
	
	
	
	//我的报销单
	@RequestMapping("/BaoxiaoBillQuery")
	public String BaoxiaoBillQuery(ModelMap model,int pageNum){
		
		//1：查询所有的报销信息（对应的BaoxiaoBill） 返回List<BaoxiaoBill>
		//Employee employee =  (Employee) session.getAttribute(Constants.GLOBAL_SESSION_ID);
		
		System.out.println("分页开始2");
		
		//从shiro的session中取activeUser
		Subject subject = SecurityUtils.getSubject();
		//取身份信息
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		
		PageHelper.startPage(pageNum,PAGE_SIZE);
		List<Baoxiaobill> list = baoxiaoService.findBaoxiaoBillListByUser((int)activeUser.getUserid());
		PageInfo<Baoxiaobill> page = new PageInfo<>(list);
		
		//放置到上下文对象中
		model.addAttribute("baoxiaoList",list);
		model.addAttribute("page",page);
		
		return "baoxiaobill";
	}
	
	
	//我的报销单--》查看当前流程图
	@RequestMapping("/viewCurrentImageByBill")
	public String viewCurrentImageByBill(Integer billId,ModelMap model) { // 业务表的id ---> 流程定义
		
		String bussinessKey = Constants.BAOXIAO_KEY + "." + billId; // baoxiao.13
		
		Task task = this.workFlowService.findTaskByBussinessKey(bussinessKey);
		
		//查看流程图
		//1.获取任务ID：获取任务对象，使用对象获取流程定义ID，查询流程定义对象
		ProcessDefinition pd = this.workFlowService.findProcessDefinitionByTaskId(task.getId());
		
		model.addAttribute("deploymentId", pd.getDeploymentId());
		model.addAttribute("imageName", pd.getDiagramResourceName());
		
		/**二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
		Map<String, Object> map = workFlowService.findCoordingByTask(task.getId());

		model.addAttribute("acs", map);
		return "viewimage";
	}
	
	
	//删除报销单一条记录
	@RequestMapping("/deleteBaoxiao")
	public String deleteBaoxiao(Integer deleteBaoxiaoId) {
		baoxiaoService.deleteBaoxiaoBillById(deleteBaoxiaoId);
		return "redirect:/myBaoxiaoBill";
		
	}
	
	//查看审核记录
	@RequestMapping("/recordForm")
	public ModelAndView recordForm(Integer recordId) {
		
		String bussinessKey = Constants.BAOXIAO_KEY + "." + recordId; // baoxiao.13
		Task task = this.workFlowService.findTaskByBussinessKey(bussinessKey);
		
		//查流程的批注信息
		List<Comment> commentList = this.workFlowService.findCommentListByTask(task.getId());
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("commentList", commentList);
		
		mv.setViewName("Record");
		return mv;
		
		
	}
	
	
}
