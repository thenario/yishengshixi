package com.yisheng.Config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SsePool {

    private final Map<String, SseEmitter> ssePool = new ConcurrentHashMap<>();

    public void addNewSseLink(String key, SseEmitter value) {
        if (ssePool.containsKey(key)) {
            removeSseLink(key);
        }

        value.onCompletion(() -> ssePool.remove(key));
        value.onTimeout(() -> ssePool.remove(key));
        value.onError((e) -> ssePool.remove(key));

        ssePool.put(key, value);
    }

    public void removeSseLink(String key) {
        SseEmitter emitter = ssePool.remove(key);
        if (emitter != null) {
            try {
                emitter.complete();
            } catch (Exception ignored) {
            }
        }
    }

    public SseEmitter getSseLink(String key) {
        return ssePool.get(key);
    }

    public void broadcast(String message) {
        for (Map.Entry<String, SseEmitter> pair : ssePool.entrySet()) {
            try {
                pair.getValue().send(SseEmitter.event()
                        .name(pair.getKey())
                        .id(pair.getKey())
                        .data(message));
            } catch (IOException e) {
                removeSseLink(pair.getKey());
            }
        }
    }
}