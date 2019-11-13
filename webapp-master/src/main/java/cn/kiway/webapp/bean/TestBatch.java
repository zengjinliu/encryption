package cn.kiway.webapp.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author 刘玉祥
 */
@Data
@SuppressWarnings("serial")
public class TestBatch implements java.io.Serializable {
    private Long seqId;

    private String name;

    private int age;

    private LocalDateTime createDate;


}