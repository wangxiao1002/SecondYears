# JNI demo
* 创建jni 接口
```
public class MyUtil {

    public static native String  getWord ();
}
```
* javah 生成头文件
```
javah -classpath . -jni com.sy.jnidemo.MyUtil

```
