package com.jhl.util;

import com.jcraft.jsch.*;
import com.jhl.constant.ConfigKey;
import com.jhl.service.SysConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * @author YangHua
 * 转载请注明出处：http://www.xfok.net/2009/10/124485.html
 */
public class SFTPUtil {

    String host = "220.248.70.90";
    int port = 28888;
    String username = "testLu";
    String password = "1234!@#$qaz";
    String directory = "/testLu/20160219/";
    private static SFTPUtil sftpUtil = new SFTPUtil();


    public InputStream downLoad(String head){
        ChannelSftp sftp= sftpUtil.connect(host, port, username, password);
        return sftpUtil.download(directory, assembleFileName(head), sftp);
    }

    private String assembleFileName(String head){
        String date = DateUtil.format(DateUtil.yesterday(),"YYYYMMDD");
        return head + "_" +SysConfig.geteConfigByKey(ConfigKey.JYT_ACC) + "_" +date;
    }

    private SFTPUtil(){
        host = SysConfig.geteConfigByKey(ConfigKey.JYT_BILLING_HOST);
        port = Integer.parseInt(SysConfig.geteConfigByKey(ConfigKey.JYT_BILLING_PORT));
        username = SysConfig.geteConfigByKey(ConfigKey.JYT_BILLING_USERNAME);
        password = SysConfig.geteConfigByKey(ConfigKey.JYT_BILLING_PWD);
        directory = SysConfig.geteConfigByKey(ConfigKey.JYT_BILLING_DIR);
    }

    public static SFTPUtil instance(){
        return sftpUtil;
    }

    /**
     * 连接sftp服务器
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    private ChannelSftp connect(String host, int port, String username,
                               String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {

        }
        return sftp;
    }

    /**
     * 上传文件
     * @param directory 上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp
     */
    private void upload(String directory, String uploadFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param sftp
     */
    private InputStream download(String directory, String downloadFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            return sftp.get(downloadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     * @param directory 要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp
     */
    private void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出目录下的文件
     * @param directory 要列出的目录
     * @param sftp
     * @return
     * @throws SftpException
     */
    private Vector listFiles(String directory, ChannelSftp sftp) throws SftpException{
        return sftp.ls(directory);
    }

    public static void main(String[] args) {
//        SFTPUtil sf = new SFTPUtil();
//
////        String uploadFile = "D:\\tmp\\upload.txt";
//        String downloadFile = "DFDZ_00120000000010000297_20160219.txt";
//        String saveFile = "D:\\tmp\\download.txt";
////        String deleteFile = "delete.txt";
//        ChannelSftp sftp=sf.connect(host, port, username, password);
////        sf.upload(directory, uploadFile, sftp);
//        sf.download(directory, downloadFile, saveFile, sftp);
////        sf.delete(directory, deleteFile, sftp);
//        try{
////            sftp.cd(directory);
////            sftp.mkdir("ss");
//            System.out.println("finished");
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }
}
//    public static void main(String[] args) {
//        SFTPUtil.downFile("220.248.70.90",28888,"testLu","1234!@#$qaz","/testLu/20160219","DFDZ_00120000000010000297_20160219.txt","D:/");
//    }
