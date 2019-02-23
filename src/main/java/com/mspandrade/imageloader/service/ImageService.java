package com.mspandrade.imageloader.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
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

	private GridFsTemplate gridFSTemplate;
	private GridFSBucket gridFSBucket;
	private Random random;
	
	@Autowired
	public ImageService(GridFsTemplate gridFSTemplate, GridFSBucket gridFSBucket) {
		this.gridFSTemplate = gridFSTemplate;
		this.gridFSBucket = gridFSBucket;
		this.random = new Random();
	}
	
	public UploadFileResponse save(InputStream stream) throws IOException {
		
		stream = new ByteArrayInputStream(resize(stream).toByteArray());
		
		String filename = "image-" + (new Date().getTime()) + "-" + random.nextLong();
		
		ObjectId objectId = gridFSTemplate.store(stream, filename, new BasicDBObject());
		
		return new UploadFileResponse(
				objectId.toString(), 
				objectId.getDate()
				);
	}
	
	public InputStream findById(String id) throws IOException {
		
		GridFSFile gridFSFile = gridFSTemplate.findOne(new Query(Criteria.where("_id").is(id)));
		
		GridFsResource resource = new GridFsResource(
				gridFSFile, 
				gridFSBucket.openDownloadStream(gridFSFile.getObjectId())
				);

		return resource.getInputStream();
	}
	
	public ByteArrayOutputStream resize(InputStream stream, Integer maxWidth, Integer maxheight) throws IOException { 
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
        Thumbnails.of(ImageIO.read(stream))
        		  .size(maxWidth, maxheight)
        		  .outputFormat(format)
        		  .toOutputStream(outputStream);
        
        return outputStream;
	}
	
	public ByteArrayOutputStream resize(InputStream stream) throws IOException {
		return resize(stream, maxWidth, maxheight);
	}
	
}
