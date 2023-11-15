package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resfood implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer fid;
    private String fname;
    private Double normprice;
    private Double realprice;
    private String detail;
    private String fphoto;
    @TableField( exist = false )//这个数据是redis中的而不是数据库的
    private Integer detail_count;//商品详情浏览次数
}
