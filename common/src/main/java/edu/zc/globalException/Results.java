package edu.zc.globalException;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: keeplooking
 * @since: 2021/12/31 - 0:01
 */
@Data
public class Results {
    private Integer code;
    private String msg;
    private Map<String, Object> data = new HashMap<>();

    public static Results success(){
        Results results = new Results();
        results.setCode(ResultCode.SUCCESS);
        results.setMsg("操作成功");
        return results;
    }


    public static Results fail(){
        Results results = new Results();
        results.setCode(ResultCode.ERROR);
        results.setMsg("操作失败");
        return results;
    }

    public Results msg(String msg){
        this.setMsg(msg);
        return this;
    }

    public Results code(Integer code){
        this.setCode(code);
        return this;
    }

    public Results data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Results data(Map<String, Object> data){
        this.setData(data);
        return this;
    }

}
