package edu.uncc.itcs4180.homework07;

import java.util.ArrayList;
import java.util.Map;

public class Cart {
    String title;
    ArrayList<Map<String, Object>> invitedUsers;
    ArrayList<Map<String, Object>> items;
    String ownerId;
    String ownerName;

    public Cart(String title, ArrayList<Map<String, Object>> invitedUsers, ArrayList<Map<String, Object>> items, String ownerId, String ownerName) {
        this.title = title;
        this.invitedUsers = invitedUsers;
        this.items = items;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public Cart() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Map<String, Object>> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(ArrayList<Map<String, Object>> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public ArrayList<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(ArrayList<Map<String, Object>> items) {
        this.items = items;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "title='" + title + '\'' +
                ", invitedUsers=" + invitedUsers +
                ", items=" + items +
                ", ownerId='" + ownerId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}
