# HelloWorld

这是一个 对dagger2 常用注解的基本原理理解的一个demo

涉及到的注解主要有

    //@Inject

    //@Component

    //@Module

    //@Singleton

    //@Provides

    注解作用 和原理 见demo

    dagger2 主要运用了 java 的  抽象处理器 AbstractProcessor 类 编译时注解
     可以在编译时 结合 自定义注解生成相应功能的类

     //annotationProcessor "com.google.dagger:dagger-compiler:2.14.1"

    ButterKnife 也是这个原理