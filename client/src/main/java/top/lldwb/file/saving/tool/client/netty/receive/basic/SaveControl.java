package top.lldwb.file.saving.tool.client.netty.receive.basic;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.client.config.ClientConfig;
import top.lldwb.file.saving.tool.client.netty.ClientHandler;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.service.control.ControlService;

import java.io.*;
import java.util.Map;

/**
 * 绑定操作
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/7
 * @time 9:48
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service("save")
public class SaveControl implements ControlService {
    @Override
    public void control(SocketMessage message) {
        Client client = Convert.convert(Client.class, message.getData());
        log.info("接收的UUID：" + client.getClientSecretKey());
        // 客户端绑定成功，发送会服务端进行添加客户端操作
        ClientHandler.ctx.writeAndFlush(client);
    }

    private void setClient(Client client){
        // 向本地添加client对象用于标识为绑定
        try {
            // 创建一个输出流
            OutputStream outputStream = new FileOutputStream(ClientHandler.PATH);
            // 创建一个缓冲流
            outputStream = new BufferedOutputStream(outputStream);
            // 创建一个序列号流
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            // 将对象写入序列化流
            objectOutputStream.writeObject(client);
            // 关闭流
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
