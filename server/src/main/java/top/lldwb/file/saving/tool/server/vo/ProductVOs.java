package top.lldwb.file.saving.tool.server.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductVOs {
    /**
     * 名称
     */
    private String name;
    /**
     * SpringMVC 封装的上传附件对象
     * 图片
     */
    private MultipartFile[] file;
}
