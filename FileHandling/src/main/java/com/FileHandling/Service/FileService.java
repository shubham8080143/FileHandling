package com.FileHandling.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	private final String uploadDir = "uploads/";

	public String uploadFile(MultipartFile file) throws IOException {
		File directory = new File(uploadDir);
		if (!directory.exists())
			directory.mkdirs();

		String filePath = uploadDir + file.getOriginalFilename();
		file.transferTo(new File(filePath));
		return filePath;
	}

	public byte[] getFile(String fileName) throws IOException {
		Path path = Paths.get(uploadDir + fileName);
		return Files.readAllBytes(path);
	}

	public boolean deleteFile(String fileName) {
		File file = new File(uploadDir + fileName);
		return file.exists() && file.delete();
	}
}
