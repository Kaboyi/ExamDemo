package com.migu.schedule;

import com.migu.schedule.info.TaskExInfo;
import com.migu.schedule.info.TaskInfo;

import java.util.*;

class ScheduleStore {

    public Comparator<? super TaskInfo> comparator = (Comparator<TaskInfo>) (o1, o2) -> o1.getTaskId() - o2.getTaskId();
    private List<Integer> nodeStore = new ArrayList<>();
    private Map<Integer, TaskExInfo> taskStore = new HashMap<>();
    private List<TaskExInfo> store = new ArrayList<>();
    private Map<Integer, List<TaskExInfo>> runningStore = new HashMap<>();

    private int threshold = 0;
    private Map<Integer, List<TaskExInfo>> sames = new HashMap<>();
    private Comparator<? super TaskExInfo> comparatorNodeId = (Comparator<TaskExInfo>) (o1, o2) -> o1.getNodeId() - o2.getNodeId();

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

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
        return runningStore.computeIfAbsent(nodeId, k -> new ArrayList<>());
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
        int sumConsumption = sumConsumption(runningStore.get(nodeId));
        for (Integer key : runningStore.keySet()) {
            if (key.equals(nodeId)) {
                continue;
            }
            int sum = 0;
            List<TaskExInfo> taskExInfos = runningStore.get(key);
            if (taskExInfos != null) {
                sum = sumConsumption(taskExInfos);
            }
            if (Math.abs(sum - sumConsumption) > threshold) {
                return false;
            }
        }

        return true;
    }

    public void putSames(TaskExInfo task) {
        int consumption = task.getConsumption();
        List<TaskExInfo> taskExInfos = sames.computeIfAbsent(consumption, k -> new ArrayList<>());
        taskExInfos.add(task);
    }

    public void sortSames() {
        List<TaskExInfo> tasks = new ArrayList<>();
        for (Integer key : sames.keySet()) {
            List<TaskExInfo> taskExInfos = sames.get(key);
            if (taskExInfos.size() > 1) {
                for (List<TaskExInfo> taskInfos : runningStore.values()) {
                    for (TaskExInfo task : taskInfos) {
                        if (taskExInfos.contains(task)) {
                            tasks.add(task);
                        }
                    }
                }
            }

            Collections.sort(taskExInfos);
            tasks.sort(comparatorNodeId);

            int i = 0;
            for (TaskExInfo task : tasks) {
                task.setNodeId(Objects.requireNonNull(tasks.get(i++)).getNodeId());
            }
        }
        sames.clear();
    }

    public void addAllRunning(List<TaskInfo> tasks) {
        for (List<TaskExInfo> list : runningStore.values()) {
            tasks.addAll(list);
        }
    }
}
