package com.example.Flightaware.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Flightaware.model.User;
import com.example.Flightaware.model.UserIp;
import com.example.Flightaware.repository.FlightRepo;
import com.example.Flightaware.repository.userRepo;

@Controller
public class FlightController {

@Autowired
FlightRepo frepo;

@Autowired
userRepo urepo;

BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
@GetMapping("/")
public ModelAndView homeGet(ModelAndView modelAndView,UserIp userip)
{
	modelAndView.setViewName("home");
	return modelAndView;
}
@GetMapping("/Register")
public ModelAndView RegGet(ModelAndView modelAndView,User user)
{
	modelAndView.addObject("user", user);
	modelAndView.setViewName("register");
	return modelAndView;
}
@GetMapping("/Login")
public ModelAndView LogGet(ModelAndView modelAndView,User user)
{
	modelAndView.addObject("user", user);
	modelAndView.setViewName("login");
	return modelAndView;
}
@PostMapping("/Register")
public String registerUser(ModelAndView modelAndView, User user) {
	String path = null;
	String email=user.getEmailId();
	System.out.println(email);
	User existingUser = urepo.findByEmailIdIgnoreCase(email);
	if(existingUser != null) {
		modelAndView.addObject("msg","This email already exists!");
		modelAndView.setViewName("register");
	} else {
		System.out.println(user.getPass());
		user.setPass(encoder.encode(user.getPass()));
		urepo.save(user);
		//sendEmail(user.getEmailId());
		modelAndView.addObject("emailId", user.getEmailId());
		 path="redirect:/Login";
	}
	
	return path;
}
long loggedInUser;
@PostMapping("/Login")
public ModelAndView loginUser(ModelAndView modelAndView, User user) {
	String email=user.getEmailId();
	User existingUser = urepo.findByEmailIdIgnoreCase(email);
	System.out.println(existingUser);
	if(existingUser != null) {
		if (encoder.matches(user.getPass(), existingUser.getPass())){
			// successfully logged in
			modelAndView.addObject("msg", "You Have Successfully Logged in");
			modelAndView.setViewName("loginHome");
		} else {
			// wrong password
			modelAndView.addObject("msg", "Incorrect password. Try again.");
			modelAndView.setViewName("login");
		}
	} else {	
		modelAndView.addObject("msg", "The email provided does not exist!");
		modelAndView.setViewName("login");
	}	
	return modelAndView;
}

ApiCalls ap=new ApiCalls();
@GetMapping("/logHome")
public ModelAndView loghomeGet(ModelAndView modelAndView,UserIp userip)
{
	modelAndView.setViewName("loginHome");
	return modelAndView;
}
@GetMapping("/fprice")
public ModelAndView fpriceGet(ModelAndView modelAndView,UserIp userip)
{
	modelAndView.addObject("userip", userip);
	modelAndView.setViewName("fprice");
	return modelAndView;
}
@GetMapping("/dprice")
public ModelAndView dpriceGet(ModelAndView modelAndView,UserIp userip)
{
	modelAndView.addObject("userip", userip);
	modelAndView.setViewName("dprice");
	return modelAndView;
}
@GetMapping("/routes")
public ModelAndView drGet(ModelAndView modelAndView,UserIp userip)
{
	modelAndView.addObject("userip", userip);
	modelAndView.setViewName("route");
	return modelAndView;
}
@PostMapping(value="/fprice")
public ModelAndView dispform(ModelAndView modelAndView,UserIp userip) throws InterruptedException, IOException, ParseException
{
	String locale,src,dest,dte;
	frepo.save(userip);
	locale=userip.getLocale();
	src=userip.getOrigin();
	dest=userip.getDest();
	dte=userip.getOutDate(); 
    //Date date1=(Date) new SimpleDateFormat("yyyy-mm-dd").parse(dte); 
    //DateFormat sdf = new SimpleDateFormat();
    //java.util.Date out = sdf.parse(dte);
    //System.out.println(dte+"\t"+date1);  
	String str=ap.fprices(locale, src, dest, dte);
	System.out.println("data is"+locale+" "+src+" "+dest+" "+dte+" ");
	ObjectMapper mapper = new ObjectMapper();
     Map<String, Object> restMap=mapper.readValue(str, Map.class);
     System.out.println(restMap.toString());  
   ArrayList<String> qlist=(ArrayList<String>)restMap.get("Quotes");
   ArrayList<String> plist=(ArrayList<String>)restMap.get("Places");
   ArrayList<String> clist=(ArrayList<String>)restMap.get("Carriers");
   ArrayList<String> crlist=(ArrayList<String>)restMap.get("Currencies");
Iterator qit=qlist.iterator();
Iterable<Object> qarray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(qit, 0),false).collect(Collectors.toList());
Iterator pit=plist.iterator();
Iterable<Object> parray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(pit, 0),false).collect(Collectors.toList());
Iterator cit=clist.iterator();
Iterable<Object> carray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(cit, 0),false).collect(Collectors.toList());
Iterator crit=crlist.iterator();
Iterable<Object> crarray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(crit, 0),false).collect(Collectors.toList());

System.out.println("qaeear is "+qarray);
modelAndView.addObject("msg","flight details");
     modelAndView.addObject("qarray",qarray);
     modelAndView.addObject("parray",parray);
     modelAndView.addObject("carray",carray);
     modelAndView.addObject("crarray",crarray);
     modelAndView.addObject("userip",userip);
	modelAndView.setViewName("fprice");
	return modelAndView;
}
@PostMapping(value="/dprice")
public ModelAndView diform(ModelAndView modelAndView,UserIp userip) throws InterruptedException, IOException, ParseException
{
	String ct,cr,dte2,locale,src,dest,dte;
	frepo.save(userip);
	locale=userip.getLocale();
	ct=userip.getCountry();
	cr=userip.getCurrency();
	src=userip.getOrigin();
	dest=userip.getDest();
	dte=userip.getOutDate();
	dte2=userip.getInDate();
    //Date date1=(Date) new SimpleDateFormat("yyyy-mm-dd").parse(dte); 
    //DateFormat sdf = new SimpleDateFormat();
    //java.util.Date out = sdf.parse(dte);
    //System.out.println(dte+"\t"+date1);  
	String str=ap.dprices(ct,cr,locale, src, dest, dte,dte2);
	System.out.println("data is"+locale+" "+src+" "+dest+" "+dte+" ");
	ObjectMapper mapper = new ObjectMapper();
     Map<String, Object> restMap=mapper.readValue(str, Map.class);
     System.out.println(restMap.toString());  
   ArrayList<String> qlist=(ArrayList<String>)restMap.get("Quotes");
   ArrayList<String> plist=(ArrayList<String>)restMap.get("Places");
   ArrayList<String> clist=(ArrayList<String>)restMap.get("Carriers");
   ArrayList<String> crlist=(ArrayList<String>)restMap.get("Currencies");
   Map<String, Object> dateMap=(Map<String, Object>) restMap.get("Dates");
   ArrayList<String> dolist=(ArrayList<String>)dateMap.get("OutboundDates");
   ArrayList<String> dilist=(ArrayList<String>)dateMap.get("OutboundDates");
Iterator qit=qlist.iterator();
Iterable<Object> qarray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(qit, 0),false).collect(Collectors.toList());
Iterator pit=plist.iterator();
Iterable<Object> parray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(pit, 0),false).collect(Collectors.toList());
Iterator cit=clist.iterator();
Iterable<Object> carray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(cit, 0),false).collect(Collectors.toList());
Iterator crit=crlist.iterator();
Iterable<Object> crarray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(crit, 0),false).collect(Collectors.toList());
Iterator doit=dolist.iterator();
Iterable<Object> doarray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(doit, 0),false).collect(Collectors.toList());
Iterator diit=dilist.iterator();
Iterable<Object> dirray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(diit, 0),false).collect(Collectors.toList());

System.out.println("qaeear is "+qarray);
modelAndView.addObject("msg","flight details");
     modelAndView.addObject("qarray",qarray);
     modelAndView.addObject("parray",parray);
     modelAndView.addObject("doarray",doarray);
     modelAndView.addObject("diarray",dirray);
     modelAndView.addObject("carray",carray);
     modelAndView.addObject("crarray",crarray);
     modelAndView.addObject("userip",userip);
	modelAndView.setViewName("dprice");
	return modelAndView;
}
@PostMapping(value="/routes")
public ModelAndView route(ModelAndView modelAndView,UserIp userip) throws InterruptedException, IOException, ParseException
{
	String ct,cr,dte2,locale,src,dest,dte;
	frepo.save(userip);
	locale=userip.getLocale();
	ct=userip.getCountry();
	cr=userip.getCurrency();
	src=userip.getOrigin();
	dest=userip.getDest();
	dte=userip.getOutDate();
    //Date date1=(Date) new SimpleDateFormat("yyyy-mm-dd").parse(dte); 
    //DateFormat sdf = new SimpleDateFormat();
    //java.util.Date out = sdf.parse(dte);
    //System.out.println(dte+"\t"+date1);  
	String str=ap.rts(ct,cr,locale, src, dest, dte);
	System.out.println("data is"+locale+" "+src+" "+dest+" "+dte+" ");
	ObjectMapper mapper = new ObjectMapper();
     Map<String, Object> restMap=mapper.readValue(str, Map.class);
     System.out.println(restMap.toString());  
   ArrayList<String> qlist=(ArrayList<String>)restMap.get("Quotes");
   ArrayList<String> plist=(ArrayList<String>)restMap.get("Places");
   ArrayList<String> clist=(ArrayList<String>)restMap.get("Carriers");
   ArrayList<String> crlist=(ArrayList<String>)restMap.get("Currencies");

   ArrayList<String> rtlist=(ArrayList<String>)restMap.get("Routes");
Iterator qit=qlist.iterator();
Iterable<Object> qarray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(qit, 0),false).collect(Collectors.toList());
Iterator pit=plist.iterator();
Iterable<Object> parray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(pit, 0),false).collect(Collectors.toList());
Iterator cit=clist.iterator();
Iterable<Object> carray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(cit, 0),false).collect(Collectors.toList());
Iterator crit=crlist.iterator();
Iterable<Object> crarray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(crit, 0),false).collect(Collectors.toList());
Iterator doit=rtlist.iterator();
Iterable<Object> doarray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(doit, 0),false).collect(Collectors.toList());

System.out.println("qaeear is "+qarray);
modelAndView.addObject("msg","flight details");
     modelAndView.addObject("qarray",qarray);
     modelAndView.addObject("parray",parray);
     modelAndView.addObject("doarray",doarray);
     modelAndView.addObject("carray",carray);
     modelAndView.addObject("crarray",crarray);
     modelAndView.addObject("userip",userip);
	modelAndView.setViewName("route");
	return modelAndView;
}
@GetMapping("/uguides")
public ModelAndView uguidet(ModelAndView modelAndView,UserIp userip)
{
	modelAndView.addObject("userip", userip);
	modelAndView.setViewName("uguide");
	return modelAndView;
}
@GetMapping("/currs")
public ModelAndView currss(ModelAndView modelAndView) throws IOException, InterruptedException
{
	String str=ap.curs();
	ObjectMapper mapper = new ObjectMapper();
     Map<String, Object> restMap=mapper.readValue(str, Map.class);
     System.out.println(restMap.toString());  
   ArrayList<String> qlist=(ArrayList<String>)restMap.get("Currencies");
   Iterator doit=qlist.iterator();
   Iterable<Object> curList = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(doit, 0),false).collect(Collectors.toList());

	modelAndView.addObject("curList", curList);
	modelAndView.setViewName("uguide");
	return modelAndView;
}
@GetMapping("/cntrys")
public ModelAndView cntry(ModelAndView modelAndView) throws IOException, InterruptedException
{
	String str=ap.cntrys();
	ObjectMapper mapper = new ObjectMapper();
     Map<String, Object> restMap=mapper.readValue(str, Map.class);
     System.out.println(restMap.toString());  
   ArrayList<String> qlist=(ArrayList<String>)restMap.get("Countries");
   Iterator doit=qlist.iterator();
   Iterable<Object> cntry = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(doit, 0),false).collect(Collectors.toList());

	modelAndView.addObject("cntry", cntry);
	modelAndView.setViewName("uguide");
	return modelAndView;
}
@GetMapping("/places")
public ModelAndView places(ModelAndView modelAndView,UserIp userip) throws IOException, InterruptedException
{
	
	modelAndView.addObject("userip",userip);
	modelAndView.setViewName("places");
	return modelAndView;
}
@PostMapping("/places")
public ModelAndView placespost(ModelAndView modelAndView,UserIp userip) throws IOException, InterruptedException
{
	String cntry,curr,code,locale;
	frepo.save(userip);
	locale=userip.getLocale();
	cntry=userip.getCnt();
	code=userip.getCountry();
	curr=userip.getCurr();
	String str=ap.placess(curr, code, cntry, locale);

	ObjectMapper mapper = new ObjectMapper();
     Map<String, Object> restMap=mapper.readValue(str, Map.class);
     System.out.println(restMap.toString());  
   ArrayList<String> plist=(ArrayList<String>)restMap.get("Places");
   Iterator doit=plist.iterator();
   Iterable<Object> placeArray = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(doit, 0),false).collect(Collectors.toList());
   modelAndView.addObject("placeArray",placeArray);
	modelAndView.addObject("userip",userip);
	modelAndView.setViewName("places");
	return modelAndView;
}

}