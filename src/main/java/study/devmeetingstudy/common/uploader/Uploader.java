package study.devmeetingstudy.common.uploader;

import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface Uploader {

    String FILE_NAME = "fileName";
    String UPLOAD_URL = "uploadUrl";

    Map<String, String> upload(MultipartFile multipartFile, String dirName) throws IOException;
    List<Map<String, String>> uploadFiles(List<MultipartFile> multipartFiles, String dirName) throws IOException;
}
