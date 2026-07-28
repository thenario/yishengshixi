interface getFileI{
    content:string;
}

interface runI{

}

interface sweepI{
    run_list:runI[] | null;
}

interface evidence{

}

interface sweep_envI{

}

export type {
    getFileI,
    sweepI,
    evidence,
    sweep_envI,
    runI
}