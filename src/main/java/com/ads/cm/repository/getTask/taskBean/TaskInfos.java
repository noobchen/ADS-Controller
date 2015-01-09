package com.ads.cm.repository.getTask.taskBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-27
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class TaskInfos {

    private Integer id;
    private Integer type;
    private Integer weight;
    private Integer countType;
    private Integer times;
    private String prefer_time;
    private String prefer_provider;
    private String prefer_province;
    private String prefer_city;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCountType() {
        return countType;
    }

    public void setCountType(Integer countType) {
        this.countType = countType;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getPrefer_time() {
        return prefer_time;
    }

    public void setPrefer_time(String prefer_time) {
        this.prefer_time = prefer_time;
    }

    public String getPrefer_provider() {
        return prefer_provider;
    }

    public void setPrefer_provider(String prefer_provider) {
        this.prefer_provider = prefer_provider;
    }

    public String getPrefer_province() {
        return prefer_province;
    }

    public void setPrefer_province(String prefer_province) {
        this.prefer_province = prefer_province;
    }

    public String getPrefer_city() {
        return prefer_city;
    }

    public void setPrefer_city(String prefer_city) {
        this.prefer_city = prefer_city;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id:");
        stringBuilder.append(id);
        stringBuilder.append("type:");
        stringBuilder.append(type);
        stringBuilder.append("weight:");
        stringBuilder.append(weight);
        stringBuilder.append("prefer_time:");
        stringBuilder.append(prefer_time);
        stringBuilder.append("prefer_provider:");
        stringBuilder.append(prefer_provider);
        stringBuilder.append("prefer_province:");
        stringBuilder.append(prefer_province);
        stringBuilder.append("prefer_city:");
        stringBuilder.append(prefer_city);
        stringBuilder.append("countType:");
        stringBuilder.append(countType);

        return stringBuilder.toString();
    }


    public List<String> getPreferList(String prefer) {
        List<String> list = new ArrayList<String>();
        String[] prefers = prefer.split(",");

        for (String temp : prefers) {

            list.add(temp);
        }

        return list;

    }
}
