package com.anuragntl.pictolabels;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PictolabelsApplication {

	public static void main(String[] args) {
		File uploadDir=new File("uploads");
		if(!uploadDir.exists())
			uploadDir.mkdir();
		SpringApplication.run(PictolabelsApplication.class, args);
	}

}
