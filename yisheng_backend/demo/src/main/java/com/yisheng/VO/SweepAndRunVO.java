package com.yisheng.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

public class SweepAndRunVO {
    @Data
    @NoArgsConstructor
    public static class SseLinkVO {
        String SseLinkId;
        
    }

    @Data
    public static class CancelSweepVO {
        
    }

    @Data
    public static class SseBroadcastVO {
        String id;
        String name;
        // 待确定数据结构
    }
}
