package com.migu.schedule;

import com.migu.schedule.info.TaskExInfo;
import com.migu.schedule.info.TaskInfo;

import java.util.*;

class ScheduleStore {

    private List<Integer> nodeStore = new ArrayList<>();
    private List<Integer> waitTasks = new ArrayList<>();
    private Map<Integer, TaskExInfo> taskStore = new HashMap<>();
    private List<TaskInfo> store = new ArrayList<>();
    private Map<Integer, List<TaskInfo>> runningStore = new HashMap<>();

    void clear() {
        nodeStore.clear();
        taskStore.clear();
        waitTasks.clear();
        store.clear();
        runningStore.clear();
    }

    void addNode(int nodeId) {
        nodeStore.add(nodeId);
    }

    boolean containsNode(int nodeId) {
        return nodeStore.contains(nodeId);
    }

    void removeNode(Integer nodeId) {
        nodeStore.remove(nodeId);
    }

    public void addTask(int taskId, int consumption) {
        taskStore.put(taskId, new TaskExInfo(consumption));
    }

    public boolean containsTask(int taskId) {
        return taskStore.containsKey(taskId);
    }

    public void removeTask(Integer taskId) {
        waitTasks.remove(taskId);
        TaskExInfo taskExInfo = taskStore.get(taskId);
        if (taskExInfo.getNodeId() > 0) {
            runningStore.remove(taskExInfo.getNodeId());
        }
    }

    public boolean existsTask() {
        return !taskStore.isEmpty();
    }

    public Map<Integer, TaskExInfo> getTasks() {
        return taskStore;
    }

    public void removeRunning(Integer nodeId) {
        List<TaskInfo> taskInfos = runningStore.get(nodeId);
        if (taskInfos != null) {
            for (TaskInfo task : taskInfos) {
                waitTasks.add(task.getTaskId());
            }
        }
        runningStore.remove(nodeId);
    }

}
