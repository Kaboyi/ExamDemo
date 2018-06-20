package com.migu.schedule;

import com.migu.schedule.info.TaskInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ScheduleStore {

    private List<Integer> nodeStore= new ArrayList<>();
    private Map<Integer, Integer> taskStore = new HashMap<>();
    private List<TaskInfo> store = new ArrayList<>();

    void clear() {
        nodeStore.clear();
        taskStore.clear();
        store.clear();
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
        taskStore.put(taskId, consumption);
    }

    public boolean containsTask(int taskId) {
        return taskStore.containsKey(taskId);
    }

    public void removeTask(int taskId) {
        taskStore.remove(taskId);
    }

    public boolean existsTask() {
        return !taskStore.isEmpty();
    }

    public Map<Integer,Integer> getTasks() {
        return taskStore;
    }
}
