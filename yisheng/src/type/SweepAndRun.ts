enum Sweep_Status {
  Pending = "pending",
  Running = "running",
  Done = "done",
  Failed = "failed",
  Cancelled = "cancelled",
}

enum Load_Type {
  RandomText = "随机文本 ",
  TextCache = "文本缓存",
  RandomPhotoText = "随机图文",
  PhotoTextCache = "图文缓存",
}

enum Run_Status{
    Running = "runnig",
    Passing = "passing",
    Failing = "failing",
}
// 接口类型我习惯加上I，但是好像不是很重要了
interface runI {
  concurrency: number;
  attempt_no: number;
  search_phase: string;

  run_status: Run_Status;//加上run,改成enum

  started_at: Date;
  finished_at: Date;
  created_at: Date;

  evidence_file :evidence_fileI,
}

interface sweepI {
  sweep_name: string;//感觉加上sweep舒服一点
  tags: string[];
  note: string;

  snapshot: snapshotI;
  run_list: runI[];
  endpoint: endpointI;
  run_env: run_envI;

  search_lower_bound: number;
  search_upper_bound: number;
  search_result: number;

  sweep_status: Sweep_Status;//同上
  load_type: Load_Type;
  cancellation_reason: String;
  voided: boolean;//去掉is
  void_reason: String;
  created_at: Date;
  finished_at: Date;
  started_at: Date;
  updated_at: Date;
  voided_at: Date;
}

interface evidence_fileI {
    file_id : string,
    file_name : string,
    file_sha256 : string,
    file_content : string,
    file_type : string,
}

interface snapshotI {
  inference_config: string;
  inference_config_sha256: string;
  tokenizer: string;

  request_number: number;
  request_rate: number;
  seed: number;

  temperature: number;
  top_p: number;
  ignore_eos: boolean;
  extra_body: string;

  input_length: number;
  output_length: number;
  length_float_ratio: number;
  fixed_prefix: string;

  cache_method: string;
  cache_hit_ratio: number;
  prefix_count: number;
  cache_prefix_length: number;

  images_per_request: number;
  image_size_bucket: string;
  multimodal_chat: boolean;
  unique_image_count: number;
  repeat_ratio: number;
  media_manifest_ref: string;

  threshold_version: number;
  ttft_limit_ms: number;
  tpot_limit_ms: number;
  success_rate_min: number;

  tool_version: string;
  code_version: string;

  created_at: Date;
}

interface endpointI {
  endpoint_name: string;
  base_url: string;
  backend: string;
  served_model: string;
  credential_ref: string;

  verification_status: String;
  verified_at: Date;
  verification_error: string;
  last_heartbeat_at: Date;

  enabled: boolean;
}

interface run_envI {
  run_env_id: number;
  tool_version: string;
  code_version: string;
  working_directory: string;
}

interface thresholdI {
    topt_limit_ms:number,
    tfft_limit_ms:number,
    min_sucess_rate:number
}

// 以下是axios里封装的函数的返回结果定义

interface getFileI {
  content: string;
}
export type {
  sweepI,
  evidence_fileI,
  runI,
  endpointI,
  snapshotI,
  run_envI,
  thresholdI,
  // 以下是axios里封装的函数的返回结果定义
  getFileI,
};
