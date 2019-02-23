package com.mspandrade.imageloader.config.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

@Configuration
public class MongoDbProvider {

	private MongoDbFactory mongoDbFactory;
	private MongoConverter mongoConverter;
	
	@Autowired
	public MongoDbProvider(
			MongoDbFactory mongoDbFactory,
			MongoConverter mongoConverter
			) {
		this.mongoDbFactory = mongoDbFactory;
		this.mongoConverter = mongoConverter;
	}
	
	@Bean
	public GridFsTemplate gridFsTemplate() {
		return new GridFsTemplate(mongoDbFactory, mongoConverter);
	}
	
	@Bean public GridFSBucket getGridFSBuckets() {
		MongoDatabase db = mongoDbFactory.getDb();
		return GridFSBuckets.create(db);
	}
}
