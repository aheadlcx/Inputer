/*
 *    Copyright 2016 Ted xiong-wei@hotmail.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.android.ted.inputer.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.android.ted.inputer.model.GlobalCache;

/**
 * Created by Ted on 2015/12/2.
 * InputDataOperator
 */
public class InputDataOperator {
    private InputDataInterface mDataInterface;

    private AccessibilityNodeInfo mFocusNodeInfo;
    private String mFocusRecord = "xiongwei";
    //private String

    public InputDataOperator(InputDataInterface dataInterface) {
        this.mDataInterface = dataInterface;
    }

    /***
     * 处理无障碍消息事件
     *
     * @param event 消息事件
     */
    public void onHandleAccessibilityEvent(AccessibilityEvent event) {
        if (isFilterApp(event)) return;
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                readTextChangeEvent(event);
                break;
            default:
                break;
        }
    }

    public void onExpand() {
        if (mFocusNodeInfo == null) return;
        if (TextUtils.isEmpty(mFocusRecord)) return;
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, mFocusRecord);
        mFocusNodeInfo.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT.getId(), arguments);
    }

    public void onRepeal(){
        if (mFocusNodeInfo == null) return;
        if (TextUtils.isEmpty(mFocusRecord)) return;
    }


    /*************************************/
    private void readTextChangeEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        }
        AccessibilityNodeInfo editView = getEditTextNodeInfo(source);
        if (null != editView && null != editView.getText()) {
            String text = editView.getText().toString();
            if (text.equals("12")) {
                mFocusNodeInfo = editView;
                mFocusRecord = "xiongwei";
                mDataInterface.onMatchPart();
            } else if (text.equalsIgnoreCase("123")) {
                mFocusNodeInfo = editView;
                mFocusRecord = "xiongwei";
                mDataInterface.onMatchAll();
            }
        }
    }

    private AccessibilityNodeInfo getEditTextNodeInfo(AccessibilityNodeInfo source) {
        AccessibilityNodeInfo current = source;
        while (true) {
            if (current == null) {
                return null;
            }
            if ("android.widget.EditText".equals(current.getClassName())) {
                return current;
            }
            AccessibilityNodeInfo oldCurrent = current;
            current = oldCurrent.getParent();
            oldCurrent.recycle();
        }
    }

    /***
     * 是否是过滤的应用
     *
     * @param event 消息事件
     * @return 是或者否
     */
    private boolean isFilterApp(AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        if (TextUtils.isEmpty(packageName)) return true;
        if (getPackageName().equals(packageName)) return true;
        if (GlobalCache.getInstance().getAppPackageNameList().contains(packageName)) return true;
        return false;
    }

    /***
     * 获取应用程序的包名
     *
     * @return 报名
     */
    private String getPackageName() {
        String packageName = ((InputAccessibilityService) mDataInterface).getPackageName();
        if (TextUtils.isEmpty(packageName))
            packageName = "com.android.ted.inputer";
        return packageName;
    }
}
