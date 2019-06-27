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

    ButterKnife   也是这个原理


## 这也是一个简单的组件化架构

### 每一个组件对应一个中间件

组件之间没有直接的依赖关系（base类除外），组件之间通讯需要依赖对应的中间件来通讯，
组件之间如果有相同的接口需要调用（比如根据uid查询用户信息，返回一些用户数据，
这个属于user模块，但是现在领唱模块需要用，你也可以copy user模块的方法出来调用，
完全OK，但是最好不要那样做，正确的做法是user模块提供接口，并实现，
别的模块使用ARouter的provider方法调用），