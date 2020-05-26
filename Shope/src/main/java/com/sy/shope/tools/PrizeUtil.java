package com.sy.shope.tools;

import com.sy.shope.entity.PrizeDomain;

import java.text.DecimalFormat;

import java.util.List;

/**
 * 实现抽奖
 * @author wangxiao
 * @since
 */
public class PrizeUtil {


    public static String calcPrizeCode(List<PrizeDomain> prizeDomains) {
        DecimalFormat format = new DecimalFormat("#####00.00");

        String code ="";
        double sumWeight = 0;
        for (PrizeDomain prizeDomain : prizeDomains) {
            sumWeight += prizeDomain.getProbability();
        }
        double random= Math.random();

        double start = 0;
        double end = 0;
        for(int i=0;i<prizeDomains.size();i++){
            end += Double.parseDouble(String.valueOf(prizeDomains.get(i).getProbability()))/sumWeight;
            if(i==0){
                start = 0;
            }else{
                start += Double.parseDouble(String.valueOf(prizeDomains.get(i-1).getProbability()))/sumWeight;
            }
            if(random >= start && random <= end){
                code = prizeDomains.get(i).getCode();
                break;
            }
        }

        return code;
    }

}
