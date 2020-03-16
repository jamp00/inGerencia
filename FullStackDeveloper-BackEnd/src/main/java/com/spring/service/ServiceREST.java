package com.spring.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.HackerNew;
import com.spring.repository.HackerNewsImplement;


//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin
@RestController
@RequestMapping(path = "/HackerNews")
public class ServiceREST {

	@Autowired
	@Qualifier("HackerNewsImplement")
	private HackerNewsImplement hackerNewsImplement;

//	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/getHackerNews", produces = "application/json")
	public List<HackerNew> getHackerNews(HttpServletRequest request) throws Exception {

		return hackerNewsImplement.findByNoDeleted();
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/setDeletedHackNew/{id}")
	public boolean setDeletedHackNew(@PathVariable(value = "id") int id, HttpServletRequest request) throws Exception {

//		Integer id = Integer.parseInt(request.getParameter("id"));
		System.out.println("ID::" + id);
		
		return hackerNewsImplement.setDeleted(id);
	}
	
}
