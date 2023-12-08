package top.lldwb.file.saving.tool.factory;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;

@Setter
public class MinioFactoryBean implements FactoryBean<MinioClient> {
    /**
     * minio地址
     */
    private String endpoint;
    private String username;
    private String password;
    /**
     * 桶名
     */
    private String bucket;

    @Override
    public MinioClient getObject() throws Exception {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(username, password)
                .build();
        // 初始化筒
        initBucket(minioClient);
        return minioClient;
    }

    private void initBucket(MinioClient minioClient) throws Exception {
        // 先判断桶是否存在，不存在则创建
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    @Override
    public Class<?> getObjectType() {
        return MinioClient.class;
    }
}
