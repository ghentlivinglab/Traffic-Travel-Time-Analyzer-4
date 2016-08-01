/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.service;

import be.ugent.verkeer4.verkeerdomain.IPOIService;
import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.POIService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import java.util.Date;

public abstract class BaseService {

    private volatile boolean running = false;

    private int everyXMillis;
    private String name;

    public BaseService(int everyXMillis, String name) {
        this.everyXMillis = everyXMillis;
        this.name = name;
    }

    public void start() {
        running = true;
        Thread t = new Thread(() -> {
            try {
                run();
            } catch (ClassNotFoundException ex) {
                LogService.getInstance().insert(LogTypeEnum.Error, BaseService.class.getName() + " Poll", ex.getMessage());
            }
        });
        t.setName(this.name);
        t.setDaemon(true);
        t.start();
    }

    public void stop() {
        running = false;
    }

    private void run() throws ClassNotFoundException {

        long curTime = new Date().getTime() - everyXMillis;

        while (running) {

            if (new Date().getTime() - curTime > everyXMillis) {
                curTime = new Date().getTime();

                try {
                    action();
                } catch (Exception ex) {
                    LogService.getInstance().insert(LogTypeEnum.Error, BaseService.class.getName() + " Poll", ex.getMessage());
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    LogService.getInstance().insert(LogTypeEnum.Error, BaseService.class.getName() + " Poll", ex.getMessage());
                }
            }

        }
    }

    protected abstract void action();
}
