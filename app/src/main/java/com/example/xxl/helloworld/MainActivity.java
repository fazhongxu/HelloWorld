package com.example.xxl.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.xxl.helloworld.dagger2.DaggerStudentComponent;
import com.example.xxl.helloworld.dagger2.Student;
import com.example.xxl.helloworld.dagger2.StudentModule;
import com.example.xxl.mediator_web.MediatorWeb;

import javax.inject.Inject;
/**
 * MainActivity 类似于 咱们的家  需要在家里面等快递
 */

public class MainActivity extends AppCompatActivity  {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Inject
    Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //需要快递 则需要快递员送过来
        // 依赖注入  如果不添加这句话 那么 mStudent 变量时一个null 因为 activity 并没有和student 关联起来

        /**
         * // Generated by Dagger (https://google.github.io/dagger).
         * package com.example.xxl.helloworld.dagger2;
         *
         * import com.example.xxl.helloworld.MainActivity;
         * import com.example.xxl.helloworld.MainActivity_MembersInjector;
         * import dagger.internal.Preconditions;
         *
         * public final class DaggerStudentComponent implements StudentComponent {
         *   private StudentModule studentModule;
         *
         *   private DaggerStudentComponent(Builder builder) {
         *     initialize(builder);
         *   }
         *
         *   public static Builder builder() {
         *     return new Builder();
         *   }
         *
         *   @SuppressWarnings("unchecked")
         *   private void initialize(final Builder builder) {
         *     this.studentModule = builder.studentModule;
         *   }
         *
         *   @Override
         *   public void inject(MainActivity mainActivity) {
         *     injectMainActivity(mainActivity);
         *   }
         *
         *   private MainActivity injectMainActivity(MainActivity instance) {
         *     MainActivity_MembersInjector.injectMStudent(
         *         instance, StudentModule_ProvideStudentFactory.proxyProvideStudent(studentModule));
         *     return instance;
         *   }
         *
         *   public static final class Builder {
         *     private StudentModule studentModule;
         *
         *     private Builder() {}
         *
         *     public StudentComponent build() {
         *       if (studentModule == null) {
         *         throw new IllegalStateException(StudentModule.class.getCanonicalName() + " must be set");
         *       }
         *       return new DaggerStudentComponent(this);
         *     }
         *
         *     public Builder studentModule(StudentModule studentModule) {
         *       this.studentModule = Preconditions.checkNotNull(studentModule);
         *       return this;
         *     }
         *   }
         * }
         *
         *
         *
         */


        //快递员就是去拿快递 Student 然后到你家 MainActivity
        // 查看 inject 源码 会发现其实时调用了 MainActivity_MembersInjector

        /**
         * // Generated by Dagger (https://google.github.io/dagger).
         * package com.example.xxl.helloworld;
         *
         * import com.example.xxl.helloworld.dagger2.Student;
         * import dagger.MembersInjector;
         * import javax.inject.Provider;
         *
         * public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
         *   private final Provider<Student> mStudentProvider;
         *
         *   public MainActivity_MembersInjector(Provider<Student> mStudentProvider) {
         *     this.mStudentProvider = mStudentProvider;
         *   }
         *
         *   public static MembersInjector<MainActivity> create(Provider<Student> mStudentProvider) {
         *     return new MainActivity_MembersInjector(mStudentProvider);
         *   }
         *
         *   @Override
         *   public void injectMembers(MainActivity instance) {
         *     injectMStudent(instance, mStudentProvider.get());
         *   }
         *
         *
         *   //&#x91cd;&#x70b9;
         *   //instance.mStudent &#x5c31;&#x662f; MainActivity &#x7684;     @Inject Student mStudent;&#x5bf9;&#x8c61;
         *
         *   //mStudent &#x5c31;&#x662f; Student_Factory &#x751f;&#x6210;&#x7684; Student &#x5bf9;&#x8c61;
         *   public static void injectMStudent(MainActivity instance, Student mStudent) {
         *     instance.mStudent = mStudent;
         *   }
         * }
         *
         */

        /**
         *
         * public static void injectMStudent(MainActivity instance, Student mStudent) {
         *        instance.mStudent = mStudent;
         * }
         *
         *
         *  instance.mStudent = mStudent; 这样 创建的 Student对象就和
         *
         *  MainActivity 的 @Injet Student mStudent; 对象关联起来了
         */


        DaggerStudentComponent
                .builder()
                .studentModule(new StudentModule(this))
                .build()
                .inject(this);

        TextView testTv = findViewById(R.id.test_tv);
        testTv.setText(stringFromJNI());
        testTv.setOnClickListener(new View.OnClickListener() {
                    @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "我是Student 我有值"+mStudent.toString(), Toast.LENGTH_SHORT).show();

                MediatorWeb.startWeb("www.google.com");
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

}
