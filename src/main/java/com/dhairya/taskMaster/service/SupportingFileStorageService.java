package com.dhairya.taskMaster.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SupportingFileStorageService {

	public static final String STORAGE_DIRECTORY = "C:\\Users\\Dhairya.Jain\\Desktop\\fileUploads" ;
	
	public String save(MultipartFile fileToSave) throws Exception {
		
		if(fileToSave == null) {
			throw new FileNotFoundException("Required file is not found");
		}
		
		var targetFile = new File(STORAGE_DIRECTORY + File.separator + fileToSave.getOriginalFilename());
		
		if(!Objects.equals(targetFile.getParent(), STORAGE_DIRECTORY)) {
			throw new SecurityException("Invalid filename!");
		}
		
		Files.copy(fileToSave.getInputStream(),targetFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
		return targetFile.getPath();
	}

	public static void getZip(List<File> supportingDocumentList, OutputStream byteArrayOutputStream) throws Exception {
		
		ZipOutputStream outputStream = new ZipOutputStream(byteArrayOutputStream);
		
		for(File file : supportingDocumentList) {
			InputStream fis = new FileInputStream(file);
			ZipEntry zipEntry =  new ZipEntry(file.getName());
			outputStream.putNextEntry(zipEntry);
			
			byte[] bytes = new byte[1004];
			int length;
			while((length = fis.read(bytes))>=0) {
				outputStream.write(bytes,0,length);
			}
			fis.close();
		}
		outputStream.close();
		
		
	}

}
