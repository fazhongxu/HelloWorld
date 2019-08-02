package com.xxl.example.dagger2;

/**
 * Created by xxl on 19/4/27.
 * <p>
 * Description  // @Inject 注解作用
 *
 * 给一个类的构造方法 添加 @Inject
 *
 *
 * 编译时（android studio  Build－》ReBuid project）
 *
 * Dagger2 会根据注解标志去生存一个 这样的类  Student_Factory
 *
 *
 * package com.example.xxl.helloworld.dagger2;
 *
 * import dagger.internal.Factory;
 *
 * public final class Student_Factory implements Factory<Student> {
 *   private static final Student_Factory INSTANCE = new Student_Factory();
 *
 *   @Override
 *   public Student get() { // get 方法会new 一个Student实例对象
 *     return new Student();
 *   }
 *
 *   public static Student_Factory create() {
 *     return INSTANCE;
 *   }
 * }
 *
 *  Student 类似于快递 需要被送的物体  Student_Factory 类似于工厂 生产出快递
 *
 **/
public class Student {
    //@Inject
    public Student() {

    }
}
