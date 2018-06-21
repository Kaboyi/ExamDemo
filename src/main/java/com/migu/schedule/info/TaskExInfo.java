package com.migu.schedule.info;

public class TaskExInfo extends TaskInfo implements Comparable<TaskExInfo> {
    int consumption;

    public TaskExInfo() {

    }

    public TaskExInfo(int taskId, int nodeId, int consumption) {
        this.setTaskId(taskId);
        this.setNodeId(nodeId);
        this.consumption = consumption;
    }

    public int getConsumption() {
        return this.consumption;
    }

    @Override
    public int compareTo(TaskExInfo o) {
        return getTaskId() - o.getTaskId();
    }
}
