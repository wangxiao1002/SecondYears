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


    public String calcPrizeCode (List<PrizeDomain> prizeDomains) {
        DecimalFormat format = new DecimalFormat("#####00.00");

        String code ="";
        double sumWeight = 0;
        for (PrizeDomain prizeDomain : prizeDomains) {
            sumWeight += prizeDomain.getProbability();
        }
        double random= Math.random();

        double d1 = 0;
        double d2 = 0;
        for(int i=0;i<prizeDomains.size();i++){
            d2 += Double.parseDouble(String.valueOf(prizeDomains.get(i).getProbability()))/sumWeight;
            if(i==0){
                d1 = 0;
            }else{
                d1 +=Double.parseDouble(String.valueOf(prizeDomains.get(i-1).getProbability()))/sumWeight;
            }
            if(random >= d1 && random <= d2){
                code = prizeDomains.get(i).getCode();
                break;
            }
        }

        return code;
    }


    public static void main(String[] args) {

    }
}
