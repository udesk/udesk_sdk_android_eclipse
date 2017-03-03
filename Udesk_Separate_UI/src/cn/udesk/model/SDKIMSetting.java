package cn.udesk.model;

/**
 * Created by user on 2016/12/27.
 */

public class SDKIMSetting {

//    {"code"=>"1000",
//            "message"=>"success",
//            "result"=>
//        {"enable_im_group"=>false,
//                "im_session_closed"=>false,
//                "is_worktime"=>true,
//                "has_robot"=>true,
//                "enable_robot"=>false,
//                "enable_sdk_robot"=>false,
//                "enable_agent"=>false,
//                "enable_web_im_feedback"=>false,
//                "no_reply_hint"=>"对不起，当前无客户在线"}}
    private int code;
    private Object message;

    //是否配置了引导页
    private boolean enable_im_group;
    //会话是否关闭
    private boolean in_session;
    //是否在工作时间
    private boolean is_worktime;
    private boolean has_robot;
    private boolean enable_robot;
    private boolean enable_sdk_robot;
    private boolean enable_agent;
    private boolean enable_web_im_feedback;
    private Object no_reply_hint;
    private Object robot;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public boolean getEnable_im_group() {
      
        return enable_im_group;
    }

    public void setEnable_im_group(boolean enable_im_group) {
        this.enable_im_group = enable_im_group;
    }

    public boolean getIn_session() {
      
        return in_session;

    }

    public void setIn_session(boolean in_session) {
        this.in_session = in_session;
    }

    public boolean getIs_worktime() {
       
        return is_worktime;
    }

    public void setIs_worktime(boolean is_worktime) {
        this.is_worktime = is_worktime;
    }

    public boolean getHas_robot() {
       
        return has_robot;

    }

    public void setHas_robot(boolean has_robot) {
        this.has_robot = has_robot;
    }

    public boolean getEnable_robot() {
       
        return enable_robot;
    }

    public void setEnable_robot(boolean enable_robot) {
        this.enable_robot = enable_robot;
    }

    public boolean getEnable_sdk_robot() {
       
        return enable_sdk_robot;
    }

    public void setEnable_sdk_robot(boolean enable_sdk_robot) {
        this.enable_sdk_robot = enable_sdk_robot;
    }

    public String getEnable_agent() {
    	 if (enable_agent){
             return  "true";
         }else{
             return  "false";
         }
    }

    public void setEnable_agent(boolean enable_agent) {
        this.enable_agent = enable_agent;
    }

    public boolean getEnable_web_im_feedback() {
     
        return enable_web_im_feedback;
    }

    public void setEnable_web_im_feedback(boolean enable_web_im_feedback) {
        this.enable_web_im_feedback = enable_web_im_feedback;
    }

    public String getNo_reply_hint() {
        if (no_reply_hint instanceof  String){
            return (String) no_reply_hint;
        }
        return "";
    }

    public void setNo_reply_hint(Object no_reply_hint) {
        this.no_reply_hint = no_reply_hint;
    }

    public String getRobot() {
//        return robot;
        if (robot instanceof  String){
            return (String) robot;
        }
        return "";
    }

    public void setRobot(Object robot) {
        this.robot = robot;
    }
}
