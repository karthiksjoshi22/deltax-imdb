package com.deltax.imdb.services.file;

import com.deltax.imdb.exception.DataException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    long addFile(MultipartFile multipartFile)throws DataException;
}
