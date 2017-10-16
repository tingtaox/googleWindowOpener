# googleWindowOpener
The opener is able to open 2 windows simultaneously. And it can be scaled to open n windows.

### Before Run
* Add a java class under billing folder named "BillingInfo.java"
* Add following code and fill with value to use:
<pre>
    public final static String ORDER_BILL_NAME = "dummy name";
    public final static String ORDER_EMAIL = "dummy email";
    public final static String ORDER_TEL_NUMBER = "123456890";
    public final static String ORDER_ADDRESS = "dummy address";
    public final static String ORDER_ZIP = "90007";
    public final static String ORDER_CITY = "la";
    public final static String ORDER_STATE = "CA";
    public final static String ORDER_COUNTRY = "USA";
    
    public final static String CREDIT_CARD_NUMBER = "1234567890123456789";
    public final static String CREDIT_CARD_CVV = "123";
    public final static String CREDIT_CARD_EXP_MONTH = "01";
    public final static String CREDIT_CARD_EXP_YEAR = "2020";
</pre>

### How to run test
1. Go to https://sites.google.com/a/chromium.org/chromedriver/downloads and download ChromeDriver 2.32
2. Extract the zip file and the path to the chromedriver in line 12 BaseTest.java
3. run $mvn test-compile
4. run $mvn test

You should see one Shopping page open and operated all the way to checkout.
