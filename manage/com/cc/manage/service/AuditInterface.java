package com.cc.manage.service;

public interface AuditInterface {
    
    /**
     * 获取是否拥有权限
     * 
     * @param actno
     * @param key
     * @param flowno
     * @return
     */
    public Object getAuditPower(String ccno, String actno, String key, String flowno, String ipAddressAndPort);
    
}
