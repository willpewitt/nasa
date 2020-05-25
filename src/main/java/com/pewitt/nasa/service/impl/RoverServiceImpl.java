package com.pewitt.nasa.service.impl;

import com.pewitt.nasa.rest.NasaClient;
import com.pewitt.nasa.dto.RoverDTO;
import com.pewitt.nasa.dto.RoverListDTO;
import com.pewitt.nasa.exceptions.RoverException;
import com.pewitt.nasa.service.RoverService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoverServiceImpl implements RoverService {

	private NasaClient nasaClient;

	public RoverServiceImpl(NasaClient nasaClient) {
		this.nasaClient = nasaClient;
	}

	private Logger logger = LoggerFactory.getLogger(RoverServiceImpl.class);

	@Override
	public List<String> getRoverNames() {
		RoverListDTO roverListDTO = nasaClient.getRoverListDTO();
		if (roverListDTO.getRovers() == null || roverListDTO.getRovers().isEmpty()) {
			String unableToGetRovers = "Unable to get Rover names.";
			logger.error(unableToGetRovers);
			throw new RoverException(unableToGetRovers);
		}

		return roverListDTO.getRovers().stream().map(RoverDTO::getName).collect(Collectors.toList());
	}
}
