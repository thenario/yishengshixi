package com.yisheng.Imple;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.yisheng.Config.Result;
import com.yisheng.Config.SsePool;
import com.yisheng.DTO.SweepAndRunDTO;
import com.yisheng.Service.SweepAndRunService;
import com.yisheng.VO.SweepAndRunVO;

@Service
public class SweepAndRunImp implements SweepAndRunService {
    private final SsePool ssePool;

    public SweepAndRunImp(SsePool ssePool){
        this.ssePool = ssePool;
    }

    @Override
    public SseEmitter createSseLink(SweepAndRunDTO.SseLinkDTO sseLinkDTO) {
        SseEmitter emitter = new SseEmitter(0L);
        String emitterId = sseLinkDTO.getUserId();
        ssePool.addNewSseLink(emitterId, emitter);
        SseEmitter.SseEventBuilder event = SseEmitter.event()
                .id(emitterId)
                .name("sse link")
                .reconnectTime(3000)
                .data(new Result<SweepAndRunVO.SseLinkVO>("连接成功", 200, new SweepAndRunVO.SseLinkVO()));
        try {
            emitter.send(event);
        } catch (IOException e) {
            ssePool.removeSseLink(emitterId);
        }
        return emitter;
    };

    @Override
    public SweepAndRunVO.CancelSweepVO cancelSweep(SweepAndRunDTO.CancelSweepDTO cancelSweepDTO) {
        return new SweepAndRunVO.CancelSweepVO();
    };
}
