package com.tistory.aircook.mybatis.config.data;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port}")
    private int port;
    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        log.debug("redis server start!");
        //redisServer = new RedisServer(isRedisRunning() ? findAvailablePort() : port);
        //redisServer = new RedisServer(port);
        redisServer = RedisServer.newRedisServer()
                .port(port)
                .setting("maxmemory 128M")
                .build();

        redisServer.start();

        log.debug("redis server active is [{}]", redisServer.isActive());

    }

    @PreDestroy
    public void stopRedis() throws IOException {
        log.debug("redis server stop!");
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    /**
     * Embedded Redis가 현재 실행중인지 확인
     */
    private boolean isRedisRunning() throws IOException {
        return isRunning(executeGrepProcessCommand(port));
    }

    /**
     * 현재 PC/서버에서 사용가능한 포트 조회
     */
    public int findAvailablePort() throws IOException {

        for (int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcessCommand(port);
            if (!isRunning(process)) {
                return port;
            }
        }

        throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
    }

    /**
     * 해당 port를 사용중인 프로세스 확인하는 sh 실행
     */
    private Process executeGrepProcessCommand(int port) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    /**
     * 해당 Process가 현재 실행중인지 확인
     */
    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }

        } catch (Exception e) {
        }

        return StringUtils.hasText(pidInfo.toString());
    }
}
