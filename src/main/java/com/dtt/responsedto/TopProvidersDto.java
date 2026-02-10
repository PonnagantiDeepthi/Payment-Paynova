package com.dtt.responsedto;

import java.util.List;

public class TopProvidersDto {
    private List<ProviderBucketDto> buckets;

    public TopProvidersDto(List<ProviderBucketDto> buckets) {
        this.buckets=buckets;
    }

    public List<ProviderBucketDto> getBuckets() {
        return buckets;
    }

    public void setBuckets(List<ProviderBucketDto> buckets) {
        this.buckets = buckets;
    }

    @Override
    public String toString() {
        return "TopProvidersDto{" +
                "buckets=" + buckets +
                '}';
    }
}
