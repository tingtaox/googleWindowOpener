package com.happyworker.shopping.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderTarget {

    private String url;

    private String productSize;

}
