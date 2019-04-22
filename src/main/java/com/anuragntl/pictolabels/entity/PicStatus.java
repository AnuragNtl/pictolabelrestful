package com.anuragntl.pictolabels.entity;

public class PicStatus {
private boolean uploaded;
private String url;
private String link;
public PicStatus(){}
public PicStatus(String fileName,String url,boolean status)
{
    this.fileName=fileName;
    this.status=status;
    this.url=url;
}
public String getFileName()
{
    return fileName;
}
public boolean getStatus()
{
    return status;
}
public String getUrl()
{
    return url;
}
}
