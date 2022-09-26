package img;

import img.stack.StaticStack;
import img.queue.ArrayQueue;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Steganography {
    public static void save(BufferedImage img, String name) {
        try {
            String format = "png";
            if (name.endsWith(".png")) format = "png";
            else if (name.endsWith(".jpg") || name.endsWith(".jpeg")) format = "jpg";
            else if (name.endsWith(".bmp")) format = "bmp";
            else if (name.endsWith(".wbmp")) format = "wbmp";
            else if (name.endsWith(".gif")) format = "gif";
            else name += ".png";

            ImageIO.write(img, format, new File(name));
            System.out.printf("%s saved!%n", name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage read(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Converts base-2 to base-10
    private static int binaryToInt (StaticStack<Integer> binary)
    {
        int value = 0;
        for (int i = 0; i < binary.getCapacity(); i++)
        {
            value = (int) (value + binary.remove()*Math.pow(2, i));
        }
        return value;
    }

    // Converts base-10 to base-2
    private static StaticStack<Integer> intToBinary(int num)
    {
        // Size is 8 because it will only need 8 bits for individual RGB values -> 256 or 2⁸ for each component
        var binary = new StaticStack<Integer>(8);

        while (num > 0) {
            binary.add(num % 2);
            // Since int division "rounds" down, the number will get progressively smaller until reaching 0
            num = num / 2;
        }

        int n = binary.getSize();

        // Fill the "empty space" with zeros to ensure the right length
        for (int i = 0; i < 8 - n; i++)
        {
            binary.add(0);
        }

        return binary;
    }

    // Gets the binary value of every pixel in an image and puts it inside a queue
    private static ArrayQueue<StaticStack<Integer>> imageToBinary(BufferedImage img)
    {
        var binaryQueue = new ArrayQueue<StaticStack<Integer>>();

        for (var y = 0; y < img.getHeight(); y++){
            for (var x = 0; x < img.getWidth(); x++) {
                var color = new Color (img.getRGB(x,y));
                binaryQueue.add(intToBinary(color.getRed()));
                binaryQueue.add(intToBinary(color.getGreen()));
                binaryQueue.add(intToBinary(color.getBlue()));
                }
            }
        return binaryQueue;
    }

    // For debugging purposes
    private static void printBinary(StaticStack<Integer> binary)
    {
        // Prints the whole stack
        while (!binary.isEmpty())
            System.out.print(binary.remove() + "");
        System.out.println();
    }

    private static int colorCycler(Color color, int i)
    {
        if (i == 0)
        {
            return color.getRed();
        }

        else if (i == 1)
        {
            return color.getGreen();
        }

        else if (i == 2)
        {
            return color.getBlue();
        }

        return -1;
    }

    public static int clamp(int v, int min, int max) {
        return v < min ? min : (v >= max ? max : v);
    }

    public static BufferedImage encodeImage(BufferedImage template, BufferedImage imageToEncode, int variance){
        var out = new BufferedImage(template.getWidth(), template.getHeight(),BufferedImage.TYPE_INT_RGB);
        // Queue containing all the pixels binaries for each RGB component;
        var encodedQueue = imageToBinary(imageToEncode);
        var currentComponentToEncode = new StaticStack<Integer>(1);
        // Stack and int are used to transport the out colors to the "NewColor" variable
        var newColorStack = new StaticStack<Integer>(3);
        int red = 0, green = 0, blue = 0;

        // Goes through every pixel in the image
        for (var y = 0; y < template.getHeight(); y++){
            for (var x = 0; x < template.getWidth(); x++){
                var color1 = new Color (template.getRGB(x,y));
                var newColor = color1;
                if (!encodedQueue.isEmpty()){
                    // Encodes binary
                    for(int colorNumber = 0; colorNumber<3; colorNumber++) {
                        int color = colorCycler(color1, colorNumber);
                        int result = color;

                        /*
                        variance is the amount of change that will happen to the template image.
                         If the number is low the change will be negligible, but you will have a lower
                         end resolution to encode, if the variance is high the change will be more
                         noticeable, but you will have a higher resolution to encode
                         */
                        for (int i = 0; i < variance; i++) {
                            // Goes to the next binary if the previous one ended
                            if(currentComponentToEncode.isEmpty()){
                                currentComponentToEncode = encodedQueue.remove();
                            }

                            // Gets current bit value
                            int bit = currentComponentToEncode.remove();

                            // bit = 1 / inserts a 1 if there isn't one in that spot
                            if (bit == 1) {
                                result = result | (int) Math.pow(2, i);
                            }

                            // bit = 0 / inserts a 0 if there isn't one in that spot
                            else {
                                if ((color >> i & 1) == 1) {
                                    result = result ^ (int) Math.pow(2, i);
                                }
                            }
                        }
                        newColorStack.add(result);
                    }
                    blue = newColorStack.remove();
                    green = newColorStack.remove();
                    red = newColorStack.remove();
                    newColor = new Color(red, green, blue);
                }

                out.setRGB(x,y, newColor.getRGB());
                }
            }
        return out;
        }

    public static BufferedImage decryptImage(BufferedImage encodedImage, int variance){
        // This whole interaction forces the final image to retain a 2:1 proportion between the width
        // and the height while still keeping the pixel count constant
        int width = (encodedImage.getWidth())*3*variance/8;
        int height = (encodedImage.getHeight())/3;
        height = (int) Math.sqrt(width * height/2);
        width = 2*height;

        var out = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        var binaryQueue = new ArrayQueue<StaticStack<Integer>>();
        var binary = new StaticStack<Integer>(8);
        int binaryIndex = -1;

        for (var y = 0; y < encodedImage.getHeight(); y++){
            for (var x = 0; x < encodedImage.getWidth(); x++){
                var color = new Color (encodedImage.getRGB(x,y));
                for (int colorNumber = 0; colorNumber < 3; colorNumber++) {
                    for (int i = 0; i < variance; i++) {
                        // Stops the stack from overflowing
                        if (binaryIndex == 8 || binaryIndex == -1){
                            if (binaryIndex != -1){
                                binaryQueue.add(binary);}
                            binary = new StaticStack<Integer>(8);
                            binaryIndex = 0;
                        }

                        // == 1
                        if ((colorCycler(color, colorNumber) >> i & 1) == 1) {
                            binary.add(1);
                        }
                        // == 0
                        else {
                            binary.add(0);
                        }
                        binaryIndex++;
                    }
                }
            }
        }

        int red = 0, green = 0, blue = 0;
        var newColorStack = new StaticStack<Integer>(3);

        for (var y = 0; y < height; y++){
            for (var x = 0; x < width; x++){
                for (int i = 0; i < 3; i++) {
                    if (binaryQueue.isEmpty()){break;}
                    newColorStack.add(binaryToInt(binaryQueue.remove()));
                }

                // Means that if there isn't any more info to decode it just fills rest of the image with
                // the last sampled color
                if (newColorStack.getSize() == 3)
                {
                blue = newColorStack.remove();
                green = newColorStack.remove();
                red = newColorStack.remove();
                }

                var newColor = new Color(red, green, blue);
                out.setRGB(x,y, newColor.getRGB());
            }
        }
    return out;
    }

    public static void findMeasurements(int templateWidth, int templateHeight) {
        for (int i = 1; i <= 8; i++) {
            int width = (templateWidth) * 3 * i / 8;
            int height = (templateHeight) / 3;
            height = (int) Math.sqrt(width * height / 2);
            width = 2 * height;
            System.out.println("Variance " + i + ":" + width + "x" + height);
        }
    }
}


