package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;//고객이 실제 등록한 filename
    private String storeFileName; // 관리하는 filename

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
