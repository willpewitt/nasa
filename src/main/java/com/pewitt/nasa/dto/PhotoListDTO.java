package com.pewitt.nasa.dto;

import java.util.List;

public class PhotoListDTO {

	List<PhotoDTO> photos;

	public List<PhotoDTO> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoDTO> photoDTOList) {
		this.photos = photoDTOList;
	}
}
