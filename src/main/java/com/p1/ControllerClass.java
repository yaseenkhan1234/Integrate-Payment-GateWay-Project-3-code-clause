package com.p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerClass {

	//dependency injection 
	@Autowired
	ClientAddRepo repo;
	//pay through upi
	@Autowired
	PayThroughUPIRepo repo1;
	
	
	//redirect to home page
	@RequestMapping({"/home","/"})
	public String home() {
		return "home";
	}
	
	//redirect to payment gateway
	@RequestMapping("/payment")
	public String payment() {
		return "payment";
	}
	
	//add client
		@RequestMapping("/clientadd")
		public String addClient(@ModelAttribute ClientAdd add,@RequestParam("money") Long money,Model model,HttpServletRequest req) {
			model.addAttribute("money",money);
			repo.save(add);
			
			if(req.getRequestURL().equals("http://localhost:9999/upi")) {
				model.addAttribute("money",money);
				return "upi";
			}
			return "gateway";
		}
		
		
		  //upi
		  
		  @RequestMapping("/upi") 
		  public String upi(Model mode) {
			  
			  return "upi";
		}

	//redirect to qr code
	@RequestMapping("/qr")
	public String QR() {
			  return "qr";
   }


			//pay through upi id 
	@RequestMapping("/pay/through/upi")
	public String PayThroughUPI(@ModelAttribute PayThroughUPI pay) {
					 
					  repo1.save(pay);
					  return "success";
	}
				  

	//redirect to profile page
	
	@RequestMapping("/profile")
	public String profile() {
		
		return "profile";
	}

	@RequestMapping("/card")
	public String card() {
		
		return "card";
	}

	
	//details of customer
	
	@RequestMapping("/details")
	public String details(@RequestParam("email") String email,@RequestParam("upiId") String upi,Model model,HttpServletResponse res,HttpServletRequest req) throws IOException{
		
		List<PayThroughUPI> list1=repo1.findByupiId(upi);
		
		for(PayThroughUPI upi1:list1) {
			String dbupiId=upi1.getUpiId();
			
			if(upi.equals(dbupiId)) {
				List<ClientAdd> client=repo.findByEmail(email);
				for(ClientAdd add:client) {
					String email1=add.getEmail();
					HttpSession session=req.getSession();
					session.setAttribute("email", email1);

				}
				model.addAttribute("client",client);
				return "details";
			}
			else {
				PrintWriter out=res.getWriter();
				out.println("<h1>you are not register</h1>");
			}
			
		}
		
		
		return "profile";
	}

	
	
}
