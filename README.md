# UdeskSDK Android 3.5+ 开发者文档

### 快速接入
------
   一.初始管理员后台创建应用是生成的对应app key 和 app id
   
   ``` java
      UdeskSDKManager.getInstance().initApiKey(context, "you domain","App Key","App Id");
      
      注意：域名不要带有http://部分，加入注册生成的域名是"http://udesksdk.udesk.cn/" ,只要传入"udesksdk.udesk.cn"
   ```
      
   二.设置客户的信息。
   
  ``` java   
      Map<String, String> info = new HashMap<String, String>();
      String sdkToken = "你们识别客户的唯一标识，和我们系统一一映射";
      info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, sdkToken);
      info.put(UdeskConst.UdeskUserInfo.NICK_NAME, "客户的姓名");
      UdeskSDKManager.getInstance().setUserInfo(context, sdkToken, info);
      注意sdktoken是客户的唯一标识，用来识别身份，sdk_token: 你们传入的字符请使用 只包含字母，数字的字符集。
      
  ```    
     
  三. 进入页面分配会话
  
``` java
    
      UdeskSDKManager.getInstance().entryChat(context);
      
``` 

  四. 如果应用内切换用户
``` java
     1调用
      UdeskSDKManager.getInstance().logoutUdesk;
     之后重复对应的 二和三步骤
      
``` 
      
  更多功参考demo。
  
 
------

## eclipse 版本SDK最高支持22，对应于手机系统5.0， 安卓手机5.0后的特性不在支持，如果需要请接入AndroidStudio版本。
