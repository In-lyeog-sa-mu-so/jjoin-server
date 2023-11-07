package org.dongguk.jjoin.util;

import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.type.ImageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {
    @Value("${file.dir}")
    private String FileDir;

    public String getFullPath(String FileName) {
        return FileDir + FileName;
    }

    public String getFileExtension(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
    }

    private String getUuidName(String originalFileName) {
        return UUID.randomUUID().toString() + '.' + getFileExtension(originalFileName);
    }

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        String originalFileName = file.getOriginalFilename();
        String uuidFileName = getUuidName(originalFileName);
        try {
            file.transferTo(new File(getFullPath(uuidFileName)));
        } catch (IOException e) {
            return null;
        }
        return uuidFileName;
    }
}
