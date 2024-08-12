#  快速开始
   在app的build.gradle文件中添加依赖

      implementation "io.github.upappio:upapp:4.4.0"
   
# 接收参数

   在入口文件或者Application中调用打开 CustomWebView 打开下载页

      CustomWebView.getInstance(Context,"包名");

   该页面可作为引导页使用 仅打开一次  会自动关闭 发送数据 并打开接收页

   配置接收页 如MainActivity

   编辑Manifest.xml
   构造接收类  如接收类为MainActivity 添加协议 并使 android:exported="true"

      <activity
        android:name=".MainActivity"
        android:exported="true">
        ...
        <intent-filter>
          <action android:name="android.intent.action.VIEW" />
          <category android:name="android.intent.category.DEFAULT" />
          <category android:name="android.intent.category.BROWSABLE" />
          <data
            android:scheme="io"
            android:host="saveData"
            android:pathPrefix="/upApp"
            tools:ignore="AppLinkUrlError" />
        </intent-filter>
      </activity>
      
   接收类MainActivity中通过intent接收通过协议传过来的参数
   
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...
        handleIntent(getIntent());
      }
      @Override
      protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
      }
      @SuppressLint("SetTextI18n")
      private void handleIntent(Intent intent) {
        Uri data = intent.getData();
        if (data != null && "io".equals(data.getScheme())) {
          String action = data.getHost();
          if ("saveData".equals(action)) {
            List<String> params = data.getPathSegments();
            String key = params.get(0);
            String value = params.get(1);
            CustomEventApi.getVisitorInfo(MainActivity.this,value);
          }
        }
      }
      
  将接收到的value值 调用CustomEventApi.getVisitorInfo(Context,value); 传输给依赖 方便以后 归因使用

# 归因调用 

1. 调用`DetailBody`实体类进行封装归因参数 如事件名称,货币单位,价格等
2. 调用`CustomEventApi.sendEvent(Context,DetailBody);`进行归因

       DetailBody body = new DetailBody("purchase");
       body.setDescription("web page");
       body.setPrice(1.00);
       body.setCurrency("USD");
       body.setBrand("Fancy Sneakers");
       CustomEventApi.sendEvent(MainActivity.this,body);

# Google归因
本sdk使用的Firebase来做Google归因 需要完成以下对接 
1. 在Firebase控制中心创建项目 分别添加网站应用(对应着陆页地址)和移动Android应用 
2. 如果您使用的自定义着陆页 请自行对接Firebase;如果您使用的是生成着陆页,不需要您做其他处理
3. 网站应用 将对应秘钥信息填写在后台域名管理模块
4. Android应用 下载google-services.json 文件 放在 app 目录下
5. 自定义Application页面 继承 UpAPP类 并在清单文件 application标签中引用

        public class CustomAPP extends UpApp{}
        <application
            ...
            android:name=".CustomAPP">

6. 在project的build.gradle文件中添加
    
        plugins {
             id 'com.android.application' version '7.3.0' apply false
            id("com.google.gms.google-services") version "4.3.15" apply false
        }

7. 在app的build.gradle文件中添加

        plugins {
            id 'com.android.application'
            id("com.google.gms.google-services")
        }

8. 参考归因调用模块发送事件


# Context参数

    Context: 上下文






## 注意事项

1. 确保定义的着陆页 配置的像素信息 已在后台 域名管理中添加
2. 确保着陆页域名和 后台应该管理中填写的域名一致
3. 确保w2a生成模板 着陆页url 已填写

## 如何使用

1.  在<head>中引用 platform.js 文件
2.  在 页面加载完成时调用 platform.getWebInfo() 方法
3.  在 点击 下载时 先调用 platform.installClickEvent() 方法
4.  在 Android app 应该中 请按照 开发sdk 模块引用依赖, 依赖会将该着陆页 会自动拼接包名 作为引导页再次打开 
5.  可以仿照以下例子 添加一个loading 布局 在着陆页中 
 
        <head>
           <script src="js/platform.js"></script>
           <style>
              body{
                 margin: 0;
              }
              .web{
                 width: 100vw;
                 height: 100vh;
                 color: white;
                 background-color: #000000;
                 display: flex;
                 justify-content: center;
                 align-items: center;
                 font-size: 24px;
              }
              .loader {
                 font-size: 24px;
                 font-weight: bold;
                 color: white;
                 text-shadow: 1px 1px 2px black;
                 position: relative;
                 overflow: hidden;
              }
           </style>
    
        </head>
        <body>
             ...

           <div id="webFull" class="web">
               <div class="loader">{{loader}}</div>
           </div>
         <script>
             document.addEventListener('DOMContentLoaded', async () => {
                let params = new URLSearchParams(window.location.search);
                let packageName = params.get("package")
                if (packageName){
                document.getElementById('installWrap').style.display = 'none';
                document.getElementById('webFull').style.display = 'flex';
                document.getElementById('webFull').style.backgroundColor = '#000';
                platform.changeInstallState();
             }});
           </script>
         </body>




 
