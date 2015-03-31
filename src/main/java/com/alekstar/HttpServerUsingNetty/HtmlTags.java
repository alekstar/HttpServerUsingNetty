package com.alekstar.HttpServerUsingNetty;

public class HtmlTags {
    public static String defineHeaderBeginTag() {
        return "<h3>";
    }

    public static String defineHeaderEndTag() {
        return "</h3>";
    }

    public static String generateHeader(String header) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(defineHeaderBeginTag());
        stringBuilder.append(header);
        stringBuilder.append(defineHeaderEndTag());
        return stringBuilder.toString();
    }

    public static String defineTableBeginTag() {
        return "<table border = \"1\">";
    }

    public static String defineTableEndTag() {
        return "</table>";
    }

    public static String defineHeadOfTable(String... headers) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(defineTableRowBeginTag());
        for (String currentHeader : headers) {
            stringBuilder.append(defineTableHeaderCellBeginTag());
            stringBuilder.append(currentHeader);
            stringBuilder.append(defineTableHeaderCellEndTag());
        }
        stringBuilder.append(defineTableRowEndTag());
        return stringBuilder.toString();
    }

    public static String defineTableRowBeginTag() {
        return "<tr>";
    }

    public static String defineTableRowEndTag() {
        return "</tr>";
    }

    public static String defineTableHeaderCellBeginTag() {
        return "<th>";
    }

    public static String defineTableHeaderCellEndTag() {
        return "</th>";
    }

    public static String defineTableRow(String... values) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(defineTableRowBeginTag());
        for (String currentValue : values) {
            stringBuilder.append(defineTableCellBeginTag());
            stringBuilder.append(currentValue);
            stringBuilder.append(defineTableCellEndTag());
        }
        stringBuilder.append(defineTableRowEndTag());
        return stringBuilder.toString();
    }

    public static String defineTableCellBeginTag() {
        return "<td>";
    }

    public static String defineTableCellEndTag() {
        return "</td>";
    }
}
