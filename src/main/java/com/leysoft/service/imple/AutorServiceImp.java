package com.leysoft.service.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leysoft.entity.AutorModel;
import com.leysoft.repository.AutorRepository;
import com.leysoft.service.inter.AutorService;

@Service
public class AutorServiceImp implements AutorService {

	@Autowired
	private AutorRepository autorRepository;
	
	@Override
	public AutorModel save(AutorModel autor) {
		return autorRepository.save(autor);
	}

	@Override
	public AutorModel findById(Integer id) {
		return autorRepository.findOne(id);
	}

	@Override
	public List<AutorModel> findAll() {
		return (List<AutorModel>) autorRepository.findAll();
	}

	@Override
	public AutorModel update(AutorModel autor) {
		return autorRepository.save(autor);
	}

	@Override
	public boolean delete(Integer id) {
		boolean deleted = false;
		if(findById(id) != null) {
			autorRepository.delete(id);
			deleted = true;
		}
		return deleted;
	}
}