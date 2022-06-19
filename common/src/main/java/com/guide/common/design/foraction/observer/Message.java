package com.guide.common.design.foraction.observer;

import lombok.Data;

@Data
public class Message
{
    /**
     *操作：add  update delete  export
     */
    private String oper;
    /**
     * 操作人账号:admin
     */
    private String userId;
    /**
     *记录标识
     */
    private String recordId;
    /**
     *记录中文描述
     */
    private String recordName;
}
