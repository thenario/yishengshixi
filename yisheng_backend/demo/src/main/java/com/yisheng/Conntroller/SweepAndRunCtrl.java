package com.yisheng.Conntroller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.yisheng.DTO.SweepAndRunDTO;
import com.yisheng.Service.SweepAndRunService;
import com.yisheng.VO.SweepAndRunVO;
import com.yisheng.Config.Result;;

@RestController
@RequestMapping("/api/sweep")
public class SweepAndRunCtrl {
    private SweepAndRunService sweepAndRunService;

    public SweepAndRunCtrl(SweepAndRunService sweepAndRunService){
        this.sweepAndRunService = sweepAndRunService;
    }

    @PostMapping("/link")
    public SseEmitter createSseLink(@RequestBody SweepAndRunDTO.SseLinkDTO linkDTO) {
        return sweepAndRunService.createSseLink(linkDTO);
    }

    @GetMapping("/file/{fileId}")
    public String downloadFile(@PathVariable("fileId") String fileId) {
        return new String();
    }

    @PostMapping("/{sweepId}/cancel")
    public Result<SweepAndRunVO.CancelSweepVO> cancelSweep(@PathVariable("sweepId") String sweepId) {
        return Result.success(sweepId, 200, new SweepAndRunVO.CancelSweepVO());
    }

}
