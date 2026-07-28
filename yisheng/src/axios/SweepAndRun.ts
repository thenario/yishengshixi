import type { getFileI } from "@/type/SweepAndRun";
import request from "./request";

const getFile = async (file_id: number) => {
  const res = await request.get<getFileI>("/sweep/files/${file_id}");
  return res.data.content;
};

export { getFile };
