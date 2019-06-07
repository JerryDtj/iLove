package com.love.ilove.enums;

import lombok.Getter;

/**
 * @author Jerry
 * @Date 2019-06-07 07:02
 */
@Getter
public enum  QiniuEnum {
    PUBLICS("lovephoto","psk85spi5.bkt.clouddn.com"),
    SECURITY("ilove","img.tianzijiaozi.top");
    String bucketName;
    String url;

    QiniuEnum(String bucketName, String url) {
        this.bucketName = bucketName;
        this.url = url;
    }
}
