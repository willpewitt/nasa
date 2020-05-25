package com.pewitt.nasa.rest;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.pewitt.nasa.constants.Camera;
import com.pewitt.nasa.dto.PhotoListDTO;
import com.pewitt.nasa.dto.RoverListDTO;
import com.pewitt.nasa.exceptions.PhotoException;
import java.io.InputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.stereotype.Component;

@Component
public class NasaClient {

	private static final String NASA_API_TOKEN = "8GP5mFKhi0JDd0XVCkNLTbF9SN8J2lL8rezyaDAo";
	private static final String NASA_API_URL = "https://api.nasa.gov/mars-photos/api/v1";


	private static final String API_KEY_PARAM_NAME = "api_key";
	private static final String DATE_PARAM_NAME = "earth_date";
	private static final String CAMERA_PARAM_NAME = "camera";


	private final JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider().configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	private Client client = ClientBuilder.newClient(new ClientConfig(jacksonJsonProvider));

	public RoverListDTO getRoverListDTO() {
		RoverListDTO roverNamesList =
				client.target(NASA_API_URL + "/rovers").queryParam(API_KEY_PARAM_NAME, NASA_API_TOKEN)
						.request().get(RoverListDTO.class);

		return roverNamesList;
	}

	public PhotoListDTO getPhotoListDTO(String rovername, String date, Camera camera) {
		String url = String.format("%s/rovers/%s/photos/", NASA_API_URL, rovername);

		PhotoListDTO photoListDTO = client.target(url).queryParam(API_KEY_PARAM_NAME, NASA_API_TOKEN)
				.queryParam(DATE_PARAM_NAME, date).queryParam(CAMERA_PARAM_NAME, camera)
				.request(MediaType.APPLICATION_JSON).get(PhotoListDTO.class);

		return photoListDTO;
	}

	public InputStream getPhoto(String url) {
		return client.target(url).request().get(InputStream.class);
	}


}
