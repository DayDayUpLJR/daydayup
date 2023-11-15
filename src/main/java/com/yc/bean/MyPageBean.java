package com.yc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyPageBean {
    private int pageno;
    private int pagesize;
    private String sort;
    private String sortby;
    private long total;
    private List<Resfood> dataset;
    private int totalpages;
    private int pre;
    private int next;

}
