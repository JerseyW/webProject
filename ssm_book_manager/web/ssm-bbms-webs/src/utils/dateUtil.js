/**
 * 格式为yyyy-MM-dd OR yyyy-MM-dd HH:mm:ss
 * @param {Object} date   日期对象
 * @param {Object} hasTime 是否有时间  true有时间，false没有时间
 */
export function formatDate(date,hasTime=false){
  const hasTimes = hasTime;//可传第二个参数false，返回yyyy-MM-dd
  const d = date;
  const year = d.getFullYear();
  const month = (d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1);
  const day = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
  const hour = d.getHours() < 10 ? '0' + d.getHours() : d.getHours();
  var minute = d.getMinutes()<10 ? '0'+d.getMinutes() : d.getMinutes();
  const second = d.getSeconds() < 10 ? '0' + d.getSeconds() : d.getSeconds();
  if(hasTimes){
      return [year, month, day].join('-') + " " + [hour, minute, second].join(':');
  }else{
      return [year, month, day].join('-');
  }
}
