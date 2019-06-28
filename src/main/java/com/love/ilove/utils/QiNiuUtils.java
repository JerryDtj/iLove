package com.love.ilove.utils;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Jerry
 * @Date 2019-06-04 08:01
 */
public class QiNiuUtils {
    private static final String accessKey = "xP26v6i5ZX5ricklajjShhW9Jjc20X-PKc9X9MOW";
    private static final  String secretKey = "YfDr4HEk9cgtjscKa7BgIbGvU6u5W79kNhJPVi0A";
    private static final  String agreeImgType = "image/*";
    private static final  String imgMaxType = "5242880";


    /**
     * 获取bucket里面所有文件的信息
     * @param bucketNm
     * @param prefix 以什么开头的文件
     */
    public static BucketManager.FileListIterator getFileInfo(String bucketNm, String prefix) {
        BucketManager.FileListIterator fileListIterator = null;
        try {
            BucketManager bucketManager = getBucketManager();

            //每次迭代的长度限制，最大1000，推荐值 1000
            int limit = 1000;
            //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
            String delimiter = "";

            //列举空间文件列表
            fileListIterator = bucketManager.createFileListIterator(bucketNm, prefix, limit, delimiter);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return fileListIterator;
    }

    /**
     * 获取制定空间下绑定的域名，不带http/https
     * @param bucketName
     * @return
     * @throws QiniuException
     */
    public static String getDomain(String bucketName) throws QiniuException {
        BucketManager bucketManager = getBucketManager();
        String[] s = bucketManager.domainList(bucketName);
        Assert.notNull(s,bucketName+"获取不到域名");
        return s[0];
    }

    /**
     * 根据前缀和空间名称获取私密下载链接
     * @param bucketNm
     * @param prefix
     * @return
     * @throws QiniuException
     */
    public static List<String> getSecretDolandLoadUrl(String bucketNm, String prefix,String picFomart) throws QiniuException {
        if (StringUtils.isBlank(picFomart)){
            picFomart="";
        }
        String domain = getDomain(bucketNm);
        String url = String.format("http://%s/",domain);
        BucketManager.FileListIterator fileListIterator = getFileInfo(bucketNm, prefix);
        List<String> urlList = new ArrayList<>();
        while (fileListIterator.hasNext()){
            FileInfo[] fileInfos = fileListIterator.next();
            for (FileInfo info:fileInfos){
                String publicUrl = url+info.key+picFomart;
                String securiteUrl = getPrivateDownloadUrl(publicUrl);
                urlList.add(securiteUrl);
            }

        }
        return urlList;
    }

    /**
     * 获取所有的bucket
     */
    public static void getBucketsInfo() {
        try {
            BucketManager bucketManager = getBucketManager();
            //获取所有的bucket信息
            String[]  bucketNms = bucketManager.buckets();
            for(String bucket:bucketNms) {
                System.out.println(bucket);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除多个文件
     * @param bucketNm bucket的名称
     * @param keys     文件名称数组
     * //单次批量请求的文件数量不得超过1000 , 这个是七牛所规定的
     * @return
     */
    public static boolean deletes(String bucketNm ,String [] keys) {
        boolean result = false;
        try {
            //当文件大于1000的时候，就直接不处理
            if(keys.length >1000) {
                return result;
            }

            //设定删除的数据
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucketNm, keys);
            BucketManager bucketManager = getBucketManager();
            //发送请求
            Response response = bucketManager.batch(batchOperations);

            //返回数据
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < keys.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keys[i];
                System.out.print(key + "\t");
                if (status.code == 200) {
                    System.out.println("delete success");
                } else {
                    System.out.println(status.data.error);
                    return result;
                }
            }
            result = true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 删除bucket中的文件名称
     * @param bucketNm bucker名称
     * @param key 文件名称
     * @return
     */
    public static Boolean delete(String bucketNm ,String key) {
         boolean result = false;
        try {
            BucketManager mg = getBucketManager();
            mg.delete(bucketNm, key);
            result = true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 上传输入流
     * @param bucketNm  bucket的名称
     * @param in        输入流
     * @return
     */
    public static boolean upload(String bucketNm, InputStream in, String key) {
        boolean result = false;
        try {
            UploadManager uploadManager = getUploadManager(bucketNm);

            //获取token
            String token = getToken(bucketNm,null);

            //上传输入流
            Response response = uploadManager.put(in,key,token, null,null);

            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            result = true;
//            result = new Result(true,putRet.key);
        }catch (Exception e) {
            e.printStackTrace();
//            result = new Result(false);
        }
        return result;
    }
    /**
     * 通过文件来传递数据
     * @param bucketNm
     * @param file
     * @param prefix
     * @return
     */
    public static boolean upload(String bucketNm,File file,String prefix) {
        boolean result = false;
        try {

            UploadManager uploadManager = getUploadManager(bucketNm);

            String token = getImgToken(bucketNm,prefix);
            Response response = uploadManager.put(file.getAbsolutePath(),prefix+file.getName(), token);

            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            result = true;
//            result = new Result(true,putRet.key);
        } catch (QiniuException e) {
            e.printStackTrace();
//            result = new Result(false);
        }
        return result;

    }

    /**
     * 通过老文件的名称自动生成新的文件
     *
     * @param oldName
     * @return
     */
    public static String newName(String oldName) {
        String[] datas = oldName.split("\\.");
        String type = datas[datas.length - 1];
        String newName = UUID.randomUUID().toString() + "." + type;
        return newName;
    }

    /**
     * 获取上传管理器
     * @param bucketNm
     * @return
     */
    public static UploadManager getUploadManager(String bucketNm) {
        //构造一个带指定Zone对象的配置类
        //区域要和自己的bucket对上，不然就上传不成功
        //华东    Zone.zone0()
        //华北    Zone.zone1()
        //华南    Zone.zone2()
        //北美    Zone.zoneNa0()
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        return uploadManager;
    }
    /**
     * 获取Bucket的管理对象
     * @return
     */
    public static BucketManager getBucketManager() {
        //构造一个带指定Zone对象的配置类
        //区域要和自己的bucket对上，不然就上传不成功
        //华东    Zone.zone0()
        //华北    Zone.zone1()
        //华南    Zone.zone2()
        //北美    Zone.zoneNa0()
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        return bucketManager;
    }

    /**
     * 获取私有空间下载
     * @param publicUrl
     * @return
     */
    public static String getPrivateDownloadUrl(String publicUrl){
        Auth auth = Auth.create(accessKey, secretKey);
        //1小时，可以自定义链接过期时间
        long expireInSeconds = 3600;
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        return finalUrl;
    }


    /**
     * 获取七牛云的上传Token
     * @param bucketNm
     * @return
     */
    public static String getToken(String bucketNm,String key) {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken;
        if (StringUtils.isNotBlank(key)){
            upToken = auth.uploadToken(bucketNm,key);
        }else {
            upToken = auth.uploadToken(bucketNm);
        }

        return upToken;
    }

    /**
     * 生成7牛云图片上传key
     * @param bucketNm
     * @param key
     * @return
     */
    public static String getImgToken(String bucketNm,String key) {
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        try {
            putPolicy.put("mimeLimit",agreeImgType);
            putPolicy.put("fsizeLimit",Long.valueOf(imgMaxType));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auth.uploadToken(bucketNm,key,3600,putPolicy);
    }

}
