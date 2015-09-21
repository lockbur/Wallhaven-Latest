package com.anhao.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anhao.spring.dao.JobPhotosDAO;
import com.anhao.spring.formbeans.UserBean;
import com.anhao.spring.task.crawlTask;
import com.anhao.spring.wallhaven.StorageService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Resource
	private TaskExecutor taskExecutor;
	
	@Resource
	private JobPhotosDAO jobPhotosDAO;

	@Resource
	private StorageService storageService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("userBean", new UserBean());
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		for (int i = 10000, c = 10050; i < c; i++) {
//			wallhavenJobCrawler.crawlByWallPaperId(i + "");
			
			taskExecutor.execute(new crawlTask(i + "",jobPhotosDAO,storageService));
		}
		
		return "formPage";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String submitForm(@Valid UserBean userBean,BindingResult result,Model model){
		if(result.hasErrors()){
			
			model.addAttribute("errorMessage", result.getAllErrors());
			return "formPage";
		}
		
		model.addAttribute("message", "Successfully saved userBean: " + userBean.toString());
		return "formPage";
		
	}
	
}
