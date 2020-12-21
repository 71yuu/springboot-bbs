package com.bbs.controller.front;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@RestController
public class UploadController {

	// 端口
    private static final String ENDPOINT = "";
	// ACCESS_KEY_ID
    private static final String ACCESS_KEY_ID = "";
	// ACCESS_KEY_SECRET
    private static final String ACCESS_KEY_SECRET = "";
	// BUCKET_NAME
    private static final String BUCKET_NAME = "";

    /**
     * 上传图片到本地
     *
     * @param editorFiles
     * @param request
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Map<String, Object> upload(MultipartFile[] editorFiles, MultipartFile avatarFile, @RequestParam(value = "avatar_data", required = false) String fileCut, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // 头像上传
        if (avatarFile != null) {
            result.put("result", writeFile(avatarFile, fileCut, request));
        }


        // wangEditor 上传
        if (editorFiles != null && editorFiles.length > 0) {
            List<String> fileNames = new ArrayList<>();

            for (MultipartFile editorFile : editorFiles) {
                fileNames.add(writeFile(editorFile, null, request));
            }

            result.put("errno", 0);
            result.put("data", fileNames);
        }

        return result;
    }

    /**
     * 将图片写入指定目录
     *
     * @param multipartFile
     * @param request
     * @return 返回文件完整路径
     */
    private String writeFile(MultipartFile multipartFile, String fileCut, HttpServletRequest request) {
        // 获取文件后缀
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newName = UUID.randomUUID() + "." + suffix;

        // 文件存放路径
        String filePath = request.getSession().getServletContext().getRealPath("/upload");

        // 判断路径是否存在，不存在则创建文件夹
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 将文件写入目标
        file = new File(filePath, newName);
        try {
            multipartFile.transferTo(file);

            // 图片剪裁
            if (fileCut != null) {
                // JSON的对象格式的字符串转换成json数据
                JSONObject json = JSONObject.parseObject(fileCut);
                String x = json.get("x").toString();
                String y = json.get("y").toString();
                String width = json.get("width").toString();
                String height = json.get("height").toString();

                // 下面的要的数据时int类型，把小数去掉
                if (x.indexOf(".") > -1) {
                    x = x.substring(0, x.indexOf("."));
                }
                if (y.indexOf(".") > -1) {
                    y = y.substring(0, y.indexOf("."));
                }
                if (width.indexOf(".") > -1) {
                    width = width.substring(0, width.indexOf("."));
                }
                if (height.indexOf(".") > -1) {
                    height = height.substring(0, height.indexOf("."));
                }

                // 剪切图片
                BufferedImage image = ImageIO.read(file);
                image = image.getSubimage(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width),
                        Integer.parseInt(height));

                // 压缩边长为100的正方形, 生成图片 到指定的位置
                Builder<BufferedImage> builder = null;
                builder = Thumbnails.of(image).size(100, 100);
                builder.toFile(file);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        byte[] bytes = new byte[(int)file.length()];
        try {
            new FileInputStream(file).read(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OSS client = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            client.putObject(new PutObjectRequest(BUCKET_NAME, newName, new ByteArrayInputStream(bytes)));
            // 上传文件路径 = http://BUCKET_NAME.ENDPOINT/自定义路径/fileName
            filePath = "http://" + BUCKET_NAME + "." + ENDPOINT + "/" + newName;
            file.delete();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }

        return filePath;
    }
}
