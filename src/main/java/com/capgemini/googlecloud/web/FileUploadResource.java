package com.capgemini.googlecloud.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.capgemini.googlecloud.helper.StorageSample;
import org.capgemini.googlecloud.helper.VisionOCRAnalysis;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Component;

/**
 * This class exposes the REST services related to file upload
 * 
 * @author
 *
 */

@Component
@Path("/file")
public class FileUploadResource extends BaseResource {

	private static final Logger logger = Logger.getLogger(FileUploadResource.class);

	/**
	 * This method uploads the particular image from a directory for
	 * organization logo
	 * 
	 * @param uploadedInputStream
	 * @param fileDetail
	 * @return byte[]
	 */
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response FileUploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		System.out.println("In upload method of FileUploadResource");
		System.out.println("filename:" + fileDetail.getFileName());
		
		String uploadedFileLocation = "/tmp/" + fileDetail.getFileName();
		Integer byteCount = writeToFile(uploadedInputStream, uploadedFileLocation);
		String ocrResp = null;
		
		try {
			File file = new File(uploadedFileLocation);
			System.out.println("Before Upload " + fileDetail.getFileName());
			StorageSample.uploadFile(fileDetail.getFileName(), "image/jpg", file, "poc-importbills");
			System.out.println("After Upload 1" + fileDetail.getFileName());
			VisionOCRAnalysis ocr = new VisionOCRAnalysis();
			System.out.println("Before ocr " + fileDetail.getFileName());
			ocrResp = ocr.OCRAnalysis(fileDetail.getFileName());
			System.out.println("After ocr" + fileDetail.getFileName());
			System.out.println("response length = " + ocrResp.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("filesize:" + byteCount);
		return Response.ok(ocrResp).build();
	}

	  // save uploaded file to new location
	  /**
	   * This method save uploaded file to new location
	 * @param uploadedInputStream
	 * @param uploadedFileLocation
	 */
	private Integer writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		Integer byteCount = 0;
	    try {
	      FileOutputStream out = new FileOutputStream(new File(uploadedFileLocation));
	      int read = 0;
	      byte[] bytes = new byte[1024];
	      
	      while ((read = uploadedInputStream.read(bytes)) != -1) {
	        out.write(bytes, 0, read);
	        byteCount += read;
	      }
	      out.flush();
	      out.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return byteCount;
	  }
	
}
