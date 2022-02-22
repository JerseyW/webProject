/**
 @author: Jersey
 @create: 2022-02-16 11:19
 @version: V1.0
 @slogan: 业精于勤,荒于嬉;行成于思,毁于随。
 @description:
 */
 import crypto from 'crypto-js';

export  default class Cryptos {

    static iv = crypto.enc.Utf8.parse('abcdefgabcdefg12');
    static key = crypto.enc.Utf8.parse('abcdefgabcdefg12'); //偏移量;

    // 解密方法
    static decrypt(word) {
        const decrypt = crypto.AES.decrypt(word, this.key, {mode: crypto.mode.CBC,iv: this. iv, padding: crypto.pad.Pkcs7});
        return crypto.enc.Utf8.stringify(decrypt).toString();

    }

    // 加密方法
    static encrypt(word){
        const srcs = crypto.enc.Utf8.parse(word);
        const encrypted = crypto.AES.encrypt(srcs, this.key, {mode: crypto.mode.CBC,
            iv: this.iv, padding: crypto.pad.Pkcs7});
        return encrypted.toString();
    }

}