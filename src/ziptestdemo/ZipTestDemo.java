/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ziptestdemo;

/**
 *
 * @author Lenny Manset
 */
public class ZipTestDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Required Output for Zipcode Testing");

        ZipCode zip1 = new ZipCode(995047),
                        zip2 = new ZipCode("100101010011100001100110001"),
                        zip3 = new ZipCode(12345),
                        zip4 = new ZipCode(67890);


        System.out.printf("%5d 's bar code is %s", zip1.Zip , zip1.GetBarCode());
        System.out.printf("%n %5d 's bar code is %s", zip2.Zip, zip2.GetBarCode());
        System.out.printf("%n %5d 's bar code is %s", zip3.Zip ,zip3.GetBarCode());
        System.out.printf("%n %5d 's bar code is %s%n", zip4.Zip, zip4.GetBarCode());

        // Test a range of values by first constructing a zip code with
        // an integer, then retrieving the bar code and using that to
        // construct another ZipCode.
        int zip_int = 0;
        for (int i = 0; i < 25; i++)
        {
            // Make an aribrary 5-digit zip code integer, and use it
            // to construct a ZipCode
            int five_digit_zip = (zip_int * zip_int) % 100000;
            ZipCode z1 = new ZipCode(five_digit_zip);

            // Construct a next ZipCode from the current one's bar code
            String z1_code = z1.GetBarCode();
            ZipCode z2 = new ZipCode(z1_code);
             System.out.printf("  %02d  %05d has code %s " ,  i + 1 , z2.Zip,z1_code);
            if ((z1_code.equals(z2.GetBarCode())) &&
                            (z1.Zip == z2.Zip) &&
                            (z2.Zip == five_digit_zip))
            {
                 System.out.println(" [OK]");
            }
            else
            {
                System.out.println(z2.Zip +" "+five_digit_zip +" [ERR]");
            }

            // Increment the test value arbitrarily
            zip_int += (233 + zip_int % 7);
        }  // end for

        System.out.println();
        // Test some error conditions. This test assumes that
        // ZipCode will simply print an error message to Console
        //  and will not exit the program.
         System.out.println("\nInitialize an array of bar codes with known errors:");
        int BAD_ZIP_COUNT = 5;
        // Use 2-D array of strings for tests.
        String[ ][] badZips = {
        { "10010101001110000110011001", "bad length" },
        { "000101010011100001100110001", "bad start/end character" },
        { "100101010011100001100110000", "bad start/end character" },
        { "100104010011100001100110021","bad digit" },
        { "100101010011100001100111001","bad sequence" }
        };
        for (int i = 0; i < BAD_ZIP_COUNT; i++)
        {
           
        System.out.printf ("Testing %s for %s %n" , badZips[i][0], badZips[i][1]);
            // Construct ZipCode object and it will detect errors
            ZipCode testZip = new ZipCode(badZips[i][ 0]);
           System.out.println();
        }
    }  //************** END OF main() ************    }
}
