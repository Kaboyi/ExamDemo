package com.migu.schedule;

import com.migu.schedule.info.TaskExInfo;

import java.util.*;

class ScheduleStore {

    private List<Integer> nodeStore = new ArrayList<>();
    private Map<Integer, TaskExInfo> taskStore = new HashMap<>();
    private List<TaskExInfo> store = new ArrayList<>();
    private Map<Integer, List<TaskExInfo>> runningStore = new HashMap<>();

    void clear() {
        nodeStore.clear();
        taskStore.clear();
        store.clear();
        runningStore.clear();
    }

    void addNode(int nodeId) {
        nodeStore.add(nodeId);
        Collections.sort(nodeStore);
    }

    boolean containsNode(int nodeId) {
        return nodeStore.contains(nodeId);
    }

    void removeNode(Integer nodeId) {
        nodeStore.remove(nodeId);
    }

    public void addTask(int taskId, int consumption) {
        taskStore.put(taskId, new TaskExInfo(taskId, -1, consumption));
    }

    public boolean containsTask(int taskId) {
        return taskStore.containsKey(taskId);
    }

    public void removeTask(Integer taskId) {
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

    public List<TaskExInfo> getRunning(Integer nodeId) {
        return runningStore.get(nodeId);
    }

    public Integer findMinNode() {
        Integer nodeId = -1;
        Integer minVal = null;
        for (Integer iNode : nodeStore) {
            List<TaskExInfo> taskExInfos = runningStore.get(iNode);
            if (taskExInfos == null) {
                return iNode;
            } else {
                int sumConsumption = sumConsumption(taskExInfos);
                if (minVal == null || minVal > sumConsumption) {
                    minVal = sumConsumption;
                    nodeId = iNode;
                }
            }
        }

        return nodeId;
    }

    private int sumConsumption(List<TaskExInfo> taskExInfos) {
        int res = 0;
        for (TaskExInfo taskExInfo : taskExInfos) {
            res += taskExInfo.getConsumption();
        }
        return res;
    }

    public List<Integer> getNodes() {
        return nodeStore;
    }

    public boolean validate(Integer nodeId) {
        List<TaskExInfo> taskExInfos = runningStore.get(nodeId);
        return false;
    }
}
