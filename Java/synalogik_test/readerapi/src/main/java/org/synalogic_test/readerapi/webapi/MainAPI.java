package org.synalogic_test.readerapi.webapi;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.synalogic_test.readerapi.countingUtils.CountingUtilsService;
import org.synalogic_test.readerapi.fileuploaddownload.FileStorageService;
import org.synalogic_test.readerapi.model.Document;

@RestController
@RequestMapping("/synalogik/reader")

public class MainAPI {

    //demo db
    Map<Integer,Document> db = new HashMap<Integer,Document>();
    private static final Logger logger = LoggerFactory.getLogger(MainAPI.class);

    @Autowired
    private FileStorageService fileStorageService;

    
    @GetMapping("/health")
    public String isOnline(){
        return "Reader API OK " + new Date();
    }

    
    @PostMapping(path="/processdoc")
    public String  processDocument(@RequestParam("file") MultipartFile file) 
    {
        logger.info("start processing...");
        String fileName = fileStorageService.storeFile(file);
        logger.info("loading uploaded file");   
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        try
        {
            Path path = Paths.get(resource.getFile().getAbsolutePath());
            CountingUtilsService counter = new CountingUtilsService();
            
            logger.info("counting document");
            Document result = counter.readFromPath(path);
            db.put(db.size()+1, result);
            return counter.printDocumentStats(result);
        }catch(Exception ex)
        {
            return "Error occured tring to process document"+ex;
        }

    }

    //example end point to get bible data
    /* @GetMapping("/getbible")
    public String getBibleData()
    {
        Document result = null;
        CountingUtils utils = new CountingUtils();

        try {
            URL urlObject = new URL("https://janelwashere.com/files/bible_daily.txt");
            URLConnection urlConnection = urlObject.openConnection();
            InputStream
             inputStream = urlConnection.getInputStream();
            Document data = utils.readFromInputStream(inputStream);
            result = data;
            db.put(db.size()+1, data);
        } catch(MalformedURLException e ){
            return "malformed url";

        } catch (Exception e) {
            return "error occured getting data from url " + e.getMessage();
        }
       return result.toString();
    } */

    @GetMapping("/getDocs")
    public List<Document>getStoredDocs(){
        List<Document> list = new ArrayList<Document>(db.values());
        return list;
    }


    
}
