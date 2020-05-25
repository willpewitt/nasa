package com.pewitt.nasa.service;

import com.pewitt.nasa.dto.PhotoListDTO;
import com.pewitt.nasa.dto.RoverDTO;
import com.pewitt.nasa.dto.RoverListDTO;
import com.pewitt.nasa.rest.NasaClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class ApplicationReadyService implements ApplicationListener<ApplicationReadyEvent> {

	private NasaClient nasaClient;

	private PhotoService photoService;

	public ApplicationReadyService(NasaClient nasaClient, PhotoService photoService) {
		this.nasaClient = nasaClient;
		this.photoService = photoService;
	}

	private Logger logger = LoggerFactory.getLogger(ApplicationReadyService.class);

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		logger.trace("Beginning to parse dates");
		String fileName = "dates.txt";

		List<String> datesList = new ArrayList();
		try (InputStream inputStream = getClass()
				.getClassLoader().getResourceAsStream(fileName); Stream<String> stream = new BufferedReader(
				new InputStreamReader(inputStream)).lines()) {
			String[] dateFormats = {
					"MM/dd/yy",
					"MMM d, yyyy",
					"MMM-d-yyyy"
			};
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			stream.forEach(date -> {
				try {
					Date parsedDate = DateUtils.parseDateStrictly(date, dateFormats);
					datesList.add(formatter.format(parsedDate));
				} catch (ParseException e) {
					logger.error("{} is an invalid date.", date, e);
				}
			});
		} catch (IOException ioe) {
			logger.error("IO Exception reading from file.", ioe);
		}

		if (!datesList.isEmpty()) {
			RoverListDTO roverListDTO = nasaClient.getRoverListDTO();
			List<RoverDTO> roverDTOList = roverListDTO.getRovers();
			roverDTOList.forEach(rover -> {
				datesList.stream().forEach(date -> {
					nasaClient.getPhotoListDTO(rover.getName(), date, null);
				});
			});

			for(RoverDTO roverDTO : roverDTOList) {
				String roverName = roverDTO.getName();
				for(String date: datesList) {
					PhotoListDTO photoListDTO = nasaClient.getPhotoListDTO(roverName, date, null);
					photoService.processPhotos(photoListDTO.getPhotos());
				}

			}

		}


	}
}


