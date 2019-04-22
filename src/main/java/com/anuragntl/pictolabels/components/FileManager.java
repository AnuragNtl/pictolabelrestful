package com.anuragntl.pictolabels.components;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import com.anuragntl.pictolabels.config.UploadProperties;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import org.springframework.stereotype.Component;

@Component
public class FileManager
{

private UploadProperties properties;
private Path storagePath;
@Autowired
public FileManager(UploadProperties properties)
{
    this.properties=properties;
this.storagePath=Paths.get(properties.getLocation()).toAbsolutePath().normalize();
}
public Resource loadFile(String fileName)throws IOException
{
Resource resource=new UrlResource(storagePath.resolve(fileName).normalize().toUri());
if(resource.exists())
return resource;
else
throw new IOException("Cannot load file");
}
public void saveFile(InputStream in,String fileName)throws IOException
{
Files.copy(in,storagePath.resolve(fileName),StandardCopyOption.REPLACE_EXISTING);
}
public void generateTempSpaceForId(String id)
{
File tmpSpace=new File(properties.getLocation());
new File(tmpSpace+"/"+id+"/outputs").mkdirs();
}
};

