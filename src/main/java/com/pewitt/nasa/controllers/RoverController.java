package com.pewitt.nasa.controllers;

import com.pewitt.nasa.constants.Camera;
import com.pewitt.nasa.dto.PhotoListDTO;
import com.pewitt.nasa.dto.RoverForm;
import com.pewitt.nasa.rest.NasaClient;
import com.pewitt.nasa.service.PhotoService;
import com.pewitt.nasa.service.RoverService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RoverController {

	private RoverService roverService;

	private NasaClient nasaClient;

	private PhotoService photoService;


	public RoverController(RoverService roverService, NasaClient nasaClient, PhotoService photoService) {
		this.roverService = roverService;
		this.nasaClient = nasaClient;
		this.photoService = photoService;
	}

	private Logger logger = LoggerFactory.getLogger(RoverController.class);

	private void populateModel(Model model) {

		model.addAttribute("roverForm", new RoverForm());
	}

	@GetMapping("/")
	public String getRoverPage(Model model) {
		List<String> roverNames = roverService.getRoverNames();

		model.addAttribute("roverList", roverNames);
		model.addAttribute("roverForm", new RoverForm());

		return "rover.html";
	}


	@PostMapping("/")
	public String submitRoverSearch(@Valid RoverForm roverForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			List<String> roverNames = roverService.getRoverNames();
			model.addAttribute("roverList", roverNames);
			return "rover.html";
		}
		Camera camera = roverForm.getCamera();
		String date = roverForm.getDate();
		String rover = roverForm.getRover();


		PhotoListDTO photoListDTO = nasaClient.getPhotoListDTO(rover, date, camera);
		List<String> photoNames = new ArrayList<>();
		photoListDTO.getPhotos().stream().forEach(photo -> {
			String encodedFileName = photoService.processPhoto(photo);
			photoNames.add(encodedFileName);
		});
		List<String> roverNames = roverService.getRoverNames();

		model.addAttribute("photoNames", photoNames);
		model.addAttribute("roverList", roverNames);
		model.addAttribute("roverForm", new RoverForm());


		return "rover.html";


	}


}
