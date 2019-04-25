package com.anuragntl.pictolabels.components;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import java.util.Hashtable;
import com.anuragntl.pictolabellib.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.io.File;
import java.awt.Rectangle;
import java.util.Map;
import java.util.Iterator;
import java.awt.image.BufferedImage;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PicToLabelService
{
    private Hashtable<String,PicToLabel> models=new Hashtable<>();
    private ModelProperties properties=new ModelProperties();
    private Hashtable<String,ArrayList<File>> outputFiles=new Hashtable<String,ArrayList<File>>();
    private class PicToLabelConverter implements Runnable
    {
        private String id;
        private File inputImages[],outputDir;
        private double threshold;
        public PicToLabelConverter(String id,File inputImages[],File outputDir,double threshold)
        {
            this.id=id;
            this.inputImages=inputImages;
            this.outputDir=outputDir;
            this.threshold=threshold;
        }
        public void run()
        {
            try
            {
            if(models.get(id)==null)
            models.put(id,new PicToLabel(properties));
            PicToLabel picToLabel=models.get(id);
            ArrayList<File> outputs;
            if(outputFiles.get(id)==null)
            {
                outputs=new ArrayList<File>();
                outputFiles.put(id,outputs);
            }
            else
            outputs=outputFiles.get(id);
            for(File image : inputImages)
            {
                BufferedImage bufferedImage=ImageIO.read(image);
                Map<String,Rectangle> boxes=picToLabel.getCroppableAreas(picToLabel.getDetectedObjects(image,threshold));
                for(Iterator<String> objectSet=boxes.keySet().iterator();objectSet.hasNext();)
                {
                    String object=objectSet.next();
                    Rectangle rect=boxes.get(object);
                    File outputFile=new File(outputDir+"/"+object+".jpg");
                    ImageIO.write(ImageCropper.crop(bufferedImage
                    ,rect.x,rect.y,rect.width,rect.height),"JPG",outputFile);
                    outputs.add(outputFile);
                }
            }

        }
        catch(Exception exceptn)
        {
            throw new RuntimeException(exceptn.toString());
        }
    }

    };
    public PicToLabelService()
    {
        
    }
    public void generateOutput(String id,File inputFiles[],File outputDir,double threshold)
    {
        Thread thread=new Thread(new PicToLabelConverter(id,inputFiles,outputDir,threshold));
        thread.start();
    }
    public ArrayList<File> getOutputFiles(String id)
    {
        return outputFiles.get(id);
    }
};
