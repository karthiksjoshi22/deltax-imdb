package com.deltax.imdb.ws.producer;

import com.deltax.imdb.controller.AbstractController;
import com.deltax.imdb.exception.DataException;
import com.deltax.imdb.services.file.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rest/file")
public class FileWS extends AbstractController {

    @Autowired
    private FileService fileService;

    @ApiOperation("Uploads a new file/image and saves it in the path specified")
    @RequestMapping(value = "/upload",method = RequestMethod.POST,
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addFile(@RequestParam("file")MultipartFile file)
    {
        try
        {
            return buildResponse(fileService.addFile(file),"File uploaded successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }
}
