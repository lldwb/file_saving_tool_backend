package top.lldwb.file.saving.tool.pojo.dto;

import cn.hutool.core.bean.BeanDesc;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.DynaBean;
import cn.hutool.core.convert.Convert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author lldwb
 * @email 3247187440@qq.com
 * @date 2023/12/1
 * @time 11:51
 * @PROJECT_NAME file_saving_tool_backend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMessage<T> extends Message<T> {
//    public static <T> UpdateMessage getUpdateMessage(T data) {
//        UpdateMessage<T> updateMessage = new UpdateMessage<>();
//        for (Field field : data.getClass().getDeclaredFields()) {
//            if (field.isAnnotationPresent(Id.class)) {
//                DynaBean bean = DynaBean.create(data);
//                updateMessage.setId(Convert.convert(field.getType(), bean.get(field.getName())).toString());
//            }
//        }
//        updateMessage.setClazz(data.getClass().getName());
//        return updateMessage;
//    }
//
//    private String id;
    private String clazz;
    private T data;
}
