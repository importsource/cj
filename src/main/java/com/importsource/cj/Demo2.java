package com.importsource.cj;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.importsource.cj.Lottery.execute;

/**
 * @author hezhuofan
 */
public class Demo2 {
    public static void main(String[] args) {
        Map<String, String> winMap=execute(input(),initAward());
        winMap.forEach((k,v)->{
            System.out.printf("奖项：%s, 名单： %s\n",k,winMap.get(k));
        });
    }

    private static  Map<String, Integer> initAward() {
        final Map<String, Integer> awardStockMap = new ConcurrentHashMap<>();
        awardStockMap.put("五等奖", 20);
        awardStockMap.put("四等奖", 10);
        awardStockMap.put("三等奖", 8);
        awardStockMap.put("二等奖", 4);
        awardStockMap.put("一等奖", 2);
        awardStockMap.put("特等奖", 1);
        return awardStockMap;
    }

    private static  String[] input() {
        return new String[]{
                "晗昱","晗日","涵畅","涵涤", "涵亮", "涵忍", "涵容", "涵润", "涵涵", "涵煦", "涵蓄", "涵衍", "涵意", "涵映", "涵育",
                "翰采", "翰池", "翰飞", "翰海", "翰翮", "翰林", "翰墨", "翰学", "翰音", "瀚玥", "翰藻", "瀚海", "瀚漠", "昊苍", "昊昊",
                "昊空", "昊乾", "昊穹", "昊然", "昊然", "昊天", "昊焱", "昊英", "浩波", "浩博", "浩初", "浩大", "浩宕", "浩荡", "浩歌" ,"王峻熙", "张嘉懿",
                "李煜城", "赵懿轩", "王烨华", "杨煜祺", "阎智宸", "孙正豪", "吴昊然", "郭志泽", "李明杰", "杨弘文", "靳烨伟", "马苑博", "张鹏涛", "叶红艳",
                "张天荣", "孙志梅", "刘雪恩", "孙荣"};
    }


}
