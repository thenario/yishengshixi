

<template>
    <div class="flex justify-between items-center p-6">
        <!-- 左边的箭头和页面说明 -->
        <div class=" flex gap-2 items-center ">
            <ElButton class="hover:bg-blue-500">
                <el-icon size="20" ><Back class="text-blue-400"/></el-icon>
            </ElButton>

            <div class="text-lg text-blue-500">GPU性能测试</div>
        </div>
        <!-- 右边的已有测试1/4这种 -->
         <div class="text-green-500">
            运行中测试 2/5
         </div>
    </div>
</template>
<script setup lang="ts">
import { Back } from '@element-plus/icons-vue';
const el = [1,2,3]

</script>