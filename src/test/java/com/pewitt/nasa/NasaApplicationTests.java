package com.pewitt.nasa;

import com.pewitt.nasa.constants.Camera;
import com.pewitt.nasa.dto.PhotoDTO;
import com.pewitt.nasa.dto.PhotoListDTO;
import com.pewitt.nasa.dto.RoverDTO;
import com.pewitt.nasa.dto.RoverListDTO;
import com.pewitt.nasa.rest.NasaClient;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NasaApplicationTests {

	@Autowired
	private NasaClient nasaClient;

	@Test
	public void checkExpectedAmountOfRovers() {
		RoverListDTO roverNames = nasaClient.getRoverListDTO();
		assertThat(roverNames).isNotNull();
		List<RoverDTO> roverDTOList = roverNames.getRovers();
		assertThat(roverDTOList).hasSize(3);
	}

	@Test
	public void checkForPhotots() {
		PhotoListDTO photoListDTO = nasaClient.getPhotoListDTO("curiosity", "2020-01-01", Camera.MAHLI);
		assertThat(photoListDTO).isNotNull();
		PhotoDTO photoDTO = photoListDTO.getPhotos().stream().findFirst().get();
		assertThat(photoDTO).hasFieldOrProperty("id");
		assertThat(photoDTO).hasFieldOrProperty("earthDate");
		assertThat(photoDTO).hasFieldOrProperty("imgSrc");
	}


}
