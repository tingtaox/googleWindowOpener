package com.happyworker.shopping;

import com.happyworker.shopping.model.OrderTarget;
import com.happyworker.shopping.service.SupermanShopping;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SupermanShoppingTest {
    private SupermanShopping superman;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Users/zhixianb/workspace/files/chromedriver");

        superman = new SupermanShopping();
    }

    @Test
    public void testShopSingleSuccess() throws Exception {
        System.out.println(System.getProperty("webdriver.chrome.driver"));


        superman.shopSingle(
                OrderTarget.builder().url("http://www.supremenewyork.com/shop/pants/eksi1l9z0/vr8gni103").build(),
                1);
    }


    @Test
    public void testShopMultipleSuccess() throws Exception {
        List<OrderTarget> targetList = Arrays.asList(
                OrderTarget.builder().url("http://www.supremenewyork.com/shop/pants/eksi1l9z0/vr8gni103").build(),
                OrderTarget.builder().url("http://www.supremenewyork.com/shop/pants/f25hz96nw/pxtfe03cw").build(),
                OrderTarget.builder().url("http://www.supremenewyork.com/shop/accessories/wkf1tbd6j/e8c56njah").build()
        );
        superman.shopMultiple(targetList, 1);
    }


}
