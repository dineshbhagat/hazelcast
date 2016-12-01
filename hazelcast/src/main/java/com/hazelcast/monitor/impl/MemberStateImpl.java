/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.monitor.impl;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.hazelcast.internal.management.JsonSerializable;
import com.hazelcast.internal.management.dto.ClientEndPointDTO;
import com.hazelcast.internal.management.dto.MXBeansDTO;
import com.hazelcast.monitor.HotRestartState;
import com.hazelcast.monitor.LocalCacheStats;
import com.hazelcast.monitor.LocalExecutorStats;
import com.hazelcast.monitor.LocalMapStats;
import com.hazelcast.monitor.LocalMemoryStats;
import com.hazelcast.monitor.LocalMultiMapStats;
import com.hazelcast.monitor.LocalOperationStats;
import com.hazelcast.monitor.LocalQueueStats;
import com.hazelcast.monitor.LocalReplicatedMapStats;
import com.hazelcast.monitor.LocalTopicStats;
import com.hazelcast.monitor.LocalWanStats;
import com.hazelcast.monitor.WanSyncState;
import com.hazelcast.monitor.MemberPartitionState;
import com.hazelcast.monitor.MemberState;
import com.hazelcast.monitor.NodeState;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import static com.hazelcast.util.JsonUtil.getArray;
import static com.hazelcast.util.JsonUtil.getObject;
import static com.hazelcast.util.JsonUtil.getString;

@SuppressWarnings("checkstyle:classdataabstractioncoupling")
public class MemberStateImpl implements MemberState {

    private String address;
    private Map<String, Long> runtimeProps = new HashMap<String, Long>();
    private Map<String, LocalMapStats> mapStats = new HashMap<String, LocalMapStats>();
    private Map<String, LocalMultiMapStats> multiMapStats = new HashMap<String, LocalMultiMapStats>();
    private Map<String, LocalQueueStats> queueStats = new HashMap<String, LocalQueueStats>();
    private Map<String, LocalTopicStats> topicStats = new HashMap<String, LocalTopicStats>();
    private Map<String, LocalExecutorStats> executorStats = new HashMap<String, LocalExecutorStats>();
    private Map<String, LocalReplicatedMapStats> replicatedMapStats = new HashMap<String, LocalReplicatedMapStats>();
    private Map<String, LocalCacheStats> cacheStats = new HashMap<String, LocalCacheStats>();
    private Map<String, LocalWanStats> wanStats = new HashMap<String, LocalWanStats>();
    private Collection<ClientEndPointDTO> clients = new HashSet<ClientEndPointDTO>();
    private MXBeansDTO beans = new MXBeansDTO();
    private LocalMemoryStats memoryStats = new LocalMemoryStatsImpl();
    private MemberPartitionState memberPartitionState = new MemberPartitionStateImpl();
    private LocalOperationStats operationStats = new LocalOperationStatsImpl();
    private NodeState nodeState = new NodeStateImpl();
    private HotRestartState hotRestartState = new HotRestartStateImpl();
    private WanSyncState wanSyncState = new WanSyncStateImpl();

    public MemberStateImpl() {
    }

    @Override
    public Map<String, Long> getRuntimeProps() {
        return runtimeProps;
    }

    public void setRuntimeProps(Map<String, Long> runtimeProps) {
        this.runtimeProps = runtimeProps;
    }

    @Override
    public LocalMapStats getLocalMapStats(String mapName) {
        return mapStats.get(mapName);
    }

    @Override
    public LocalMultiMapStats getLocalMultiMapStats(String mapName) {
        return multiMapStats.get(mapName);
    }

    @Override
    public LocalQueueStats getLocalQueueStats(String queueName) {
        return queueStats.get(queueName);
    }

    @Override
    public LocalTopicStats getLocalTopicStats(String topicName) {
        return topicStats.get(topicName);
    }

    @Override
    public LocalReplicatedMapStats getLocalReplicatedMapStats(String replicatedMapName) {
        return replicatedMapStats.get(replicatedMapName);
    }

    @Override
    public LocalExecutorStats getLocalExecutorStats(String executorName) {
        return executorStats.get(executorName);
    }

    @Override
    public LocalCacheStats getLocalCacheStats(String cacheName) {
        return cacheStats.get(cacheName);
    }

    @Override
    public LocalWanStats getLocalWanStats(String schemeName) {
        return wanStats.get(schemeName);
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void putLocalMapStats(String name, LocalMapStats localMapStats) {
        mapStats.put(name, localMapStats);
    }

    public void putLocalMultiMapStats(String name, LocalMultiMapStats localMultiMapStats) {
        multiMapStats.put(name, localMultiMapStats);
    }

    public void putLocalQueueStats(String name, LocalQueueStats localQueueStats) {
        queueStats.put(name, localQueueStats);
    }

    public void putLocalReplicatedMapStats(String name, LocalReplicatedMapStats localReplicatedMapStats) {
        replicatedMapStats.put(name, localReplicatedMapStats);
    }

    public void putLocalTopicStats(String name, LocalTopicStats localTopicStats) {
        topicStats.put(name, localTopicStats);
    }

    public void putLocalExecutorStats(String name, LocalExecutorStats localExecutorStats) {
        executorStats.put(name, localExecutorStats);
    }

    public void putLocalCacheStats(String name, LocalCacheStats localCacheStats) {
        cacheStats.put(name, localCacheStats);
    }

    public void putLocalWanStats(String name, LocalWanStats localWanStats) {
        wanStats.put(name, localWanStats);
    }

    public Collection<ClientEndPointDTO> getClients() {
        return clients;
    }

    @Override
    public MXBeansDTO getMXBeans() {
        return beans;
    }

    public void setBeans(MXBeansDTO beans) {
        this.beans = beans;
    }

    public void setClients(Collection<ClientEndPointDTO> clients) {
        this.clients = clients;
    }

    @Override
    public LocalMemoryStats getLocalMemoryStats() {
        return memoryStats;
    }

    public void setLocalMemoryStats(LocalMemoryStats memoryStats) {
        this.memoryStats = memoryStats;
    }

    @Override
    public LocalOperationStats getOperationStats() {
        return operationStats;
    }

    public void setOperationStats(LocalOperationStats operationStats) {
        this.operationStats = operationStats;
    }

    @Override
    public MemberPartitionState getMemberPartitionState() {
        return memberPartitionState;
    }

    @Override
    public NodeState getNodeState() {
        return nodeState;
    }

    public void setNodeState(NodeState nodeState) {
        this.nodeState = nodeState;
    }

    @Override
    public HotRestartState getHotRestartState() {
        return hotRestartState;
    }

    public void setHotRestartState(HotRestartState hotRestartState) {
        this.hotRestartState = hotRestartState;
    }

    @Override
    public WanSyncState getWanSyncState() {
        return wanSyncState;
    }

    public void setWanSyncState(WanSyncState wanSyncState) {
        this.wanSyncState = wanSyncState;
    }

    @Override
    public JsonObject toJson() {
        final JsonObject root = new JsonObject();
        root.add("address", address);
        serializeMap(root, "mapStats", mapStats);
        serializeMap(root, "multiMapStats", multiMapStats);
        serializeMap(root, "replicatedMapStats", replicatedMapStats);
        serializeMap(root, "queueStats", queueStats);
        serializeMap(root, "topicStats", topicStats);
        serializeMap(root, "executorStats", executorStats);
        serializeMap(root, "cacheStats", cacheStats);
        serializeMap(root, "wanStats", wanStats);

        final JsonObject runtimePropsObject = new JsonObject();
        for (Map.Entry<String, Long> entry : runtimeProps.entrySet()) {
            runtimePropsObject.add(entry.getKey(), entry.getValue());
        }
        root.add("runtimeProps", runtimePropsObject);

        final JsonArray clientsArray = new JsonArray();
        for (ClientEndPointDTO client : clients) {
            clientsArray.add(client.toJson());
        }
        root.add("clients", clientsArray);
        root.add("beans", beans.toJson());
        root.add("memoryStats", memoryStats.toJson());
        root.add("operationStats", operationStats.toJson());
        root.add("memberPartitionState", memberPartitionState.toJson());
        root.add("nodeState", nodeState.toJson());
        root.add("hotRestartState", hotRestartState.toJson());
        root.add("wanSyncState", wanSyncState.toJson());
        return root;
    }

    private static void serializeMap(JsonObject root, String key, Map<String, ? extends JsonSerializable> map) {
        final JsonObject jsonObject = new JsonObject();
        for (Entry<String, ? extends JsonSerializable> e : map.entrySet()) {
            jsonObject.add(e.getKey(), e.getValue().toJson());
        }
        root.add(key, jsonObject);
    }

    //CHECKSTYLE:OFF
    @Override
    public void fromJson(JsonObject json) {
        address = getString(json, "address");
        for (JsonObject.Member next : getObject(json, "mapStats")) {
            LocalMapStatsImpl stats = new LocalMapStatsImpl();
            stats.fromJson(next.getValue().asObject());
            mapStats.put(next.getName(), stats);
        }
        for (JsonObject.Member next : getObject(json, "multiMapStats")) {
            LocalMultiMapStatsImpl stats = new LocalMultiMapStatsImpl();
            stats.fromJson(next.getValue().asObject());
            multiMapStats.put(next.getName(), stats);
        }
        for (JsonObject.Member next : getObject(json, "replicatedMapStats", new JsonObject())) {
            LocalReplicatedMapStats stats = new LocalReplicatedMapStatsImpl();
            stats.fromJson(next.getValue().asObject());
            replicatedMapStats.put(next.getName(), stats);
        }
        for (JsonObject.Member next : getObject(json, "queueStats")) {
            LocalQueueStatsImpl stats = new LocalQueueStatsImpl();
            stats.fromJson(next.getValue().asObject());
            queueStats.put(next.getName(), stats);
        }
        for (JsonObject.Member next : getObject(json, "topicStats")) {
            LocalTopicStatsImpl stats = new LocalTopicStatsImpl();
            stats.fromJson(next.getValue().asObject());
            topicStats.put(next.getName(), stats);
        }
        for (JsonObject.Member next : getObject(json, "executorStats")) {
            LocalExecutorStatsImpl stats = new LocalExecutorStatsImpl();
            stats.fromJson(next.getValue().asObject());
            executorStats.put(next.getName(), stats);
        }
        for (JsonObject.Member next : getObject(json, "cacheStats", new JsonObject())) {
            LocalCacheStats stats = new LocalCacheStatsImpl();
            stats.fromJson(next.getValue().asObject());
            cacheStats.put(next.getName(), stats);
        }
        for (JsonObject.Member next : getObject(json, "wanStats", new JsonObject())) {
            LocalWanStats stats = new LocalWanStatsImpl();
            stats.fromJson(next.getValue().asObject());
            wanStats.put(next.getName(), stats);
        }
        for (JsonObject.Member next : getObject(json, "runtimeProps")) {
            runtimeProps.put(next.getName(), next.getValue().asLong());
        }
        final JsonArray jsonClients = getArray(json, "clients");
        for (JsonValue jsonClient : jsonClients) {
            final ClientEndPointDTO client = new ClientEndPointDTO();
            client.fromJson(jsonClient.asObject());
            clients.add(client);
        }
        beans = new MXBeansDTO();
        beans.fromJson(getObject(json, "beans"));
        JsonObject jsonMemoryStats = getObject(json, "memoryStats", null);
        if (jsonMemoryStats != null) {
            memoryStats.fromJson(jsonMemoryStats);
        }
        JsonObject jsonOperationStats = getObject(json, "operationStats", null);
        if (jsonOperationStats != null) {
            operationStats.fromJson(jsonOperationStats);
        }
        JsonObject jsonMemberPartitionState = getObject(json, "memberPartitionState", null);
        if (jsonMemberPartitionState != null) {
            memberPartitionState = new MemberPartitionStateImpl();
            memberPartitionState.fromJson(jsonMemberPartitionState);
        }
        JsonObject jsonNodeState = getObject(json, "nodeState", null);
        if (jsonNodeState != null) {
            nodeState = new NodeStateImpl();
            nodeState.fromJson(jsonNodeState);
        }
        JsonObject jsonHotRestartState = getObject(json, "hotRestartState", null);
        if (jsonHotRestartState != null) {
            hotRestartState = new HotRestartStateImpl();
            hotRestartState.fromJson(jsonHotRestartState);
        }
        JsonObject jsonWanSyncState = getObject(json, "wanSyncState", null);
        if (jsonWanSyncState != null) {
            wanSyncState = new WanSyncStateImpl();
            wanSyncState.fromJson(jsonWanSyncState);
        }
    }

    //CHECKSTYLE:ON
    @Override
    public String toString() {
        return "MemberStateImpl{"
                + "address=" + address
                + ", runtimeProps=" + runtimeProps
                + ", mapStats=" + mapStats
                + ", multiMapStats=" + multiMapStats
                + ", replicatedMapStats=" + replicatedMapStats
                + ", queueStats=" + queueStats
                + ", topicStats=" + topicStats
                + ", executorStats=" + executorStats
                + ", cacheStats=" + cacheStats
                + ", memoryStats=" + memoryStats
                + ", operationStats=" + operationStats
                + ", memberPartitionState=" + memberPartitionState
                + ", nodeState=" + nodeState
                + ", hotRestartState=" + hotRestartState
                + ", wanSyncState=" + wanSyncState
                + '}';
    }
}
