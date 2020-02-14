package in.dsc.instagram.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Post {

    String postId;
    String userId;
    String photoUrl;
    String caption;
    ArrayList<String> likes;
    Date timeStamp;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Post(String userId, String caption, String photoUrl) {
        this.userId = userId;
        this.photoUrl = photoUrl;
        this.timeStamp= Calendar.getInstance().getTime();
        this.likes=new ArrayList<>();
        this.caption=caption;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }


    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}

