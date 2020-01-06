import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserStore {

    private static HashMap<String, Admin> admins = new HashMap<>();
    private static HashMap<String, Member> members = new HashMap<>();
    private static HashMap<String, Media> mediaTitles = new HashMap<>();

    // Serializes HashMaps
    public static void store() {
        // Serialize
        try {
            FileOutputStream mos = new FileOutputStream("memberData");
            FileOutputStream aos = new FileOutputStream("adminData");
            FileOutputStream mtos = new FileOutputStream("mediaTitles");

            ObjectOutputStream out = new ObjectOutputStream(mos);
            out.writeObject(members);

            out = new ObjectOutputStream(aos);
            out.writeObject(admins);

            out = new ObjectOutputStream(mtos);
            out.writeObject(mediaTitles);

            out.close();
            mos.close();
            aos.close();
            mtos.close();
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
            FileInputStream mtis = new FileInputStream("mediaTitles");

            ObjectInputStream in = new ObjectInputStream(mis);
            members = (HashMap) in.readObject();

            in = new ObjectInputStream(ais);
            admins = (HashMap) in.readObject();

            in = new ObjectInputStream(mtis);
            mediaTitles = (HashMap) in.readObject();

            in.close();
            mis.close();
            ais.close();
            mtis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }

    // Check if stockID has been used for media - TRUE if FOUND, otherwise false
    public static boolean checkStockID(String id) {
        return mediaTitles.containsKey(id);
    }

    // Add media item to map of titles
    public static void addMedia(Media media) {
        mediaTitles.put(media.getStockID(), media);
    }

    // Store user in HashMap using their ID as the key
    public static void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public static void addAdmin(Admin admin) {
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

    // Remove a user
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

}


