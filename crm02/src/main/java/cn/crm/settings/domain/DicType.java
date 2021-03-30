package cn.crm.settings.domain;

import org.springframework.stereotype.Component;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-01 20:19
 **/

public class DicType  {
    private String code;
    private String name;
    private String description;

    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
