/*
 * Copyright (c) 2008-2010, Hazel Ltd. All Rights Reserved.
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
 *
 */

package com.hazelcast.map.client;

import com.hazelcast.client.ClientCommandHandler;
import com.hazelcast.instance.Node;
import com.hazelcast.map.MapService;
import com.hazelcast.map.proxy.DataMapProxy;
import com.hazelcast.nio.Protocol;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.nio.serialization.SerializationConstants;

import java.nio.ByteBuffer;

public class MapGetHandler extends ClientCommandHandler {
    final MapService mapService;

    public MapGetHandler(MapService mapService) {
        super(mapService.getNodeEngine());
        this.mapService = mapService;
    }

    public Protocol processCall(Node node, Protocol protocol) {
        String name = protocol.args[0];
        byte[] key = protocol.buffers[0].array();
        DataMapProxy dataMapProxy = (DataMapProxy) mapService.getProxy(name, true);
        // TODO: !!! FIX ME !!!
        Data value = dataMapProxy.get(new Data(SerializationConstants.CONSTANT_TYPE_BYTE_ARRAY, key));
        return protocol.success(value == null ? null : ByteBuffer.wrap(value.buffer));
    }
}
