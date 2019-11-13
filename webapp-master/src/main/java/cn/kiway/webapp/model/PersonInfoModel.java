package cn.kiway.webapp.model;

import cn.kiway.webapp.bean.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 *
 * @author 刘玉祥
 * @date 2019/9/4 14:32
 */
@Data
public class PersonInfoModel {

    private Long seqId;

    private String userId;

    private Long personId;

    private String userName;

    private String phone;

    private String weixinId;

    private String position;

    private String avatarUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private LocalDateTime createDate;

    private String roleName;
}
