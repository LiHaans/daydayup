/*
package cn.golaxy.operator;

import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

public class StrLength extends ScalarFunction {

    // 可选，open方法可以不编写。
    // 如果编写open方法，需要声明'import org.apache.flink.table.functions.FunctionContext;'。
    @Override
    public void open(FunctionContext context) {
    }
    public Integer eval(String a) {
        return a == null ? 0 : a.length();
    }
    public Integer eval(String b, String c) {
        return eval(b) + eval(c);
    }
    //可选，close方法可以不编写。
    @Override
    public void close() {
    }
}
*/
