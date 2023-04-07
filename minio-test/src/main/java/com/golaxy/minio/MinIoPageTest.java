package com.golaxy.minio;

import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

public class MinIoPageTest {
    public static void main1(String[] args) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //MinioClient minioClient = new MinioClient("http://192.168.200.101:9000", "AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");

        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://192.168.200.101:9000")
                .credentials("AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY")
                .build();

        ListObjectsArgs listArgs = ListObjectsArgs.builder()
                .startAfter("加.svg")
                .prefix("")
                .maxKeys(20)
                .bucket("test1")
                .build();




        Iterable<Result<Item>> results = minioClient.listObjects(listArgs);
        Iterator<Result<Item>> iterator = results.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Result<Item> next = iterator.next();
            Item item = next.get();
            System.out.println(item.objectName());
            count++;
        }

        System.out.println("count: " + count);
    }

    public static void main(String[] args) throws Exception {
        File file = new File("D:\\lihang\\code\\big-data-governance\\bdg-admin\\pom.xml");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(fileInputStream));

        MinioClient minioClient = MinioClient.builder().endpoint("http://127.0.0.1", 9899, false).credentials("AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY").build();

        minioClient.putObject(PutObjectArgs.builder()
                .bucket("bigdatagovernance")
                .object("file_model_test/aa.png")
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                .contentType(multipartFile.getContentType())
                .build());

    }
}
