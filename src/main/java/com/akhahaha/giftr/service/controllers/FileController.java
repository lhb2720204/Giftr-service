package com.akhahaha.giftr.service.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class FileController {
	
	private String ROOT = "src/main/webapp/image";

//	@RequestMapping(method = RequestMethod.GET, value = "/image")
//	public String provideUploadInfo(Model model) {
//		return "uploadForm";
//	}

    /**
     * Upload image
     */
	@RequestMapping(method = RequestMethod.POST, value = "/image")
	@ResponseBody
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
								   Principal principal) {
		String username = principal.getName();
		String filename = file.getOriginalFilename();
		String ext = "";

		// get file extension
		int i = filename.lastIndexOf('.');
		if (i > 0) {
		    ext = filename.substring(i+1);
		}
		
		if (filename.contains("/")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Folder separators in filename not allowed");
		}
		if (ext.equalsIgnoreCase("jpg")) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(ROOT + "/" + username + ".jpg")));
                FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();
			}
			catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File was either empty or of unsupported extension");
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Image was uploaded successfully");
	}
}