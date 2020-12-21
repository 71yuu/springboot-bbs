package com.bbs.domain;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "cat")
public class Cat {
    /**
     * 分类ID
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 分类名称
     */
    @Column(name = "cat_name")
    private String catName;

    /**
     * 删除标记
     */
    @Column(name = "del_flag")
    private Integer delFlag;
}
