import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataStore {

    private static HashMap<String, Admin> admins = new HashMap<>();
    private static HashMap<String, Member> members = new HashMap<>();

    // Serializes HashMaps
    public static void store() {
        // Serialize
        try {
            FileOutputStream mos = new FileOutputStream("memberData");
            FileOutputStream aos = new FileOutputStream("adminData");

            ObjectOutputStream out = new ObjectOutputStream(mos);
            out.writeObject(members);

            out = new ObjectOutputStream(aos);
            out.writeObject(admins);

            out.close();
            mos.close();
            aos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    // Reads in serialized HashMaps
    public static void restore() {
        // Deserialize on startup
        try {
            FileInputStream mis = new FileInputStream("memberData");
            FileInputStream ais = new FileInputStream("adminData");

            ObjectInputStream in = new ObjectInputStream(mis);
            members = (HashMap) in.readObject();

            in = new ObjectInputStream(ais);
            admins = (HashMap) in.readObject();

            in.close();
            mis.close();
            ais.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }

    // Store user in HashMap using their ID as the key
    public static void addMember(Member member) {
        members.put(member.getId(), member);
    }
    public static void addAdmin(Admin admin) {
        admins.put(admin.getId(), admin);
    }

    // Check a user exists
    public static boolean memberExists(String id) {
        return members.containsKey(id);
    }
    public static boolean adminExists(String id) {
        return admins.containsKey(id);
    }

    // Remove a user (Admin only)
    public static void removeMember(String id) {
        members.remove(id);
    }
    public static void removeAdmin(String id) {
        admins.remove(id);
    }

    // Retrieve object from HashMaps
    public static Member getMember(String id) {
        return members.get(id);
    }
    public static Admin getAdmin(String id) {
        return admins.get(id);
    }

    public static void verify() {
        //Verify list data
        System.out.println("USERS");
        Iterator iterator = members.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry member = (Map.Entry) iterator.next();
            System.out.println(member.getKey() + " : " + member.getValue());
        }
        System.out.println("ADMINS");
        iterator = admins.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry admin = (Map.Entry) iterator.next();
            System.out.println(admin.getKey() + " : " + admin.getValue());
        }
    }
}
