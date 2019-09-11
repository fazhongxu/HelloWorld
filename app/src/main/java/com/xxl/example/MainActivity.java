package com.xxl.example;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.xxl.example.dagger2.animal.AnimalModule;
import com.xxl.example.dagger2.animal.DaggerAnimalComponent;
import com.xxl.example.dagger2.animal.Test;
import com.xxl.example.design.BankType;
import com.xxl.example.design.SaveMoneyFactory;
import com.xxl.example.mediator.dagger.MediatorDagger;
import com.xxl.example.mediator.web.MediatorWeb;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

/**
 * MainActivity 类似于 咱们的家  需要在家里面等快递
 */

public class MainActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

//    @Inject
//    Student mStudent;

    @Inject
    Test mTest;

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
         * import MainActivity;
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
         * import Student;
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


//        DaggerStudentComponent
//                .builder()
//                .studentModule(new StudentModule(this))
//                .build()
//                .inject(this);


        DaggerAnimalComponent.builder()
                .animalModule(new AnimalModule())
                .build()
                .inject(this);

        TextView testTv = findViewById(R.id.test_tv);

        testTv.setText(stringFromJNI());
        testTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MediatorDagger.startDagger();
//            Log.e("aaa", "onClick: "+MediatorWeb.getUserName());

                ArrayList<Integer> bankTypes = new ArrayList<>();
                bankTypes.add(BankType.ABC);
                bankTypes.add(BankType.CCB);
                bankTypes.add(BankType.CMB);

                for (int i = 0; i < bankTypes.size(); i++) {
                    String str = String.format(Locale.getDefault(),"type == %d,money == %f", bankTypes.get(i),
                            SaveMoneyFactory.getInstance().saveMoney(bankTypes.get(i))
                            .saveMoney(100));
                    Log.e("aaa", str);
                }
            }
        });

    }

    /**
     * 两个数相加 提供给C层调用
     * @param num1 参数1
     * @param num2 参数2
     */
    public void add(int num1,float num2) {
        float f_number = num1 + num2;
        Log.e("aaa", "test: "+f_number);
    }

    /**
     * 两个数相减 提供给C层调用
     * @param num1 参数1
     * @param num2 参数2
     */
    public static int sub(int num1,int num2) {
        int result = num1 - num2;
        Log.e("aaa", "sub: "+result );
        return result;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();



}
