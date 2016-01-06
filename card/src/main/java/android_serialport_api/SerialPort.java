/*
 * Copyright 2009 Cedric Priscal
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

package android_serialport_api;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {
    /**
     * TAG the current class(SerialPort) simple name
     */
    public static final String TAG = "SerialPort";

    /*
     * Do not remove or rename the field mFd: it is used by native method close();
     */
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;
    private String serialportPath;
    private int baudrate;

    /**
     * 得到串口的路径
     *
     * @return　串口路径
     */
    public String getPath() {
        return serialportPath;
    }

    /**
     * 得到串口波特率
     *
     * @return 波特率
     */
    public int getBaudrate() {
        return baudrate;
    }

    /**
     * 构造函数
     *
     * @param serialportPath 指定的串口
     * @param baudrate       波特率
     * @param flags          标记
     * @throws SecurityException 安全异常
     * @throws IOException       io异常
     */
    protected SerialPort(String serialportPath, int baudrate, int flags) throws SecurityException, IOException {
        this.serialportPath = serialportPath;
        this.baudrate = baudrate;
        File device = new File(serialportPath);
        /* Check access permission */
        if (!device.canRead() || !device.canWrite()) {
            try {
                /* Missing read/write permission, trying to chmod the file */
                Process su;
                su = Runtime.getRuntime().exec("/system/bin/su");
                String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
                        + "exit\n";
                su.getOutputStream().write(cmd.getBytes());
                if ((su.waitFor() != 0) || !device.canRead()
                        || !device.canWrite()) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }

        mFd = open(device.getAbsolutePath(), baudrate, flags);
        if (mFd == null) {
            Log.d(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
    }

    /**
     * 得到输入流
     *
     * @return 串口输入流
     */
    public InputStream getInputStream() {
        return mFileInputStream;
    }

    /**
     * 得到输出流
     *
     * @return 串口输出流
     */
    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }

    /**
     * 关闭串口以及输入输出流
     */
    protected synchronized void closeAll() {
        if (mFileInputStream != null) {
            try {
                mFileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFileInputStream = null;
        }
        if (mFileOutputStream != null) {
            try {
                mFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFileOutputStream = null;
        }
        close();
    }

    /**
     * jni
     * 打开串口
     *
     * @param path     串口路径
     * @param baudrate 波特率
     * @param flags    标记
     * @return 文件描述符对象
     */
    private native static FileDescriptor open(String path, int baudrate, int flags);

    /**
     * jni
     * 关闭串口
     */
    public native void close();

    /**
     * 加载jni libserial_port.so
     */
    static {
        System.loadLibrary("serial_port");
    }
}