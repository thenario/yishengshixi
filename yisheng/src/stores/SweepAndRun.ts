import { ref, computed } from "vue";
import { defineStore } from "pinia";
import type { runI, sweepI } from "@/type/SweepAndRun";

export const SweepAndRunStore = defineStore("SweepAndRunStore", () => {
  const sweep_list = ref<sweepI[]>([]);
  const current_run = ref<runI>();
  const current_sweep = ref<sweepI>();
  const current_run_list = ref<runI[]>();
});
