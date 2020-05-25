package com.pewitt.nasa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhotoDTO {

	private String id;
	@JsonProperty("img_src")
	private String imgSrc;
	private String sol;
	@JsonProperty("earth_date")
	private String earthDate;
	private CameraDTO camera;
	private RoverDTO rover;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getSol() {
		return sol;
	}

	public void setSol(String sol) {
		this.sol = sol;
	}

	public String getEarthDate() {
		return earthDate;
	}

	public void setEarthDate(String earthDate) {
		this.earthDate = earthDate;
	}

	public CameraDTO getCamera() {
		return camera;
	}

	public void setCamera(CameraDTO camera) {
		this.camera = camera;
	}

	public RoverDTO getRover() {
		return rover;
	}

	public void setRover(RoverDTO rover) {
		this.rover = rover;
	}
}
