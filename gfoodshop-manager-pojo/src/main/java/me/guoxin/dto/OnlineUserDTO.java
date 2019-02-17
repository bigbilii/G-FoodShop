package me.guoxin.dto;

import me.guoxin.pojo.GfsUser;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class OnlineUserDTO extends GfsUser implements Serializable {

    private String sessionId;
    private String host;
    private Date startTime;
    private Date lastAccess;
    private Long timeout;
    private Boolean sessionStatus = Boolean.TRUE;

    public OnlineUserDTO() {
    }

    public OnlineUserDTO(GfsUser gfsUser) {
        super(gfsUser);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Boolean getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(Boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
