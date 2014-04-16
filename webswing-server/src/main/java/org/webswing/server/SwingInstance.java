package org.webswing.server;

import java.io.Serializable;
import java.util.Date;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.webswing.model.admin.s2c.JsonSwingJvmStats;
import org.webswing.model.c2s.JsonConnectionHandshake;
import org.webswing.model.server.SwingApplicationDescriptor;
import org.webswing.server.SwingJvmConnection.WebSessionListener;
import org.webswing.server.util.ServerUtil;

public class SwingInstance implements WebSessionListener {

    private String user;
    private AtmosphereResource resource;
    private AtmosphereResource mirroredResource;
    private SwingApplicationDescriptor application;
    private SwingJvmConnection connection;
    private Date disconnectedSince;
    private final Date startedAt = new Date();
    private Date endedAt = new Date();

    public SwingInstance(JsonConnectionHandshake h, SwingApplicationDescriptor app, AtmosphereResource resource) {
        this.application = app;
        this.user = ServerUtil.getUserName(resource);
        registerPrimaryWebSession(resource);
        this.connection = new SwingJvmConnection(h, app, this);
    }

    public boolean registerPrimaryWebSession(AtmosphereResource resource) {
        if (this.resource == null) {
            this.resource = resource;
            this.disconnectedSince = null;
            resource.addEventListener(new AtmosphereResourceEventListenerAdapter() {

                public void onDisconnect(AtmosphereResourceEvent event) {
                    SwingInstance.this.resource = null;
                    SwingInstance.this.disconnectedSince = new Date();
                    SwingInstanceManager.getInstance().notifySwingChangeChange();
                }
            });
            return true;
        } else {
            return false;
        }
    }

    public boolean registerMirroredWebSession(AtmosphereResource resource) {
        if (this.resource == null) {
            this.mirroredResource = resource;
            resource.addEventListener(new AtmosphereResourceEventListenerAdapter() {

                public void onDisconnect(AtmosphereResourceEvent event) {
                    SwingInstance.this.mirroredResource = null;
                }
            });
            return true;
        } else {
            return false;
        }
    }

    public void sendToWeb(Serializable o) {
        if (resource != null) {
            resource.getBroadcaster().broadcast(o, resource);
        }
        if (mirroredResource != null) {
            mirroredResource.getBroadcaster().broadcast(o, mirroredResource);
        }
    }

    public void sendToSwing(Serializable h) {
        if (connection.isRunning()) {
            connection.send(h);
        }
    }

    public void notifyClose() {
        endedAt = new Date();
        SwingInstanceManager.getInstance().notifySwingClose(this);
    }

    public String getClientId() {
        return connection.getClientId();
    }

    public String getApplicationName() {
        return application.getName();
    }

    public String getSessionId() {
        if (resource != null) {
            return resource.uuid();
        }
        return null;
    }

    public String getMirroredSessionId() {
        if (mirroredResource != null) {
            return mirroredResource.uuid();
        }
        return null;
    }

    public boolean isRunning() {
        return connection.isRunning();
    }

    public String getUser() {
        return user;
    }

    public Date getDisconnectedSince() {
        return disconnectedSince;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public Date getEndedAt() {
        return endedAt;
    }

    public JsonSwingJvmStats getStats() {
        return connection.getCurrentStatus();
    }

}
