package com.mspaulo.imageloader.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mspaulo.imageloader.data.UploadFileResponse;
import com.mspaulo.imageloader.service.ImageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/images")
@Slf4j
public class ImagesController {
	
	private ImageService imageService;
	
	@Autowired
	public ImagesController(ImageService imageService) {
		this.imageService = imageService;
	}
	
	@PostMapping("preview")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> preview(
			@RequestParam("file") MultipartFile file, 
			HttpServletResponse httpServletResponse
			) {
		
		ResponseEntity<Object> response = null;
		
		try {
			
			httpServletResponse.getOutputStream().write(
					imageService.resize(file.getInputStream()).toByteArray()
					);
			
			response = new ResponseEntity<>(HttpStatus.OK);
			
		} catch (IOException e) {

			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
			log.error("ERRO AO REALIZAR PREVIEW DO ARQUIVO", e);
		}
		return response;
	}

	@PostMapping()
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> store(@RequestParam("file") MultipartFile file) {
		
		ResponseEntity<Object> response = null;
		
		try {
			
			UploadFileResponse uploadFileResponse = imageService.save(file.getInputStream());
			
			response = new ResponseEntity<>(uploadFileResponse, HttpStatus.OK);
			
		} catch (IOException e) {

			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
			log.error("ERRO AO REALIZAR UPLOAD DO ARQUIVO", e);
		}
		return response;
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('CLIENT')")
	public ResponseEntity<Object> render(@PathVariable String id, HttpServletResponse sltResponse) {
		
		ResponseEntity<Object> response = null;
		
		try {
						
			byte[] bytes = StreamUtils.copyToByteArray(imageService.findById(id));

			
			response = ResponseEntity.ok()
						  .contentType(MediaType.IMAGE_JPEG)
						  .body(bytes);
			
		} catch (Exception e) {
			
			log.error("ERRO AO RECUPERAR ARQUIVO DE ID " + id, e);
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;
	}
	
}
