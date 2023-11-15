package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resuser")
public class Resuser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userid;
    private String username;
    private String pwd;
    private String email;

//    为了与也买你传过来的yzm对应，在这里增加一个String yzm
    @TableField(exist = false)
    private String yzm;//同时又是一个vo类
}
