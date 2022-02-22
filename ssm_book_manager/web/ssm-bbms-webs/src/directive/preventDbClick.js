/**
 @author: Jersey
 @create: 2022-02-18 10:27
 @version: V1.0
 @slogan: 业精于勤,荒于嬉;行成于思,毁于随。
 @description:
 */
import  Vue from  "vue";
const  DEFAULT_TIMER = 2000;
const preventDbClick = Vue.directive('preventDbClick',{
     inserted(ele,binding){
         let timer;
        ele.addEventListener('click',()=>{
            if (!ele.disabled){

                ele.disabled = !binding.value.isLoading;
                if (ele.disabled){
                    timer = setTimeout(()=>{
                        ele.disabled = false;
                        clearInterval(timer);
                        timer = null;
                    },binding.value.timer || DEFAULT_TIMER)
                }

            }
        })
      }
    } 
);


export  default  preventDbClick;