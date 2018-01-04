package cn.leo.frame.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by Leo on 2017/8/29.
 */

public class TextViewUtil {
    private TextView mTextView;
    private String mText;
    private String mToastText;
    private boolean result = true;

    private TextViewUtil(TextView textView) {
        mTextView = textView;
        if (mTextView == null) {
            result = false;
            return;
        }
        mText = mTextView.getText().toString();
    }


    /**
     * 建立文本检测器
     *
     * @param textView
     * @return
     */
    public static TextViewUtil check(TextView textView) {
        return new TextViewUtil(textView);
    }

    /**
     * 设置后面不匹配的Toast文本，中途可重复设置
     *
     * @param toastText
     * @return
     */
    public TextViewUtil setToastText(String toastText) {
        mToastText = toastText;
        return this;
    }

    /**
     * 检测是否为空，不去去首尾空格
     *
     * @return
     */
    public TextViewUtil notEmpty() {
        if (!result) return this;
        result = !TextUtils.isEmpty(mText);
        if (!result) {
            showToast(mToastText);
        }
        return this;
    }

    /**
     * 检测是否为空，去首尾空格
     *
     * @return
     */
    public TextViewUtil notEmptyAndSpace() {
        if (!result) return this;
        result = !TextUtils.isEmpty(mText.trim());
        if (!result) {
            showToast(mToastText);
        }
        return this;
    }

    /**
     * 检测文本长度区间
     *
     * @param min
     * @param max
     * @return
     */
    public TextViewUtil inLength(int min, int max) {
        if (!result) return this;
        result = mText.length() >= min && mText.length() <= max;
        if (!result) {
            showToast(mToastText);
        }
        return this;
    }


    /**
     * 是否是全中文
     *
     * @return
     */
    public TextViewUtil isChinese() {
        String rule = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2Da-zA-Z_]+$";
        return match(rule);
    }

    /**
     * 是否是中国的手机号码
     *
     * @return
     */
    public TextViewUtil isMobile() {
        String rule = "^[1][3456789]\\d{9}$";
        return match(rule);
    }

    /**
     * 判断是否是整数
     *
     * @return
     */
    public TextViewUtil isInteger() {
        String rule = "[-+]?[0-9]+";
        return match(rule);
    }

    /**
     * 判断是否是小数
     *
     * @return
     */
    public TextViewUtil isDecimal() {
        String rule = "[-+]?[0-9]*\\.{1}[0-9]+";
        return match(rule);
    }

    /**
     * 判断是否是数字
     *
     * @return
     */
    public TextViewUtil isNumber() {
        String rule = "[-+]?[0-9]*\\.?[0-9]+";
        return match(rule);
    }

    /**
     * 是否含有特殊字符
     *
     * @return
     */
    public TextViewUtil notHaveSpecialWord() {
        String rule = ".*[`~@#\\-+*/%=\\\\$%&^<>{}|']+.*";
        return notMatch(rule);
    }

    /**
     * 禁止输入特殊字符
     *
     * @return
     */
    public TextViewUtil inhibitingInputSpecialWord() {
        mTextView.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String rule = ".*[`~@#\\-+*/%=\\\\$%&^<>{}|']+.*";
                if (Pattern.matches(rule, source)) return "";
                return source;
            }
        }});
        return this;
    }

    /**
     * 禁止输入指定字符串内含有的字符
     *
     * @param rule
     * @return
     */
    public TextViewUtil inhibitingInputWords(final String rule) {
        mTextView.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (rule.contains(source)) return "";
                return source;
            }
        }});
        return this;
    }

    /**
     * 符合正则检测 ，通过
     *
     * @param rule
     * @return
     */
    public TextViewUtil match(String rule) {
        if (!result) return this;
        result = Pattern.matches(rule, mText);//正则匹配结果
        if (!result) {
            showToast(mToastText);
        }
        return this;
    }

    /**
     * 匹配上的返回false，表示不通过
     *
     * @param rule
     * @return
     */
    public TextViewUtil notMatch(String rule) {
        if (!result) return this;
        result = !Pattern.matches(rule, mText);
        if (!result) {
            showToast(mToastText);
        }
        return this;
    }

    /**
     * 返回最终检测结果
     *
     * @return
     */
    public boolean result() {
        return result;
    }

    /**
     * 未通过校验返回true
     *
     * @return
     */
    public boolean notPass() {
        return !result;
    }

    /**
     * 获取文本转化成整数
     *
     * @return
     */
    public int getInt() {
        boolean matches = Pattern.matches("[-+]?[0-9]+", mText);
        if (matches) {
            return Integer.parseInt(mText);
        } else {
            return 0;
        }
    }

    /**
     * 获取文本转化为浮点数
     *
     * @return
     */
    public float getFloat() {
        boolean matches = Pattern.matches("[-+]?[0-9]*\\.?[0-9]+", mText);
        if (matches) {
            return Float.parseFloat(mText);
        } else {
            return 0f;
        }
    }

    /**
     * 获取文本转化成double
     *
     * @return
     */
    public double getDouble() {
        boolean matches = Pattern.matches("[-+]?[0-9]*\\.?[0-9]+", mText);
        if (matches) {
            return Double.parseDouble(mText);
        } else {
            return 0.0;
        }
    }

    /**
     * 去除首尾空格
     *
     * @return
     */
    public TextViewUtil trim() {
        mText.trim();
        return this;
    }

    /**
     * 获取文本内容
     *
     * @return
     */
    public String getText() {
        return mText;
    }

    private void showToast(String text) {
        if (text != null && mTextView != null) {
            Toast.makeText(mTextView.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }
}
