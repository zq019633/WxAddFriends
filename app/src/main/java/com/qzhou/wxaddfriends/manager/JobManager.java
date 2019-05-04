package com.qzhou.wxaddfriends.manager;

import android.content.Context;

public class JobManager {
    private static JobManager instance;
    private volatile JobProcessor jobProcessor = null;
    public static JobManager getInstance() {
        if (instance == null) {
            synchronized (JobManager.class) {
                if (instance == null) {
                    instance = new JobManager();
                }
            }
        }
        return instance;
    }

    public synchronized JobProcessor getJobProcessor() {
        return jobProcessor;
    }
    public synchronized JobProcessor setJobProcessor(JobProcessor jobProcessor) {
        return jobProcessor;
    }

    //开启任务
    public synchronized void startWorking(Context context, JobProcessor jobProcessor) {
        this.jobProcessor = jobProcessor;
    }

}
