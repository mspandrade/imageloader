package com.mspandrade.imageloader.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.mspandrade.imageloader.data.UploadFileResponse;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageService {
	
	@Value("${image.max.width}")
	private Integer maxWidth;
	
	@Value("${image.max.height}")
	private Integer maxheight;
	
	@Value("${image.format}")
	private String format;

	private GridFsTemplate gridFS;
	
	@Autowired
	public ImageService(GridFsTemplate gridFS) {
		this.gridFS = gridFS;
	}
	
	public UploadFileResponse save(InputStream stream) throws IOException {
		
		stream = new ByteArrayInputStream(resize(stream).toByteArray());
		
		GridFSFile gridFSFile = gridFS.store(stream, new BasicDBObject());
		
		return new UploadFileResponse(
				gridFSFile.getId().toString(), 
				gridFSFile.getUploadDate()
				);
	}
	
	public InputStream findById(String id) throws IOException {
		
		GridFSDBFile gridFSDBFile = gridFS.findOne(new Query(Criteria.where("_id").is(id)));
		
		if (gridFSDBFile == null) {
			
			throw new FileNotFoundException(id);
		}
		
		return new GridFsResource(gridFSDBFile).getInputStream();
	}
	
	public ByteArrayOutputStream resize(InputStream stream) throws IOException {
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
        Thumbnails.of(ImageIO.read(stream))
        		  .size(maxWidth, maxheight)
        		  .outputFormat(format)
        		  .toOutputStream(outputStream);
        
        return outputStream;
	}
	
}
