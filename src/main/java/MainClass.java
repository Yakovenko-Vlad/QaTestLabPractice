/**
 * Created by Яковенко Влад
 */
public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        Automation object = new Automation();
        object.openImagesPage();
        object.scrollPage();
        object.searchImages();
        object.setDate();
        object.openImage();
    }
}


