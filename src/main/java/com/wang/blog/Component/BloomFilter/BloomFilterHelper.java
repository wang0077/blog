package com.wang.blog.component.bloomfilter;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;

/**
 * @author wangsiyuan
 */
@SuppressWarnings("all")
public class BloomFilterHelper<T> {

    private final int numHashFunctions;

    private final int biteSize;

    private final Funnel<T> funnel;

    /**
     *
     * @param expectedInsertions 估计数据量
     * @param fpp 错误率
     */
    public BloomFilterHelper(Funnel<T> funnel,int expectedInsertions, double fpp) {
        Preconditions.checkArgument( funnel != null,"funnel不能为空");
        this.funnel = funnel;
        this.biteSize = optimalNumOfBits(expectedInsertions,fpp);
        this.numHashFunctions = optimalNumOfHashFunctions(expectedInsertions,biteSize);
    }

    /**
     *
     * @param value 传入的值
     * @return 返回Hash数组
     */
    public int[] murmurHashOffset(T value){
        int[] offset = new int[numHashFunctions];
        long hash64 = Hashing.murmur3_128().hashObject(value,funnel).asLong();
        int hash1 = (int)hash64;
        int hash2 = (int)(hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions;i++){
            int nextHash = hash1 + i * hash2;
            if(nextHash < 0){
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % biteSize;
        }
        return offset;
    }

    /**
     *
     *
     * @return 通过公式计算过滤器长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            // 设定最小期望长度
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     *
     * @return 通过公式计算需要多少个Hash函数算法
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }
}
