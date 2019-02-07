package com.deltax.imdb.services.file;

import com.deltax.imdb.constants.GeneralConstants;
import com.deltax.imdb.db.model.file.File;
import com.deltax.imdb.db.model.file.FileRepository;
import com.deltax.imdb.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public long addFile(MultipartFile multipartFile) throws DataException
    {
        FileOutputStream fileOutputStream = null;
        try
        {
            LOGGER.info("Uploading a new file");
            File file = new File();
            final String fileName = multipartFile.getOriginalFilename();
            final String filePath = env.getProperty("file.saving.path");

            fileOutputStream = new FileOutputStream(filePath + fileName);
            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.close();
            file.setFileName(fileName);
            fileRepository.save(file);
            return file.getFileId();

        }
        catch (Exception e)
        {
            LOGGER.error(GeneralConstants.ERROR,e);
            throw new DataException(GeneralConstants.FAULT,GeneralConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
