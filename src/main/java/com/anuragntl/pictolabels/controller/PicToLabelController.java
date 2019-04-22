package com.anuragntl.pictolabels.controller;

import java.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import com.anuragntl.pictolabels.entity.PicStatus;
import java.nio.file.Files;
import org.springframework.core.io.Resource;
import java.io.File;
import java.io.IOException;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import com.anuragntl.pictolabels.components.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;

@RestController
public class PicToLabelController {
    @Autowired
    private FileManager fileManager;
@GetMapping("/getTmpSpace")
public Map<String,String> genTmpSpace()
{
	String id=UUID.randomUUID().toString();
	fileManager.generateTempSpaceForId(id);
	return new HashMap<String,String>(){{
		put("id",id);
	}};
}
@PostMapping("/addPics")
public List<PicStatus> addPic(@RequestParam("files") MultipartFile files[],@ModelAttribute("id")
String id)
{
    System.out.println(files.length);
	return Arrays.asList(files).stream().map((t)->
	{
		return addPic(t,id);
	}).collect(Collectors.toList());
}
@PostMapping("/addPic")
public PicStatus addPic(@RequestParam("file") MultipartFile file,@ModelAttribute("id")
String id)
{
    String uploadPath=fileManager.getUploadPath(id);
	Path target=Paths.get(uploadPath).toAbsolutePath().normalize().resolve(file.getName());
	try
	{
	Files.copy(file.getInputStream(),target,StandardCopyOption.REPLACE_EXISTING);
	}
	catch(IOException ioexcepn)
	{
		throw new RuntimeException(ioexcepn);
	}
	return new PicStatus(file.getName(),"/getPic/"+file.getName(),true);
}
@GetMapping("/getPic/{fileName}")
public ResponseEntity<Resource> getPic(HttpServletRequest req,@PathVariable("fileName") String fileName,@RequestParam("id") String id)
{
    try
    {
    Resource resource=fileManager.loadFile(fileManager.getUploadPath(id)+"/"+fileName);
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(req.getServletContext()
    .getMimeType(resource.getFile().getAbsolutePath())
    )).
    header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"").
    body(resource);
}
catch(Throwable t)
{
    throw new RuntimeException(t);
}

}
}
