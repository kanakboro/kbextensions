package com.kbextensions;

import android.text.InputFilter;
import android.text.Spanned;


/**
 * Created By Kanak Boro 22 May, 2021
 **/

public class EmojiFilter {

    public static InputFilter[] getFilter() {

        String blockCharacterSet = "+~#^|$%&*!£,€1234567890;`•√π÷×¶∆¢¥={}\\[]<>@_-()/\"':?";
        InputFilter EMOJI_FILTER = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int index = start; index < end; index++) {
                    try {
                        int type = Character.getType(source.charAt(index));
                        if (type == Character.SURROGATE || type == Character.NON_SPACING_MARK || type == Character.OTHER_SYMBOL) {
                            return "";
                        } else if (source != null && blockCharacterSet.contains(("" + source.charAt(index)))) {
                            return ("" + source).subSequence(start, end - 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        return new InputFilter[]{EMOJI_FILTER};
    }

}
