import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserStore implements fileRW {

    private static HashMap<String, Admin> admins = new HashMap<>();
    private static HashMap<String, Member> members = new HashMap<>();

    private static final String path = "/data/users/";

    // Serializes HashMaps
    public static void store() {
        // Serialize
        fileRW.write(admins, path, "adminData");
        fileRW.write(members, path, "memberData");
    }

    // Reads in serialized HashMaps
    @SuppressWarnings("unchecked")
    public static void restore() {
        // Deserialize on startup
        members = fileRW.read(path, "adminData");
        admins = fileRW.read(path, "memberData");
    }


    // Writes and reads logfiles
    public static void writeLog(String userID, String computerID, String commit) {
        String currentDir = System.getProperty("user.dir");
        File directory = new File(currentDir + path + "logs/users/");
        if(!directory.exists()){
            if(directory.mkdirs()){
                System.out.println("Created directory " + directory.getAbsolutePath());
            } else {
                System.out.println("\nERROR: Could not create directory " + directory.getAbsolutePath());
                System.exit(-1);
            }
        }
        File logfile = new File(directory + "/" + userID + ".log");
        try {
            FileWriter out = new FileWriter(logfile, true);
            out.write(commit);
            out.write(System.lineSeparator());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File masterLog = new File(directory + "/master.log");
        try {
            FileWriter out = new FileWriter(masterLog, true);
            out.write(commit + "; Computer ID: " + computerID + " (" + userID + ")");
            out.write(System.lineSeparator());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readLog(String userID) {
        String currentDir = System.getProperty("user.dir");
        File directory = new File(currentDir + path + "logs/users/");
        if(!directory.exists()){
            if(directory.mkdirs()){
                System.out.println("Created directory " + directory.getAbsolutePath());
            } else {
                System.out.println("\nERROR: Could not create directory " + directory.getAbsolutePath());
                System.exit(-1);
            }
        }
        File logfile = new File(directory + "/" + userID + ".log");
        try {
            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", logfile.getAbsolutePath());
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Store user in HashMap using their ID as the key
    public static void addMember(Member member) {
        if(members.containsKey(member.getId())) {
            // Prevents duplicates of dummy data after first run (IDs have to be unique)
            return;
        }
        members.put(member.getId(), member);
    }

    public static void addAdmin(Admin admin) {
//        if(admins.containsKey(admin.getId())) {
//            // Prevents duplicates of dummy data after first run (IDs have to be unique)
//            return;
//        }
        admins.put(admin.getId(), admin);
    }

    // Update a user
    public static void updateMember(Member member) {
        members.replace(member.getId(), member);
    }

    public static void updateAdmin(Admin admin) {
        admins.replace(admin.getId(), admin);
    }

    // Check a user exists
    public static boolean memberExists(String id) {
        return members.containsKey(id);
    }

    public static boolean adminExists(String id) {
        return admins.containsKey(id);
    }


    // Retrieve object from HashMaps
    public static Member getMember(String id) {
        return members.get(id);
    }

    public static Admin getAdmin(String id) {
        return admins.get(id);
    }

}


