package study.devmeetingstudy.common.uploader;

import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Uploader {

    String upload(MultipartFile multipartFile, String dirName) throws IOException;

}
