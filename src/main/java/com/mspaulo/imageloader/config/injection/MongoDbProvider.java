package com.mspaulo.imageloader.config.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

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
}
