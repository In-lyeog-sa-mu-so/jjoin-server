package org.dongguk.jjoin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class FileService {
    @Value("${image.dir}")
    private String FileDir;

    public String getFullPath(String FileName) {
        return FileDir + FileName;
    }

    private String getUuidName(String originalFileName) {
        return UUID.randomUUID().toString()
                + originalFileName.substring(originalFileName.lastIndexOf('.'));
    }

    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String originalFileName = file.getOriginalFilename();
        String uuidFileName = getUuidName(originalFileName);
        file.transferTo(new File(getFullPath(uuidFileName)));
        return uuidFileName;
    }
}
