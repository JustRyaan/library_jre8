import java.io.*;
import java.util.HashMap;

public interface fileRW {

    // Writes files
    static void write(HashMap map, String path, String name) {
        String currentDir = System.getProperty("user.dir");
        File directory = new File(currentDir + path);
        if(!directory.exists()){
            if(directory.mkdirs()){
                System.out.println("Created directory " + directory.getAbsolutePath());
            } else {
                System.out.println("\nERROR: Could not create directory " + directory.getAbsolutePath());
                System.exit(-1);
            }
        }
        File file = new File(directory + "/" + name + ".ser");
        try {
            FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(map);
            out.close();
            fos.close();
            System.out.println("Successfully wrote " + file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\nERROR: Could not write file " + file);
            System.exit(-2);
        }
    }

    // Reads files
    static HashMap read(String path, String name) {
        String currentDir = System.getProperty("user.dir");
        File directory = new File(currentDir + path);
        if(!directory.exists()){
            if(directory.mkdirs()){
                System.out.println("Created directory " + directory.getAbsolutePath());
            } else {
                System.out.println("\nERROR: Could not create directory " + directory.getAbsolutePath());
                System.exit(-1);
            }
        }
        File file = new File(directory + "/" + name + ".ser");
        HashMap map = new HashMap();
        if (file.exists()){
            try {
                FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
                ObjectInputStream in = new ObjectInputStream(fis);
                map = (HashMap) in.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
                System.out.println("\nERROR: Could not read file " + file);
                System.exit(-2);
            }
        }
        return map;
    }
}
