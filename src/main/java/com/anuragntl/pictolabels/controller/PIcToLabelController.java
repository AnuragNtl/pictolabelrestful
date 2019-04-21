package com.anuragntl.pictolabels.controller;

import org.assertj.core.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anuragntl.pictolabels.entity.PicStatus;
import java.nio.file.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PIcToLabelController {
@GetMapping("/getTmpSpace")
public Map<String,String> genTmpSpace()
{
	String id=UUID.randomUUID().toString();
	File tmpSpace=new File("uploads/"+id+"/outputs");
	tmpSpace.mkdirs();
	return new HashMap<String,String>(){{
		put("id",id);
	}};
}
@PostMapping("/addPics")
public List<PicStatus> addPic(@RequestParam("files") MultipartFile files[],@ModelAttribute("id")
String id)
{
	return Arrays.asList(files).stream().map((t)->
	{
		return addPic((MultipartFile)t,id);
	}).collect(Collectors.toList());
}
@PostMapping("/addPic")
public PicStatus addPic(@RequestParam("file") MultipartFile file,@ModelAttribute("uploadPath")
String uploadPath)
{
	Path target=Paths.get(uploadPath).toAbsolutePath().normalize().resolve(file.getName());
	try
	{
	Files.copy(file.getInputStream(),target,StandardCopyOption.REPLACE_EXISTING);
	}
	catch(IOException ioexcepn)
	{
		throw new RuntimeException(ioexcepn);
	}
	return new PicStatus();
}

}
