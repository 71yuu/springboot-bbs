package com.bbs.domain;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "integral_rule")
public class IntegralRule {
    /**
     * 积分规则ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 规则名称
     */
    @Column(name = "rule_name")
    private String ruleName;

    /**
     * 积分
     */
    @Column(name = "integral")
    private Integer integral;

    /**
     * 每日积分增加限制条数
     */
    @Column(name = "daily_limit")
    private Integer dailyLimit;

    /**
     * 规则查询标识
     */
    @Column(name = "rule_flag")
    private String ruleFlag;

    /**
     * 规则说明
     */
    @Column(name = "`explain`")
    private String explain;
}