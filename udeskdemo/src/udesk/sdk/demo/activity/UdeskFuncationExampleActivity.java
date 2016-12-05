package udesk.sdk.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdekConfigUtil;
import cn.udesk.config.UdeskConfig;
import cn.udesk.model.UdeskCommodityItem;
import cn.udesk.widget.UdeskTitleBar;
import udesk.core.model.MessageInfo;
import udesk.sdk.demo.R;

public class UdeskFuncationExampleActivity extends Activity {

    private UdeskTitleBar mTitlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udesk_funcation_example_view);
        settingTitlebar();
    }

    private void settingTitlebar() {
        mTitlebar = (UdeskTitleBar) findViewById(cn.udesk.R.id.udesktitlebar);
        if (mTitlebar != null) {
            mTitlebar.setLeftTextSequence(getString(R.string.udesk_utils_tips));
            mTitlebar.setLeftLinearVis(View.VISIBLE);
            mTitlebar.setLeftViewClick(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            if (UdeskConfig.DEFAULT != UdeskConfig.udeskbackArrowIconResId) {
                mTitlebar.getUdeskBackImg().setImageResource(UdeskConfig.udeskbackArrowIconResId);
            }
            UdekConfigUtil.setUITextColor(UdeskConfig.udeskTitlebarTextLeftRightResId,mTitlebar.getLeftTextView(),mTitlebar.getRightTextView());
            UdekConfigUtil.setUIbgDrawable(UdeskConfig.udeskTitlebarBgResId ,mTitlebar.getRootView());
        }
    }

    public  void onClick(View v){
        if (v.getId() == R.id.udesk_by_agentid){
 
            final CustomDialog dialog = new CustomDialog(UdeskFuncationExampleActivity.this);
            dialog.setDialogTitle(getString(R.string.udesk_by_agentid));
            final EditText editText = (EditText) dialog.getEditText();
            editText.setHint(getString(R.string.udesk_agentid));
            dialog.setOkTextViewOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), getString(R.string.udesk_agentid_empty_show), Toast.LENGTH_LONG).show();
                        return;
                    }
                    UdeskSDKManager.getInstance().lanuchChatByAgentId(UdeskFuncationExampleActivity.this, editText.getText().toString().trim());

                }
            });
            dialog.setCancleTextViewOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else if (v.getId() == R.id.udesk_by_groupid){
  
            final CustomDialog dialog = new CustomDialog(UdeskFuncationExampleActivity.this);
            dialog.setDialogTitle(getString(R.string.udesk_by_groupid));
            final EditText editText = (EditText) dialog.getEditText();
            editText.setHint(getString(R.string.udesk_groupid));
            dialog.setOkTextViewOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), getString(R.string.udesk_groupid_empty_show), Toast.LENGTH_LONG).show();
                        return;
                    }
                    UdeskSDKManager.getInstance().lanuchChatByGroupId(UdeskFuncationExampleActivity.this, editText.getText().toString().trim());

                }
            });
            dialog.setCancleTextViewOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else if (v.getId() == R.id.udesk_unread_msg){
        
            List<MessageInfo> unReadMsgs =  UdeskSDKManager.getInstance().getUnReadMessages();
            if (unReadMsgs == null || unReadMsgs.isEmpty()){
                Toast.makeText(UdeskFuncationExampleActivity.this,getString(R.string.udesk_no_unreadmsg),Toast.LENGTH_SHORT).show();
                return;
            }
            final CustomDialog dialog = new CustomDialog(UdeskFuncationExampleActivity.this);
            dialog.setDialogTitle(getString(R.string.udesk_unreadmsg));
            ListView mListview =  dialog.getListView();
            UnRedMsgAdapter msgAdapter = new UnRedMsgAdapter(UdeskFuncationExampleActivity.this);
            mListview.setAdapter(msgAdapter);
            msgAdapter.setList(unReadMsgs);
            dialog.setOkTextViewOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.setCancleTextViewOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else  if(v.getId() == R.id.udesk_unread_msgcount){

            int unreadMsg = UdeskSDKManager.getInstance().getCurrentConnectUnReadMsgCount();
            final CustomDialog dialog = new CustomDialog(UdeskFuncationExampleActivity.this);
            dialog.setDialogTitle(getString(R.string.udesk_unreadmsg_count));
            final TextView text = (TextView) dialog.getcontentText();
            text.setText(String.valueOf(unreadMsg));
            dialog.setOkTextViewOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.setCancleTextViewOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else if(v.getId() == R.id.udesk_update_userinfo){
 
            Intent intent = new Intent();
            intent.setClass(UdeskFuncationExampleActivity.this,UpdateUserInfoActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.udesk_update_ui){
     
            final CustomDialog dialog = new CustomDialog(UdeskFuncationExampleActivity.this);
            dialog.setDialogTitle(getString(R.string.udesk_update_ui));
            View view1 = dialog.getViewStyle1();
            View view2 = dialog.getViewStyle2();
         
            final CheckBox checkBox1 = dialog.getStyle1Checkbox();
            final CheckBox checkBox2 = dialog.getStyle2Checkbox();
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBox2.setChecked(false);
                    if (checkBox1.isChecked()){
                        checkBox1.setChecked(false);
                    }else {
                        checkBox1.setChecked(true);
                    }
                }
            });

            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBox1.setChecked(false);
                    if (checkBox2.isChecked()){
                        checkBox2.setChecked(false);
                    }else {
                        checkBox2.setChecked(true);
                    }
                }
            });

            dialog.setOkTextViewOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBox1.isChecked()){
                        UIStyle1();
                    }else if(checkBox2.isChecked()){
                        UIStyle2();
                    }
                    dialog.dismiss();
                }
            });
            dialog.setCancleTextViewOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else if(v.getId() == R.id.udesk_conversion_bysetting_menu){

            UdeskSDKManager.getInstance().showConversationByImGroup(UdeskFuncationExampleActivity.this);
        }else if(v.getId() == R.id.udesk_send_commodity_link){

            createCommodity();
        }

    }


    private void createCommodity() {
        UdeskCommodityItem item = new UdeskCommodityItem();
        item.setTitle("title");
        item.setSubTitle("99.00");
        item.setThumbHttpUrl("https://img.alicdn.com/imgextra/i1/1728293990/TB2ngm0qFXXXXcOXXXXXXXXXXXX_!!1728293990.jpg_430x430q90.jpg");
        item.setCommodityUrl("https://detail.tmall.com/item.htm?spm=a1z10.3746-b.w4946-14396547293.1.4PUcgZ&id=529634221064&sku_properties=-1:-1");
        UdeskSDKManager.getInstance().setCommodity(item);
        UdeskSDKManager.getInstance().toLanuchChatAcitvity(UdeskFuncationExampleActivity.this);
    }

    private void UIStyle1(){
        UdeskConfig.udeskTitlebarBgResId = R.color.udesk_titlebar_bg1;
        UdeskConfig.udeskTitlebarTextLeftRightResId = R.color.udesk_color_navi_text1;
        UdeskConfig.udeskIMRightTextColorResId = R.color.udesk_color_im_text_right1;
        UdeskConfig.udeskIMLeftTextColorResId = R.color.udesk_color_im_text_left1;
        UdeskConfig.udeskIMAgentNickNameColorResId = R.color.udesk_color_im_left_nickname1;
        UdeskConfig.udeskIMTimeTextColorResId = R.color.udesk_color_im_time_text1;
        UdeskConfig.udeskIMTipTextColorResId = R.color.udesk_color_im_tip_text1;
        UdeskConfig.udeskbackArrowIconResId = R.drawable.udesk_titlebar_back;
        UdeskConfig.udeskCommityBgResId = R.color.udesk_color_im_commondity_bg1;
        UdeskConfig.udeskCommityTitleColorResId = R.color.udesk_color_im_commondity_title1;
        UdeskConfig.udeskCommitysubtitleColorResId = R.color.udesk_color_im_commondity_subtitle1;
        UdeskConfig.udeskCommityLinkColorResId = R.color.udesk_color_im_commondity_title1;
    }

    private void UIStyle2(){
        UdeskConfig.udeskTitlebarBgResId = R.color.udesk_titlebar_bg2;
        UdeskConfig.udeskTitlebarTextLeftRightResId = R.color.udesk_color_navi_text2;
        UdeskConfig.udeskIMRightTextColorResId = R.color.udesk_color_im_text_right2;
        UdeskConfig.udeskIMLeftTextColorResId = R.color.udesk_color_im_text_left2;
        UdeskConfig.udeskIMAgentNickNameColorResId = R.color.udesk_color_im_left_nickname2;
        UdeskConfig.udeskIMTimeTextColorResId = R.color.udesk_color_im_time_text2;
        UdeskConfig.udeskIMTipTextColorResId = R.color.udesk_color_im_tip_text2;
        UdeskConfig.udeskbackArrowIconResId = R.drawable.udesk_titlebar_back;

        UdeskConfig.udeskCommityBgResId = R.color.udesk_color_im_commondity_bg2;
        UdeskConfig.udeskCommityTitleColorResId = R.color.udesk_color_im_commondity_title2;
        UdeskConfig.udeskCommitysubtitleColorResId = R.color.udesk_color_im_commondity_subtitle2;
        UdeskConfig.udeskCommityLinkColorResId = R.color.udesk_color_im_commondity_title2;
    }
}
