package com.spring.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.spring.dto.HackerNew;

@Service("HackerNewsImplement")
public class HackerNewsImplement {

	@Autowired
	@Qualifier("HackerNewsRepository")
	private HackerNewsRepository hackerNewsRepository;


	public void Create(HackerNew hackerNew) {

		hackerNewsRepository.save(hackerNew);
	}

	public boolean existsById(int id) {

		return hackerNewsRepository.existsById(id);
	}

	public List<HackerNew> findByNoDeleted() {
		
		// Busca registros no borrados desde el front:: deleted = false
		return hackerNewsRepository.findByDeleted(false);
	}

	public boolean setDeleted(int id) {

		boolean deleted = false;

		// Busa el registro según el id
		HackerNew hackerNew = hackerNewsRepository.findById(id);
		
		if( hackerNew != null) {

			// Si el registro existe actualiza ampo deleted a true
			hackerNew.setDeleted(true);

			// Guarda el regstro actualizado
			hackerNewsRepository.save(hackerNew);
			deleted = true;
		}
	
		return deleted;
	}
}
