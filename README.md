# EasyMockMaster
方便大家可以在app上使用大搜车的easymock，提供部分接口mock，以及真实数据录制功能
## 背景

在前端和移动端的开发中，造数据一直是一个非常苦恼的事情，麻烦后端也并不是一个长久之计，为了解决这个问题，在开源环境下产生了大量的mock平台，支持利用moc.js语法随机生成各种mock数据
然而，对于移动端来说，使用这些平台天然有一些弊端要解决：

1. url的动态切换，每次运行中利用mock解决了问题，必须手动将mock地址的URL改回真实的URL
2. 部分接口的mock依赖真实的数据接口，即进入这个界面进行调试，必须先把前面的接口全部mock掉

##  项目模块

app - EasyMockHelper的Demo用法展示示例app

mockHelper - mockHelper源代码

# Get Started

1. 根目录的gradle文件
```groovy
allprojects {
		repositories {
            //添加jitpack.io
			maven { url 'https://jitpack.io' }
		}
	}
```
2.添加 EasyMockMaster的依赖
```groovy
dependencies {
        //记得版本号改为最新 
	    implementation 'com.github.blackjuly:EasyMockMaster:0.0.4'
	}
```
3.在你的app的application文件中，初始化 

```java
    public static  String baseIp ="http://ip地址:port/";
    public static  String userId = "用户名";
    public static String password = "密码";
    public static  String mockDataProjectId = "工程id";

     @Override
    public void onCreate() {
        //初始化
         EasyMockHelperApplication.init(this,userId,password,baseIp,mockDataProjectId);
    }
```

4.将mock功能添加到你的okhttp的网络模块
```java
import okhttp3.Interceptor;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;

public class SomeNetworkClass {
    public Retrofit.Builder init(){
        Builder clientBuilder = new Builder();
        IMockService mockService = new MockServiceImpl();
        List<Interceptor> list = mockService.getMockInterceptors();
        for (Interceptor interceptor : list) {
            clientBuilder.addInterceptor(interceptor);
        }
        
        // 构建 Retrofit
       return new Retrofit.Builder()
                .client(clientBuilder.build())
                .baseUrl("www.xxx.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    
```
5.OK，可以开始享受你的mock生活啦~~

具体mock功能详细使用，请看wiki！