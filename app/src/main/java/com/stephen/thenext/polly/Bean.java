package com.stephen.thenext.polly;

/**
 * Created by Stephen on 2015/9/23.
 */
public class Bean {
    private int beanId;
    private boolean isSelected;
    private String name;

    public Bean(int beanId, boolean isSelected, String name) {
        this.beanId = beanId;
        this.isSelected = isSelected;
        this.name = name;
    }

    public Bean() {
    }

    public int getBeanId() {
        return beanId;
    }

    public void setBeanId(int beanId) {
        this.beanId = beanId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
