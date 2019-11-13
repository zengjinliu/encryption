package cn.kiway.webapp.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 刘玉祥
 */
@Data
@SuppressWarnings("serial")
public class PersonInfo implements java.io.Serializable {
    private Long seqId;

    private String userId;

    private Long personId;

    private String userName;

    private String phone;

    private String weixinId;

    private String position;

    private String avatarUrl;

    private LocalDateTime createDate;

    private Person person;


}