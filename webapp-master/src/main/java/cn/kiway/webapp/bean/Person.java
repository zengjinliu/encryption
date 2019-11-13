package cn.kiway.webapp.bean;

import lombok.Data;

import java.time.LocalDateTime;
/**
 * @author minte
 */
@Data
@SuppressWarnings("serial")
public class Person implements java.io.Serializable {
    private Long seqId;

    private String userId;

    private String pw;

    private Long orgId;

    private Long depId;

    private Long roleId;

    private String roleName;

    private String assistRole;

    private LocalDateTime registerDate;

    private Short personState;

    private Short isReserved;

    private String unionid;

    private String loginType;



}