/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ziptestdemo;

/**
 *
 * @author Lenny Manset
 */
class ZipCode {
    private String zip = "";    // used to build the barcode
    public int Zip;             // main class expects this public field

    // -------- INT constructor (keep simple; pad/trim; print error if >5) --------
    public ZipCode(int zipcode) {
        String s = String.valueOf(zipcode);

        // pad with leading zeros if less than 5 digits
        while (s.length() < 5) {
            s = "0" + s;
        }

        // if more than 5 digits, print error and keep last 5 for usability
        if (s.length() > 5) {
            System.out.println("Your zipcode is too long (5 digits max)");
            s = s.substring(s.length() - 5);
        }

        this.Zip = Integer.parseInt(s);
    }

    // -------- STRING constructor (add all the safeguards) --------
    public ZipCode(String code) {
        this.Zip = 0; // default unless fully valid & decoded

        if (code == null || code.length() < 2) {
            System.out.println("Error: bar code must be in multiples of 5-binary digits");
            return;
        }

        // 1) Length check: inner (without guards) must be multiple of 5
        if ((code.length() - 2) % 5 != 0) {
            System.out.println("Error: bar code must be in multiples of 5-binary digits");
        }

        // 2) Guard bars check (must both be '1')
        if (code.charAt(0) != '1' || code.charAt(code.length() - 1) != '1') {
            System.out.println("Error: bar code missing a 1 at start or end");
        }

        // 3) Character validation: only '0' or '1'
        boolean badChar = false;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if (c != '0' && c != '1') {
                System.out.println("bar code character: " + c + " must be '0' or '1'");
                badChar = true;
            }
        }

        // If any of the above issues exist, stop before decoding
        if ((code.length() - 2) % 5 != 0 || code.charAt(0) != '1' || code.charAt(code.length() - 1) != '1' || badChar) {
            return;
        }

        // 4) Decode groups of 5 between guards; print "invalid sequence" if any bad group
        String inner = code.substring(1, code.length() - 1);
        int groups = inner.length() / 5;

        StringBuilder digits = new StringBuilder();
        boolean badSeq = false;
        for (int g = 0; g < groups; g++) {
            String chunk = inner.substring(g * 5, g * 5 + 5);
            String mapped = mapDigitPattern(chunk);
            if (mapped == null) {
                // EXACT phrasing with two spaces before "has"
                System.out.println(chunk + "  has invalid sequence in the bar code");
                badSeq = true;
            } else {
                digits.append(mapped);
            }
        }
        if (badSeq) {
            return; // leave Zip = 0
        }

        // Use first 5 decoded digits (standard ZIP)
        String zipStr = digits.toString();
        if (zipStr.length() >= 5) {
            this.Zip = Integer.parseInt(zipStr.substring(0, 5));
        } else {
            // defensive fallback
            while (zipStr.length() < 5) zipStr = "0" + zipStr;
            this.Zip = Integer.parseInt(zipStr);
        }
    }

    // -------- Build barcode from current Zip (simple switch logic kept) --------
    public String GetBarCode() {
        String s = String.format("%05d", this.Zip); // always 5 digits
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '0' -> sb.append("11000");
                case '1' -> sb.append("00011");
                case '2' -> sb.append("00101");
                case '3' -> sb.append("00110");
                case '4' -> sb.append("01001");
                case '5' -> sb.append("01010");
                case '6' -> sb.append("01100");
                case '7' -> sb.append("10001");
                case '8' -> sb.append("10010");
                case '9' -> sb.append("10100");
                default -> { return "invalid input, pick a number 0-9"; }
            }
        }
        return "1" + sb.toString() + "1";
    }

    // -------- Kept simple: return Zip; decoding is done in the String constructor --------
    private int parseBarCode() {
        return this.Zip;
    }

    // -------- Minimal helper for decoding each 5-bit chunk --------
    private String mapDigitPattern(String chunk) {
        return switch (chunk) {
            case "11000" -> "0";
            case "00011" -> "1";
            case "00101" -> "2";
            case "00110" -> "3";
            case "01001" -> "4";
            case "01010" -> "5";
            case "01100" -> "6";
            case "10001" -> "7";
            case "10010" -> "8";
            case "10100" -> "9";
            default -> null; // triggers "invalid sequence" message
        };
    }
}