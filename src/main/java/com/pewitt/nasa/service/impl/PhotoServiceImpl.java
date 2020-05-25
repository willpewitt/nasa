package com.pewitt.nasa.service.impl;

import com.pewitt.nasa.dto.PhotoDTO;
import com.pewitt.nasa.dto.PhotoListDTO;
import com.pewitt.nasa.rest.NasaClient;
import com.pewitt.nasa.service.PhotoService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.RedirectionException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

	private NasaClient nasaClient;

	public PhotoServiceImpl(final NasaClient nasaClient) {
		this.nasaClient = nasaClient;
	}

	private Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);


	@Override
	public File requestPhoto(final String name) {
		final String imageFileName = new StringBuilder("/tmp/").append(name).toString();

		File image = null;
		if (Files.exists(Paths.get(imageFileName))) {
			image = Paths.get(imageFileName).toFile();
		}


		return image;
	}

	@Override
	public String processPhoto(final PhotoDTO photoDTO) {


		String imgSrc = photoDTO.getImgSrc();
		String name = DigestUtils.sha512Hex(imgSrc);
		String encodedFileName =
				new StringBuilder("/tmp/").append(name).toString();
		try {
			if (!Files.exists(Paths.get(encodedFileName))) {
				InputStream inputStream = nasaClient.getPhoto(imgSrc);
				Path cachedFile = Files.createFile(Paths.get(encodedFileName));
				Files.copy(inputStream, cachedFile, StandardCopyOption.REPLACE_EXISTING);
				IOUtils.closeQuietly(inputStream);
			}
		} catch (IOException ioe) {
			logger.error("Could not request photo.", ioe);
		} catch (RedirectionException re) {
			logger.warn("Could not get rover image, 301", re);
		} catch (Exception e) {
			logger.error("Could not process photo", e);
		}
		return name;

	}

	@Override
	public List<String> processPhotos(final List<PhotoDTO> photoDTOList) {
		if (photoDTOList == null && photoDTOList.isEmpty()) {
			return null;
		}

		List<String> photoNames =
				photoDTOList.stream().map(photoDTO -> processPhoto(photoDTO)).collect(Collectors.toList());

		return photoNames;
	}
}
