package top.lldwb.file.saving.tool.client.netty.receive.basic;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.lldwb.file.saving.tool.client.netty.ClientHandler;
import top.lldwb.file.saving.tool.pojo.dto.SocketMessage;
import top.lldwb.file.saving.tool.pojo.entity.Client;
import top.lldwb.file.saving.tool.service.control.ControlService;
import top.lldwb.file.saving.tool.service.send.SendService;

import java.io.*;

/**
 * 第一次连接操作
 *
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/15
 * @time 9:14
 * @PROJECT_NAME file_saving_tool_backend
 */
@Slf4j
@Service("virgin")
@RequiredArgsConstructor
public class VirginControl implements ControlService {
    private final SendService nettySend;

    @Override
    public void control(SocketMessage message) {
        String sha256Hex = Convert.convert(String.class, message.getData());
        log.info("本机秘钥：{}", sha256Hex);
        ClientHandler.sha256Hex = sha256Hex;

        SocketMessage<Client> socketMessage = new SocketMessage<>();
        socketMessage.setClazz(Client.class);
        Client client = new Client();
        // 获取客户端对象
        File file = new File(ClientHandler.PATH);
        // 如果有客户端对象
        if (file.isFile() && getClient() != null) {
            client = getClient();
        }
        // 如果没有客户端对象
        else {
            client.setUserId(0);
        }

        // 设置为更新客户端操作
        socketMessage.setControlType("updateClient");
        client.setClientSecretKey(sha256Hex);
        socketMessage.setData(client);
        nettySend.send(socketMessage);
    }

    private Client getClient() {
        Client client = null;
        try {
            // 创建一个输入流
            InputStream inputStream = new FileInputStream(ClientHandler.PATH);
            // 创建一个缓冲流
            inputStream = new BufferedInputStream(inputStream);
            // 创建一个反序列化流
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            // 从反序列化流中读取出Object对象
            Object object = objectInputStream.readObject();
            // 类型转换
            client = Convert.convert(Client.class, object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return client;
    }
}
