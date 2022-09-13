package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    //파일 저장 경로
    @Value("${file.dir}")
    private String fileDir;  //C:/Users/kimgunwoo/study/file/

    public String getFullPath(String fileName){
        return fileDir + fileName;
    }




    //파일 저장 메소드
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); // 실제 파일명
        String storeFileName = createStoreFileName(originalFilename); // 서버 관리 파일명
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        //createStoreFileName() : 서버 내부에서 관리하는 파일명은 유일한 이름을 생성하는 UUID 를 사용해서
        //충돌하지 않도록 한다.

        return new UploadFile(originalFilename, storeFileName);



    }

    //여러개의 이미지 파일을 올리는 메소드
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                UploadFile uploadFile = storeFile(multipartFile);
                storeFileResult.add(uploadFile);
            }
        }

        return storeFileResult;
    }


    //확장자 추출 메소드
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        //subString(i) : i인덱스부터 끝까지 가져오는것이다
        return originalFilename.substring(pos + 1);//.뒤부터 짤라서 가져온다
    }

    //서버 저장 파일명 생성메소드
   private String createStoreFileName(String originalFilename) {
        //서버에 저장하는 파일명
        String uuid = UUID.randomUUID().toString();
        //서버에 저장할 때 .png같은 확장자는 그대로 남겨두고 싶을때
        String ext = extractExt(originalFilename);//이 메소드를 통해서 확장자 명을 추출
        String storeFileName = uuid + "." + ext;

        return storeFileName;
    }


}
