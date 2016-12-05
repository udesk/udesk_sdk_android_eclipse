package udesk.sdk.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.udesk.PreferenceHelper;
import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;
import cn.udesk.messagemanager.UdeskMessageManager;
import cn.udesk.model.MsgNotice;
import udesk.sdk.demo.R;

public class UdeskUseGuideActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udesk_use_guide_view);

        UdeskMessageManager.getInstance().event_OnNewMsgNotice.bind(this, "OnNewMsgNotice");

    }

    public  void onClick(View v){
        if (v.getId() == R.id.udesk_group_help){
    
            UdeskSDKManager.getInstance().toLanuchHelperAcitivty(UdeskUseGuideActivity.this);
        }else if (v.getId() == R.id.udesk_group_conversation){
    
            UdeskSDKManager.getInstance().showRobotOrConversation(UdeskUseGuideActivity.this);
        }else if (v.getId() == R.id.udesk_group_formtable){
  
            UdeskSDKManager.getInstance().goToForm(UdeskUseGuideActivity.this);
        }else  if(v.getId() == R.id.udesk_group_utils){
          
            Intent funtionIntent = new Intent();
            funtionIntent.setClass(UdeskUseGuideActivity.this, UdeskFuncationExampleActivity.class);
            startActivity(funtionIntent);
        }else if(v.getId() == R.id.udesk_group_reset){
    
            PreferenceHelper.write(UdeskUseGuideActivity.this, UdeskConst.SharePreParams.Udesk_Sharepre_Name,
                    UdeskConst.SharePreParams.Udesk_SdkToken, "");
            finish();
        }

    }

        public void OnNewMsgNotice(MsgNotice msgNotice) {
        if (msgNotice != null) {
           Log.i("ttt", msgNotice.toString());
        }

    }

}
