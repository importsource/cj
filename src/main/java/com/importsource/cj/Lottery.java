package com.importsource.cj;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author hezhuofan
 */
public class Lottery {
    public static  Map<String, String> execute(String[] users, Map<String, Integer> awardStockMap) {
        final long awardCount=awardStockMap.values().stream().collect(Collectors.summarizingInt(i->i)).getSum();
        // 权重默认等于库存
        final Map<String, Integer> awardWeightMap = new ConcurrentHashMap<>(awardStockMap);
        final Map<String, Integer> initAwardStockMap = new ConcurrentHashMap<>(awardStockMap);
        System.out.printf("用户总数:%s \n",users.length);
        final long threshold = (100*awardCount)/users.length; //中奖概率
        Map<String, Integer> dailyWinCountMap = new ConcurrentHashMap<>(); // 每天实际中奖计数
        Map<String, String> dailyWinMap = new ConcurrentHashMap<>(); // 每天实际中奖计数
        for (int j = 0; j < (awardCount*100)/threshold; j++) { // 抽奖
            //确定是否中奖
            int randNum1 = new Random().nextInt(users.length);
            if(randNum1>threshold){
                dailyWinCountMap.compute("未中奖", (k,v)->v==null?1:v+1);
                continue; //未中奖
            }
            //中奖 确定是哪个奖品
            //排除掉库存为0的奖品
            Map<String, Integer> awardWeightHaveStockMap = awardWeightMap.entrySet().stream().filter(e->awardStockMap.get(e.getKey())>0).collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
            if(awardWeightHaveStockMap.isEmpty()){ //奖池已为空
                System.out.printf("第%d次抽奖 奖品已被抽完%n",j);
                break;
            }
            int totalWeight = (int) awardWeightHaveStockMap.values().stream().collect(Collectors.summarizingInt(i->i)).getSum();
            int randNum = new Random().nextInt(totalWeight);
            int prev=0;
            String choosedAward = null;
            for(Map.Entry<String,Integer> e : awardWeightHaveStockMap.entrySet() ){
                if(randNum>=prev && randNum<prev+e.getValue()){
                    choosedAward = e.getKey(); //落入此区间 中奖
                    dailyWinCountMap.compute(choosedAward, (k,v)->v==null?1:v+1);
                    dailyWinMap.put(choosedAward,dailyWinMap.get(choosedAward)==null?users[randNum1]:dailyWinMap.get(choosedAward).toString()+","+users[randNum1]);
                    break;
                }
                prev = prev+e.getValue();
            }
            //减小库存
            awardStockMap.compute(choosedAward, (k,v)->v-1);
        }
        log(awardStockMap, initAwardStockMap, dailyWinCountMap);
        return dailyWinMap;
    }

    private static void log(Map<String, Integer> awardStockMap, Map<String, Integer> initAwardStockMap, Map<String, Integer> dailyWinCountMap) {
        System.out.println("各奖品中奖计数: "); // 各奖品中奖计数
        dailyWinCountMap.entrySet().stream().sorted((e1,e2)->e2.getValue()-e1.getValue()).forEach(System.out::println);
        awardStockMap.forEach((k,v)->{if(v>0){
            System.out.printf("奖品：%s, 总库存： %d, 剩余库存： %d%n",k,initAwardStockMap.get(k),v);
        }});
    }
}
