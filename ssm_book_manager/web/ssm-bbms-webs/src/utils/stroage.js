/**
 @author: Jersey
 @create: 2022-02-13 20:12
 @version: V1.0
 @slogan: 业精于勤,荒于嬉;行成于思,毁于随。
 @description:
 */
export  default class Storage {

    constructor(props) {
        this.props = props || {}
        this.source = this.props.source || window.localStorage
        this.initRun();
    }
    initRun(){
        /*
        * set 存储方法
        * @ param {String}     key 键
        * @ param {String}     value 值，存储的值可能是数组/对象，不能直接存储，需要转换 JSON.stringify
        * @ param {String}     expired 过期时间，以分钟为单位
        */
        const reg = new RegExp("__expires__");
        let data = this.source;
        let list = Object.keys(data);
        if(list.length > 0){
            list.map((key)=>{
                if( !reg.test(key )){
                    let now = Date.now();
                    let expires = data[`${key}__expires__`]||Date.now+1;
                    if (now >= expires ) {
                        this.removeItem(key);
                    }
                }
                return key;
            });
        }
    }

    setItem(key, value, expired) {
        /*
        * set 存储方法
        * @ param {String}     key 键
        * @ param {String}     value 值，
        * @ param {String}     expired 过期时间，以毫秒为单位，非必须
        */
        let source = this.source;
        source[key] = JSON.stringify(value);
        if (expired){
            source[`${key}__expires__`] = Date.now() + expired
        }
        return value;
    }

    getItem(key) {
        /*
        * get 获取方法
        * @ param {String}     key 键
        * @ param {String}     expired 存储时为非必须字段，所以有可能取不到，默认为 Date.now+1
        */
        const source = this.source,
            expired = source[`${key}__expires__`]||Date.now+1;
        const now = Date.now();

        if ( now >= expired ) {
            this.removeItem(key);
            return;
        }
        return source[key] ? JSON.parse(source[key]) : source[key];
    }

    removeItem(key) {
        const data = this.source,
            value = data[key];
        delete data[key];
        delete data[`${key}__expires__`];
        return value;
    }

}