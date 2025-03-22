package com.FileHandling.Controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.FileHandling.Service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {
	private final FileService fileService;

	private final String uploadDir = "C:/myapp/uploads/";

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			return ResponseEntity.ok(fileService.uploadFile(file));
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
		}
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
		try {
			return ResponseEntity.ok(fileService.getFile(fileName));
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/delete/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
		return fileService.deleteFile(fileName) ? ResponseEntity.ok("File deleted")
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
	}
}
