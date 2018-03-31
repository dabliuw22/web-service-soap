package com.leysoft.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.leysoft.autor.Autor;
import com.leysoft.entity.AutorModel;

@Component
public class AutorConverter {
	
	public Autor getAutor(AutorModel model) {
		Autor autor = new Autor();
		autor.setId(model.getId());
		autor.setName(model.getName());
		return autor;
	}
	
	public List<Autor> getAutor(List<AutorModel> models) {
		List<Autor> autores = new ArrayList<>();
		models.forEach(model -> autores.add(getAutor(model)));
		return autores;
	}
	
	public AutorModel getAutorModel(Autor autor) {
		AutorModel model = new AutorModel();
		model.setId(autor.getId());
		model.setName(autor.getName());
		return model;
	}
	
	public List<AutorModel> getAutorModel(List<Autor> autores) {
		List<AutorModel> models = new ArrayList<>();
		autores.forEach(autor -> models.add(getAutorModel(autor)));
		return models;
	}
}