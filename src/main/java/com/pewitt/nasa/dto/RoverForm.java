package com.pewitt.nasa.dto;

import com.pewitt.nasa.constants.Camera;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class RoverForm {


	@NotBlank(message = "Date must be selected and in yyyy-MM-dd format.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
 	private String date = null;

	@NotBlank(message = "Rover name must be selected")
	private String rover;

	private Camera camera;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRover() {
		return rover;
	}

	public void setRover(String rover) {
		this.rover = rover;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
