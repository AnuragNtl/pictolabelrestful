import org.springframework.context.annotataion.Scope;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import java.util.Hashtable;
import com.anuragntl.pictolabellib.*;

@Service
@Scope(ConfigurableBeanFactory.SINGLETON)
public class PicToLabelService
{
    private Hashtable<String,PicToLabel> models=new Hashtable<>();
    private Hashtable<String,Integer> processed=new Hashtable<>();
    private ModelProperties properties=new ModelProperties();
    private class PicToLabelConverter implements Runnable
    {
        private String id;
        private File inputImages[],outputDir;
        public PicToLabelConverter(String id,File inputImages[],File outputDir)
        {
            this.id=id;
            this.inputImages=inputImages;
            this.outputDir=outputDir;
        }
        public void run()
        {
            if(models.get(id)==null)
            models.put(id,new PicToLabel(properties));
            PicToLabel picToLabel=models.get(id);
            for(File image : inputImages)
            {
                picToLabel.getDetectedObjects(image,4.5F)
            }
            
        }
    };
    public PicToLabelService()
    {
        
    }
    public void generateOutput(String id,File inputFiles[],File outputDir,double threshold)
    {
        
    }
};
