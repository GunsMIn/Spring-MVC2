package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    //@Value : applicaion.properties의 속성을 그대로 가져올 수 있다.
    @Value("${file.dir}")
    private String fileDir;


    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
       log.info("request={}",request);

        String itemName = request.getParameter("itemName");
        log.info("itemName={}",itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts={}",parts);

        for (Part part : parts) {
            log.info("=====part=====");
            log.info("name={}",part.getName());
            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) {
                log.info("header{}:{}",headerName,part.getHeader(headerName));
            }

            //편의메서드
            //content-disposition; filename
            log.info("submitteFilename={}",part.getSubmittedFileName());
            log.info("size={}",part.getSize());

            //데이터 읽기
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            log.info("body={}",body);

            //파일에 저장하기
            //StringUtils.hasText(값); 을 사용하 값이 있을 경우에는 true를 반환하고
            //공백이나 NULL이 들어올 경우에는 false를 반환하게 된다
            if(StringUtils.hasText(part.getSubmittedFileName())){
                //디렉토리에다가 파일명까지 합쳐진 것
                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("파일 저장 fullPath={}",fullPath);
                part.write(fullPath);
            }
        }
       return "upload-form";
    }
}
