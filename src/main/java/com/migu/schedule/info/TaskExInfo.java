package com.migu.schedule.info;

public class TaskExInfo extends TaskInfo {
    int consumption;

    public TaskExInfo(int taskId, int nodeId, int consumption) {
        this.setTaskId(taskId);
        this.setNodeId(nodeId);
        this.consumption = consumption;
    }
    public int getConsumption() {
        return this.consumption;
    }
}
