package top.lldwb.file.saving.tool.server.controller;

import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lldwb.file.saving.tool.server.config.MinIoConfig;
import top.lldwb.file.saving.tool.server.entity.Magic;
import top.lldwb.file.saving.tool.server.vo.ResultVO;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/11/30
 * @time 19:16
 * @PROJECT_NAME file_saving_tool_backend
 */
@RestController
@RequestMapping("/minio")
@RequiredArgsConstructor
public class MinIoController extends BaseController {
    private final MinioClient minioClient;

    @PostMapping("/addFile")
    public ResultVO addFile(MultipartFile multipartFile) throws Exception {
        InputStream inputStream = multipartFile.getInputStream();
        minioClient.putObject(PutObjectArgs.builder().bucket(MinIoConfig.BUCKET).object(multipartFile.getOriginalFilename()).stream(inputStream, multipartFile.getSize(), -1).contentType(multipartFile.getContentType()).build());
        return success();
    }

    @GetMapping("/download/{path}")
    public ResponseEntity<InputStreamResource> download(@PathVariable("path") String path) throws Exception {
        InputStream inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(MinIoConfig.BUCKET).object(path).build());

        // 对文件名进行编码，防止在响应头中出现乱码
        String fileName = URLEncoder.encode(path, "UTF-8");
        // 设置响应头，告诉浏览器响应的是流数据
        HttpHeaders headers = new HttpHeaders();
        // 设置头信息，将响应内容处理的方式设置为附件下载
        headers.setContentDispositionFormData("attachment", fileName);
        // 设置响应类型为流类型
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // 创建 InputStreamReader 对象封装输入流，用于读取服务器文件
        InputStreamResource inputStreamReader = new InputStreamResource(inputStream);
        // 创建 ResponseEntity 对象，封装(InputStreamReader，响应头 HttpHeaders，状态码 201)
        // 200 成功，201 成功但是还有数据
        ResponseEntity<InputStreamResource> response = new ResponseEntity<>(inputStreamReader, headers, HttpStatus.CREATED);
        return response;
    }

    @GetMapping("/fileList")
    public ResultVO<List<Magic>> fileList(String path) {
        // 创建一个Magic类型的List
        List<Magic> list = new ArrayList<>();
        // 调用Minio的listObjects方法，获取指定路径下的文件列表
        minioClient.listObjects(ListObjectsArgs.builder().bucket(MinIoConfig.BUCKET).prefix(path).build()).forEach(itemResult -> {
            try {
                // 获取文件信息
                Item item = itemResult.get();
                // 创建一个Magic类型的对象
                Magic magic = new Magic();
                // 设置文件名
                magic.setName(StringUtils.getFilename(item.objectName()));
                // 设置文件路径
                magic.setPath(item.objectName());
                // 设置文件大小
                magic.setSize(item.size() > 1024 * 1024 ? item.size() / 10240 / 1024.0 + "MB" : item.size() / 1024.0 + "KB");
                // 将文件信息添加到list中
                list.add(magic);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        // 返回文件列表
        return success(list);
    }
}
