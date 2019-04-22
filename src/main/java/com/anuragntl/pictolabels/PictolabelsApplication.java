package com.anuragntl.pictolabels;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.anuragntl.pictolabels.config.UploadProperties;
@SpringBootApplication
@EnableConfigurationProperties()
public class PictolabelsApplication {

	public static void main(String[] args) {
		File uploadDir=new File("uploads");
		if(!uploadDir.exists())
			uploadDir.mkdir();
		SpringApplication.run(PictolabelsApplication.class, args);
	}

}
