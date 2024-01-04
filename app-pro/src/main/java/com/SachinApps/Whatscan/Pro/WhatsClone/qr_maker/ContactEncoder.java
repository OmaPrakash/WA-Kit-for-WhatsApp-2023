package com.SachinApps.Whatscan.Pro.WhatsClone.qr_maker;


import android.telephony.PhoneNumberUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
abstract class ContactEncoder {

    abstract String[] encode(List<String> names,
                             String organization,
                             List<String> addresses,
                             List<String> phones,
                             List<String> phoneTypes,
                             List<String> emails,
                             List<String> urls,
                             String note);

    static String trim(String s) {
        if (s == null) {
            return null;
        }
        String result = s.trim();
        return result.isEmpty() ? null : result;
    }

    static void append(StringBuilder newContents,
                       StringBuilder newDisplayContents,
                       String prefix,
                       String value,
                       Formatter fieldFormatter,
                       char terminator) {
        String trimmed = trim(value);
        if (trimmed != null) {
            newContents.append(prefix).append(fieldFormatter.format(trimmed, 0)).append(terminator);
            newDisplayContents.append(trimmed).append('\n');
        }
    }

    static void appendUpToUnique(StringBuilder newContents,
                                 StringBuilder newDisplayContents,
                                 String prefix,
                                 List<String> values,
                                 int max,
                                 Formatter displayFormatter,
                                 Formatter fieldFormatter,
                                 char terminator) {
        if (values == null) {
            return;
        }
        int count = 0;
        Collection<String> uniques = new HashSet<>(2);
        for (int i = 0; i < values.size(); i++) {
            String value = values.get(i);
            String trimmed = trim(value);
            if (trimmed != null && !trimmed.isEmpty() && !uniques.contains(trimmed)) {
                newContents.append(prefix).append(fieldFormatter.format(trimmed, i)).append(terminator);
                CharSequence display = displayFormatter == null ? trimmed : displayFormatter.format(trimmed, i);
                newDisplayContents.append(display).append('\n');
                if (++count == max) {
                    break;
                }
                uniques.add(trimmed);
            }
        }
    }

    @SuppressWarnings("deprecation")
    static String formatPhone(String phoneData) {
        // Just collect the call to a deprecated method in one place
        return PhoneNumberUtils.formatNumber(phoneData);
    }

}