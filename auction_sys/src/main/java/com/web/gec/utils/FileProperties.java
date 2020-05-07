package com.web.gec.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String staticAccessPath;
    private String uploadFileFolder;

    public String getStaticAccessPath() {
        return staticAccessPath;
    }

    public void setStaticAccessPath(String staticAccessPath) {
        this.staticAccessPath = staticAccessPath;
    }

    public String getUploadFileFolder() {
        return uploadFileFolder;
    }

    public void setUploadFileFolder(String uploadFileFolder) {
        this.uploadFileFolder = uploadFileFolder;
    }
}
