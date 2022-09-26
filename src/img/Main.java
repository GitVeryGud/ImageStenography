/*
   Team Members:
   - Guilherme de Souza Barci
   - Samuel Santos Ferraz
   - Enzo José Stella Costa
 */

package img;
import static img.Steganography.*;


@SuppressWarnings("SpellCheckingInspection")
public class Main {
    public static void main(String[] args){
        /* Measurements for each variance using the given template
        (the important thing is the image having the exact same
        width as below, since the code will fill the blank space with the last color used you
        have some leeway with the height)

        Template :1920x1080
        Variance 1:720x360
        Variance 2:1018x509
        Variance 3:1246x623
        Variance 4:1440x720
        Variance 5:1608x804
        Variance 6:1762x881
        Variance 7:1904x952
        Variance 8:2036x1018

        To know the different measurements for different templates use
        findMeasurements(int templateWidth, int templateHeight)
*/

        var image = read("./img/template.png");
        var encode = read("./img/Variance1.png");
        var encodeHighVariance = read("./img/Variance4.png");
        var decryptPreEncoded = read("./img/PreEncodedImage.png");
        var encodeMaxVariance = read("./img/Variance8.png");

        // Encodes and decrypts a variance 1 image, also decrypts an unencoded image to show the result when
        // there is no hidden information
        save(encodeImage(image, encode, 1), "imageWithEncoding");
        var decrypt = read("./imageWithEncoding.png");
        save(decryptImage(decrypt, 1), "DecryptedImage");
        save(decryptImage(image, 1), "DecryptedImageWithoutEncoding");

        // Encodes and decrypts a variance 4 image, showing the higher resolution allowed for encoding
        // at the cost of artifacts on the template image
        save(encodeImage(image, encodeHighVariance, 4), "imageWithEncodingHighVariance");
        var decryptHighVariance = read("./imageWithEncodingHighVariance.png");
        save(decryptImage(decryptHighVariance, 4), "DecryptedImageHighVariance");

        // Decrypts a pre-encoded image to show that the information remains between transfers
        save(decryptImage(decryptPreEncoded, 2), "DecryptedPreEncodedImage");

    }
}
