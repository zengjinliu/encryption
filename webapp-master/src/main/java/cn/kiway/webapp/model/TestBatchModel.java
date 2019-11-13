package cn.kiway.webapp.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author minte
 * @date 2019/9/5 15:11
 */

@Data
public class TestBatchModel {

    private Long seqId;

    private String name;

    private int age;

    private LocalDateTime createDate;

}
