#  快速开始
  在app的build.gradle文件中添加依赖

  implementation "io.upapp.io:upapp:1.0.0"
   
# 接收参数

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
          }
        }
      }
      
  将接收到的value值 做保存 方便归因的时候 传参
   
# 归因
  在入口文件或者Application中调用打开 CustomWebView 打开下载页
  
    CustomWebView.getInstance(Context,下载页地址);
    
  该页面可作为引导页使用 会自动关闭 并打开接收页 发送数据

  使用转化api 进行归因 
  
    DetailBody detailBody = new Gson().fromJson(upApp, DetailBody.class);
    detailBody.setEventName(事件名称);
    ApiMethods.sendEvent(MainActivity.this,归因平台,detailBody);
    
  参数说明 
  
    context 上下文
    
    归因平台 平台  
        "Facebook" 对应填写 "FB"  
        "TikTok"  对应填写 "TT"
        "KWai"  对应填写 "KW"
        
    DetailBody 接口参数 解析上述接收到的参数 并添加事件名称
 
