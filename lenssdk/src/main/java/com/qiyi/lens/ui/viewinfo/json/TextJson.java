package com.qiyi.lens.ui.viewinfo.json;

import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.CallSuper;

import com.qiyi.lens.utils.LensConfig;
import com.qiyi.lens.utils.iface.IUIVerifyFactory;

import org.json.JSONArray;

public class TextJson extends ViewJson {

    public TextJson(IUIVerifyFactory factory, View view, Rect rect) {
        super(factory, view, rect);
        mType = "text";
    }

    @Override
    public String toJson() {
        JsonCompiler compiler = new JsonCompiler();
        compileJson(compiler);

        return compiler.value();
    }

    @CallSuper
    protected void compileJson(JsonCompiler compiler) {
        super.compileJson(compiler);
        TextView textView = (TextView) mView;

        JsonCompiler font = new JsonCompiler();
        font.addPair("fontSize", textView.getTextSize());
        font.addPair("fontColor", textView.getCurrentTextColor());
        font.addPair("lineHeight", textView.getLineHeight());
        int lineCount = textView.getLineCount();
        if (lineCount > 1) {
            font.addPair("lineCount", textView.getLineCount());
        }
        font.addPair("lineSpacing", getLineSpacing(textView));
//        font.addPair("fontFace", textView.getTypeface().toString());
        compiler.addPair("font", font);
        Layout layout = textView.getLayout();
        if (layout != null) {
            JsonCompiler textFrame = new JsonCompiler();
            textFrame.addPair("width", layout.getWidth());
            textFrame.addPair("height", layout.getHeight() + layout.getTopPadding() - layout.getBottomPadding() - textView.getPaddingTop()
                    - textView.getPaddingBottom());
            int paddingTop = textView.getPaddingTop() - layout.getTopPadding();
            if (paddingTop != 0) {
                textFrame.addPair("x", textView.getPaddingLeft());
                textFrame.addPair("y", paddingTop);
            }
            compiler.addPair("intrinsicSize", textFrame);
        }
    }

    /**
     * @return 如果是多行的话，返回文字的行间距
     */
    private float getLineSpacing(TextView textView) {
        return textView.getLineHeight() - textView.getTextSize();
    }
}
