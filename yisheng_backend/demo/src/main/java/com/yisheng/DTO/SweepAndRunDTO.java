package com.yisheng.DTO;

import lombok.Data;

public class SweepAndRunDTO {
    @Data
    public static class SseLinkDTO {
       String userId;
    }

    @Data
    public static class CancelSweepDTO {
        Integer sweepId;
    }

    @Data
    public static class SseBroadcastDTO {
        
    }
}
