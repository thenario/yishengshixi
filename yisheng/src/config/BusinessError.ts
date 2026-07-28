class Businesserror {
  msg: string = "";
  code: Number = 200;
  error: string = "";
  constructor(msg: string, code: Number, error: string) {
    this.code = code;
    this.error = error;
    this.msg = msg;
  }
}
export default Businesserror;
