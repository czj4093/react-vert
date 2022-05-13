package com.jsm;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * mvn compile exec:java
 *
 * @Author: chenzejin
 * @Date: 2022-05-13
 */
public class BackendVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        // 一个 Vert.x WebRoute被定义为匹配/api/message路径上的 HTTP 请求
        Route messageRoute = router.get("/api/message");
        messageRoute.handler(rc -> {
            // 处理程序使用Route问候消息回复请求
            rc.response().end("Hello React from Vert.x!");
        });
        // StaticHandler是处理静态资源请求所必需的
        router.get().handler(StaticHandler.create());

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080);
    }

    public static void main(String[] args) {
        // 创建Vertx上下文
        Vertx vertx = Vertx.vertx();
        // 部署BackendVerticle
        vertx.deployVerticle(new BackendVerticle());
    }

}
