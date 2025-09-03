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
    private String zip = "";    
    public int Zip;             

    // -------- INT constructor --------
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

    // -------- STRING constructor --------
    public ZipCode(String code) {
        
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

          StringBuilder digits = new StringBuilder();
        boolean badSeq = false;

        for (int i = 1; i + 5 <= code.length() - 1; i += 5) {  // stop before last guard
            String chunk = code.substring(i, i + 5);
            int digit;

            switch (chunk) {
                case "11000" -> digit = 0;
                case "00011" -> digit = 1;
                case "00101" -> digit = 2;
                case "00110" -> digit = 3;
                case "01001" -> digit = 4;
                case "01010" -> digit = 5;
                case "01100" -> digit = 6;
                case "10001" -> digit = 7;
                case "10010" -> digit = 8;
                case "10100" -> digit = 9;
                default -> {
                    System.out.println(chunk + " has invalid sequence in the barcode");
                    badSeq = true;
                    digit = -1;
                }
            }

            if (digit != -1) digits.append(digit);
        }

        if (!badSeq) {
            String zipStr = (digits + "00000").substring(0, 5);
            this.Zip = Integer.parseInt(zipStr);
        }
    }


    // -------- Build barcode from current Zip --------
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

    private int parseBarCode(String code) {
        int zip = 0;
        for (int i = 1; i + 5 < code.length(); i += 5) {
            String chunk = code.substring(i, i + 5);

            int digit = switch (chunk) {
                case "11000" -> 0;
                case "00011" -> 1;
                case "00101" -> 2;
                case "00110" -> 3;
                case "01001" -> 4;
                case "01010" -> 5;
                case "01100" -> 6;
                case "10001" -> 7;
                case "10010" -> 8;
                case "10100" -> 9;
                default -> -1;
            };

            if (digit == -1) {
                System.out.println(chunk + "  has invalid sequence in the bar code");
                return 0; // leave Zip = 0
            }

            zip = zip * 10 + digit;
        }

        return zip % 100000; // ensure standard 5-digit ZIP
    }
}