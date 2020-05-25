package com.pewitt.nasa.service;

import com.pewitt.nasa.dto.PhotoDTO;
import com.pewitt.nasa.dto.PhotoListDTO;
import java.io.File;
import java.util.List;
import java.util.Optional;

public interface PhotoService {

	File requestPhoto(String name);

	String processPhoto(PhotoDTO photoDTO);

	List<String> processPhotos(List<PhotoDTO> photoListDTOs);
}
