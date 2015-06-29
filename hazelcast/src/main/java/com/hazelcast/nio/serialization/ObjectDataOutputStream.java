/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.nio.serialization;

import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.UTFEncoderDecoder;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

public class ObjectDataOutputStream extends OutputStream implements ObjectDataOutput, Closeable {

    private static final int UTF_BUFFER_SIZE = 1024;
    private final SerializationService serializationService;
    private final DataOutputStream dataOut;
    private final ByteOrder byteOrder;

    private byte[] utfBuffer;

    public ObjectDataOutputStream(OutputStream outputStream, SerializationService serializationService) {
        this.serializationService = serializationService;
        this.dataOut = new DataOutputStream(outputStream);
        this.byteOrder = serializationService.getByteOrder();
    }

    @Override
    public void write(int b) throws IOException {
        dataOut.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        dataOut.write(b, off, len);
    }

    @Override
    public void writeBoolean(boolean v) throws IOException {
        dataOut.writeBoolean(v);
    }

    @Override
    public void writeByte(int v) throws IOException {
        dataOut.writeByte(v);
    }

    @Override
    public void writeShort(int v) throws IOException {
        if (bigEndian()) {
            dataOut.writeShort(v);
        } else {
            dataOut.writeShort(Short.reverseBytes((short) v));
        }
    }

    @Override
    public void writeChar(int v) throws IOException {
        if (bigEndian()) {
            dataOut.writeChar(v);
        } else {
            dataOut.writeChar(Character.reverseBytes((char) v));
        }
    }

    @Override
    public void writeInt(int v) throws IOException {
        if (bigEndian()) {
            dataOut.writeInt(v);
        } else {
            dataOut.writeInt(Integer.reverseBytes(v));
        }
    }

    @Override
    public void writeLong(long v) throws IOException {
        if (bigEndian()) {
            dataOut.writeLong(v);
        } else {
            dataOut.writeLong(Long.reverseBytes(v));
        }
    }

    @Override
    public void writeFloat(float v) throws IOException {
        if (bigEndian()) {
            dataOut.writeFloat(v);
        } else {
            writeInt(Float.floatToIntBits(v));
        }
    }

    @Override
    public void writeDouble(double v) throws IOException {
        if (bigEndian()) {
            dataOut.writeDouble(v);
        } else {
            writeLong(Double.doubleToLongBits(v));
        }
    }

    @Override
    public void writeBytes(String s) throws IOException {
        dataOut.writeBytes(s);
    }

    @Override
    public void writeChars(String s) throws IOException {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int v = s.charAt(i);
            writeChar(v);
        }
    }

    @Override
    public void writeByteArray(byte[] bytes) throws IOException {
        int len = (bytes == null) ? 0 : bytes.length;
        writeInt(len);
        if (len > 0) {
            write(bytes);
        }
    }

    @Override
    public void writeCharArray(char[] chars) throws IOException {
        int len = chars != null ? chars.length : 0;
        writeInt(len);
        if (len > 0) {
            for (char c : chars) {
                writeChar(c);
            }
        }
    }

    @Override
    public void writeIntArray(int[] ints) throws IOException {
        int len = ints != null ? ints.length : 0;
        writeInt(len);
        if (len > 0) {
            for (int i : ints) {
                writeInt(i);
            }
        }
    }

    @Override
    public void writeLongArray(long[] longs) throws IOException {
        int len = longs != null ? longs.length : 0;
        writeInt(len);
        if (len > 0) {
            for (long l : longs) {
                writeLong(l);
            }
        }
    }

    @Override
    public void writeDoubleArray(double[] doubles) throws IOException {
        int len = doubles != null ? doubles.length : 0;
        writeInt(len);
        if (len > 0) {
            for (double d : doubles) {
                writeDouble(d);
            }
        }
    }

    @Override
    public void writeFloatArray(float[] floats) throws IOException {
        int len = floats != null ? floats.length : 0;
        writeInt(len);
        if (len > 0) {
            for (float f : floats) {
                writeFloat(f);
            }
        }
    }

    @Override
    public void writeShortArray(short[] shorts) throws IOException {
        int len = shorts != null ? shorts.length : 0;
        writeInt(len);
        if (len > 0) {
            for (short s : shorts) {
                writeShort(s);
            }
        }
    }

    @Override
    public void writeUTF(String str) throws IOException {
        if (utfBuffer == null) {
            utfBuffer = new byte[UTF_BUFFER_SIZE];
        }
        UTFEncoderDecoder.writeUTF(this, str, utfBuffer);
    }

    @Override
    public void write(byte[] b) throws IOException {
        dataOut.write(b);
    }

    @Override
    public void writeObject(Object object) throws IOException {
        serializationService.writeObject(this, object);
    }

    @Override
    public void writeData(Data data) throws IOException {
        serializationService.writeData(this, data);
    }

    @Override
    public byte[] toByteArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() throws IOException {
        dataOut.flush();
    }

    @Override
    public void close() throws IOException {
        dataOut.close();
    }

    @Override
    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    private boolean bigEndian() {
        return byteOrder == ByteOrder.BIG_ENDIAN;
    }
}
