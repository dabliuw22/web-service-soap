package com.leysoft.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.leysoft.autor.AddAutorRequest;
import com.leysoft.autor.AddAutorResponse;
import com.leysoft.autor.Autor;
import com.leysoft.autor.DeleteAutorRequest;
import com.leysoft.autor.DeleteAutorResponse;
import com.leysoft.autor.GetAllAutorRequest;
import com.leysoft.autor.GetAllAutorResponse;
import com.leysoft.autor.GetAutorRequest;
import com.leysoft.autor.GetAutorResponse;
import com.leysoft.autor.UpdateAutorRequest;
import com.leysoft.autor.UpdateAutorResponse;
import com.leysoft.entity.AutorModel;
import com.leysoft.exception.InvalidRequestException;
import com.leysoft.exception.NotFoundException;
import com.leysoft.service.inter.AutorService;
import com.leysoft.util.AutorConverter;

@Endpoint
public class AutorEndpoint {
	
	private static final String NAMESPACE_URI = "http://leysoft.com/autor";
	
	@Autowired
	private AutorService autorService;
	
	@Autowired
	private AutorConverter autorConverter;
	
	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAutorRequest")
	public GetAutorResponse getAutor(@RequestPayload GetAutorRequest request) {
		GetAutorResponse response = new GetAutorResponse();
		AutorModel model = autorService.findById(request.getId());
		if(model == null) {
			throw new NotFoundException("Not Found id: " + request.getId());
		}
		Autor autor = autorConverter.getAutor(model);
		response.setAutor(autor);
		return response;
	}
	
	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllAutorRequest")
	public GetAllAutorResponse getAllAutor(@RequestPayload GetAllAutorRequest request) {
		GetAllAutorResponse response = new GetAllAutorResponse();
		response.getAutores().addAll(autorConverter.getAutor(autorService.findAll()));
		return response;
	}
	
	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addAutorRequest")
	public AddAutorResponse addAutor(@RequestPayload AddAutorRequest request) {
		AddAutorResponse response = new AddAutorResponse();
		AutorModel model = new AutorModel();
		model.setName(request.getName());
		response.setAutor(autorConverter.getAutor(autorService.save(model)));
		return response;
	}
	
	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAutorRequest")
	public UpdateAutorResponse updateAutor(@RequestPayload UpdateAutorRequest request) {
		UpdateAutorResponse response = new UpdateAutorResponse();
		AutorModel model = autorService.findById(request.getId());
		if(model == null || request.getId() != request.getAutor().getId()) {
			throw new InvalidRequestException("Invalid id: " + request.getId() 
				+ " and autor.id: " + request.getAutor().getId());
		}
		Autor autor = autorConverter.getAutor(model);
		autor.setName(request.getAutor().getName());
		model = autorConverter.getAutorModel(autor);
		autorService.update(model);
		return response;
	}
	
	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAutorRequest")
	public DeleteAutorResponse deleteAutor(@RequestPayload DeleteAutorRequest request) {
		DeleteAutorResponse response = new DeleteAutorResponse();
		AutorModel model = autorService.findById(request.getId());
		if(model == null) {
			throw new NotFoundException("Not Found id: " + request.getId());
		}
		response.setStatus(autorService.delete(request.getId()));
		return response;
	}
}