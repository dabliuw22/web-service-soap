package com.leysoft.service.inter;

import java.util.List;

import com.leysoft.entity.AutorModel;

public interface AutorService {
	
	public AutorModel save(AutorModel autor);
	
	public AutorModel findById(Integer id);
	
	public List<AutorModel> findAll();
	
	public AutorModel update(AutorModel autor);
	
	public boolean delete(Integer id);
}