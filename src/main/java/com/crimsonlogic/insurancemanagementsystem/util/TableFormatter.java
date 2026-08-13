package com.crimsonlogic.insurancemanagementsystem.util;

import java.util.*;

public final class TableFormatter {
    private TableFormatter() {
    }
    public static void print(String title, String[] headers, List<String[]> rows) {
        System.out.println("\n--- " + title + " ---");
        if (rows.isEmpty()) {
            empty();
            return;
        }
        int[] widths = new int[headers.length];
        for (int i = 0; i<headers.length; i++) widths[i] = headers[i].length();
        for (String[] row:rows) for (int i = 0; i<headers.length; i++) widths[i] = Math.max(widths[i], row[i] == null?0:row[i].length());
        StringBuilder separator = new StringBuilder("+");
        for (int width:widths) separator.append("-".repeat(width+2)).append("+");
        System.out.println(separator);
        row(headers, widths);
        System.out.println(separator);
        for (String[] data:rows) row(data, widths);
        System.out.println(separator);
    }
    private static void row(String[] data, int[] widths) {
        StringBuilder b = new StringBuilder("|");
        for (int i = 0; i<widths.length; i++)b.append(" ").append(String.format("%-"+widths[i]+"s", data[i] == null?"":data[i])).append(" |");
        System.out.println(b);
    }
    public static void empty() {
        System.out.println("No records found.");
    }
}
