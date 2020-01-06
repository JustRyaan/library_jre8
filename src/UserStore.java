import java.io.*;
import java.util.HashMap;

public class UserStore {

    private static HashMap<String, Admin> admins = new HashMap<>();
    private static HashMap<String, Member> members = new HashMap<>();
    private static HashMap<String, Media> mediaIDs = new HashMap<>();
    private static final String path = "/data/users/";

    // Serializes HashMaps
    public static void store() {
        // Serialize
        try {
            FileOutputStream memberos = new FileOutputStream(path +"memberData.ser");
            FileOutputStream adminos = new FileOutputStream(path +"adminData.ser");
            FileOutputStream mediaos = new FileOutputStream("/data/mediaIDs.ser");

            ObjectOutputStream out = new ObjectOutputStream(memberos);
            out.writeObject(members);

            out = new ObjectOutputStream(adminos);
            out.writeObject(admins);

            out = new ObjectOutputStream(mediaos);
            out.writeObject(mediaIDs);

            out.close();
            memberos.close();
            adminos.close();
            mediaos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Reads in serialized HashMaps
    public static void restore() {
        // Deserialize on startup

        try {
            FileInputStream memberis = new FileInputStream(path + "memberData.ser");
            FileInputStream adminis = new FileInputStream(path + "adminData.ser");
            FileInputStream mediais = new FileInputStream("/data/mediaIDs.ser");

            ObjectInputStream in = new ObjectInputStream(memberis);
            members = (HashMap) in.readObject();

            in = new ObjectInputStream(adminis);
            admins = (HashMap) in.readObject();

            in = new ObjectInputStream(mediais);
            mediaIDs = (HashMap) in.readObject();

            in.close();
            memberis.close();
            adminis.close();
            mediais.close();
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
        return mediaIDs.containsKey(id);
    }

    // Add media item to map of titles
    public static void addMedia(Media media) {
        mediaIDs.put(media.getStockID(), media);
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


