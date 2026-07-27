package com.yisheng.Service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.yisheng.DTO.SweepAndRunDTO;
import com.yisheng.VO.SweepAndRunVO;

public interface SweepAndRunService {
    SseEmitter createSseLink(SweepAndRunDTO.SseLinkDTO sseLinkDTO);

    SweepAndRunVO.CancelSweepVO cancelSweep(SweepAndRunDTO.CancelSweepDTO cancelSweepDTO);
}
