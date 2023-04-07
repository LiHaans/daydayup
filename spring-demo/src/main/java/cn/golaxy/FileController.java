package cn.golaxy;

import cn.golaxy.util.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("upload")
public class FileController {


    @PostMapping(value = "file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void test(@RequestPart(value = "file") MultipartFile file
            , @RequestPart(value = "params") String params) throws Exception {
        System.out.println(params);
        String tmpDir = FileUtils.createTmpDir();
        FileUtils.saveInputStreamToLocal(file.getInputStream(), tmpDir, file.getOriginalFilename());

        FileUtils.unzip(tmpDir+ file.getOriginalFilename(), tmpDir);

    }
}
