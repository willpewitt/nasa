package com.pewitt.nasa.controllers;

import com.pewitt.nasa.rest.NasaClient;
import com.pewitt.nasa.service.PhotoService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/photo")
public class PhotoController {

	private PhotoService photoService;

	public PhotoController(PhotoService photoService, NasaClient nasaClient) {
		this.photoService = photoService;
	}

	@GetMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getRoverPhoto(
			@RequestParam("name") String name) throws IOException {
		File photo = photoService.requestPhoto(name);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.IMAGE_JPEG);
		responseHeaders.set("Content-Disposition",
				"attachment; filename=mars_rover.jpg");
		byte[] file = Files.readAllBytes(photo.toPath());
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(file, responseHeaders,
				HttpStatus.OK);
		return responseEntity;
	}


}
