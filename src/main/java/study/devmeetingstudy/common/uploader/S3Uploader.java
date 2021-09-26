package study.devmeetingstudy.common.uploader;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Uploader implements Uploader{

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public List<Map<String, String>> uploadFiles(List<MultipartFile> multipartFiles, String dirName) throws IOException {
        List<Map<String, String>> uploadImageUrls = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            uploadImageUrls.add(upload(multipartFile, dirName));
        }
        return uploadImageUrls;
    }

    @Override
    public Map<String, String> upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File convert error"));
        return upload(uploadFile, dirName);
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        if (convertFile.createNewFile()){
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private Map<String, String> upload(File uploadFile, String dirName){
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        return getFileInfo(uploadFile.getName(), uploadImageUrl);
    }

    private Map<String, String> getFileInfo(String originalFileName, String uploadImageUrl) {
        Map<String, String> fileInfo = new HashMap<>();
        fileInfo.put(FILE_NAME, originalFileName);
        fileInfo.put(UPLOAD_URL, uploadImageUrl);
        return fileInfo;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()){
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

}

/** TODO : 람다식으로 multiple upload 구현해보기.
 * public class PropagateExceptionsSample {
 *     // a simplified version of Throwables#propagate
 *     public static RuntimeException runtime(Throwable e) {
 *         if (e instanceof RuntimeException) {
 *             return (RuntimeException)e;
 *         }
 *
 *         return new RuntimeException(e);
 *     }
 *
 *     // this is a new one, n/a in public libs
 *     // Callable just suits as a functional interface in JDK throwing Exception
 *     public static <V> V propagate(Callable<V> callable){
 *         try {
 *             return callable.call();
 *         } catch (Exception e) {
 *             throw runtime(e);
 *         }
 *     }
 *
 *     public static void main(String[] args) {
 *         class Account{
 *             String name;
 *             Account(String name) { this.name = name;}
 *
 *             public boolean isActive() throws IOException {
 *                 return name.startsWith("a");
 *             }
 *         }
 *
 *
 *         List<Account> accounts = new ArrayList<>(Arrays.asList(new Account("andrey"), new Account("angela"), new Account("pamela")));
 *
 *         Stream<Account> s = accounts.stream();
 *
 *         s
 *           .filter(a -> propagate(a::isActive))
 *           .map(a -> a.name)
 *           .forEach(System.out::println);
 *     }
 * }
 */