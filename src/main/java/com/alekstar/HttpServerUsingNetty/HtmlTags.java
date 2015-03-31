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

    public static String defineDocumentTypeTag() {
        return "<!DOCTYPE HTML>";
    }

    public static String defineHeadBeginTag() {
        return "<head>";
    }

    public static String defineHeadEndTag() {
        return "</head>";
    }

    public static String defineDefaultMetaTag() {
        return "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">";
    }

    public static String defineTitleBeginTag() {
        return "<title>";
    }

    public static String defineTitleEndTag() {
        return "</title>";
    }

    public static String defineTitle(String title) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HtmlTags.defineTitleBeginTag());
        if (title == null) {
            stringBuilder.append("");
        } else {
            stringBuilder.append(title);
        }
        stringBuilder.append(HtmlTags.defineTitleEndTag());
        return stringBuilder.toString();
    }

    public static String defineBodyBeginTag() {
        return "<body>";
    }

    public static String defineBodyEndTag() {
        return "</body>";
    }

    public static String defineHtmlBeginTag() {
        return "<html>";
    }

    public static String defineHtmlEndTag() {
        return "</html>";
    }

    public static String defineBreakLineTag() {
        return "<br>";
    }
}
