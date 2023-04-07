package com.golaxy.source;

import com.golaxy.bean.Address;
import com.golaxy.bean.Customer;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

import java.util.Date;
import java.util.Random;

/**
 * @Auther: lihang
 * @Date: 2021-05-27 21:03
 * @Description:
 */
public class StreamParallelSource implements ParallelSourceFunction<Customer> {

    private boolean isRunning = true;
    private String[] names = new String[5];
    private Address[] addresses = new Address[5];
    private Random random = new Random();
    private Long id = 1L;

    public void init() {
        names[0] = "刘备";
        names[1] = "关羽";
        names[2] = "张飞";
        names[3] = "曹操";
        names[4] = "诸葛亮";

        addresses[0]= new Address(1, "湖北省", "武汉市");
        addresses[1]= new Address(2, "湖北省", "黄冈市");
        addresses[2]= new Address(3, "广东省", "广州市");
        addresses[3]= new Address(4, "广东省", "深圳市");
        addresses[4]= new Address(5, "浙江省", "杭州市");
    }

    /**
     * 每隔10ms生成一个Customer数据对象（模拟获取实时数据）
     */
    @Override
    public void run(SourceContext<Customer> sourceContext) throws Exception {
        init();
        while(isRunning) {
            int nameIndex = random.nextInt(5);
            int addressIndex = random.nextInt(5);

            Customer customer = new Customer();
            customer.setId(id++);
            customer.setName(names[nameIndex]);
            customer.setGender(random.nextBoolean());
            customer.setBirth(new Date());
            customer.setAddress(addresses[addressIndex]);
            customer.setDescription("" + names[nameIndex] + "来自" + addresses[addressIndex].getProvince() + addresses[addressIndex].getCity());
            /**
             * 把创建的数据返回给Flink进行处理
             */
            sourceContext.collect(customer);
            Thread.sleep( 5);
        }
    }

    @Override
    public void cancel() {
        this.isRunning = false;
    }
}
