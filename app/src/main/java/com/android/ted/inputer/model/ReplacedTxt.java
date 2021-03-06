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

package com.android.ted.inputer.model;

/**
 * Created by Ted on 2015/12/10.
 * 被替换掉的文本对象
 */
public class ReplacedTxt {
    //被替换的文字内容
    private String replacedTxt;
    //变形之前光标位置
    private int startPos;

    public String getReplacedTxt() {
        return replacedTxt;
    }

    public void setReplacedTxt(String replacedTxt) {
        this.replacedTxt = replacedTxt;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }
}
